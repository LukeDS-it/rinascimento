package it.ldsoftware.rinascimento.multitenancy

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.AbstractResourceResolver
import org.springframework.web.servlet.resource.ResourceResolverChain

import javax.servlet.http.HttpServletRequest

/**
 * This resource resolver looks for resources for the templates within
 * the template's own directory. This is to ensure that every file
 * served is the correct one.
 *
 * @author Luca Di Stefano
 */
@Service
class MultiTenancyResourceResolver extends AbstractResourceResolver {

    private final Log logger = LogFactory.getLog(getClass())

    @Autowired
    private MultiTenancyUtils utils
    @Autowired
    private ApplicationContext applicationContext

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        String extPath = utils.getTenantResourcePath(request.getRequestURL().toString(), requestPath)
        File resFile = new File(extPath)
        if (resFile.exists()) {
            logger.info("Getting resource " + extPath)
            return new FileSystemResource(resFile)
        } else {
            Resource r = applicationContext.getResource("classpath:" + "templates/" + requestPath)
            if (r.exists())
                return r
            if (isInAdminContext(request)) {
                r = applicationContext.getResource("classpath:" + "templates/admin/app/" + requestPath)
                if (r.exists())
                    return r
                r = applicationContext.getResource("classpath:" + "templates/admin/node_modules/" + requestPath)
                if (r.exists())
                    return r
            }
        }
        return chain.resolveResource(request, requestPath, locations)
    }

    private static boolean isInAdminContext(HttpServletRequest request) {
        String referrer = request.getHeader("referer")
        return referrer && referrer =~ ".*(?:icms_admin|icms_install|live_edit).*"
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return chain.resolveUrlPath(resourceUrlPath, locations) // TODO
    }

}
