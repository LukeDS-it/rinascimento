package it.ldsoftware.rinascimento.multitenancy

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.servlet.resource.AbstractResourceResolver
import org.springframework.web.servlet.resource.ResourceResolverChain

import javax.servlet.http.HttpServletRequest
import java.util.regex.Matcher

/**
 * This resource resolver looks for resources for the templates within
 * the template's own directory. This is to ensure that every file
 * served is the correct one.
 *
 * @author Luca Di Stefano
 */
@Slf4j
@Service
class MultiTenancyResourceResolver extends AbstractResourceResolver {

    @Autowired
    private MultiTenancyUtils utils

    @Autowired
    private ApplicationContext applicationContext

    @Override
    @SuppressWarnings("GroovyAssignabilityCheck")
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {

        switch (requestPath) {
            case ~/^\/?widgets(.*)/:
                String realRes = Matcher.lastMatcher[0][1]

                String resPath = utils.getTenantExtensionDir().concat(realRes)
                File resFile = new File(resPath)
                if (resFile.exists())
                    return new FileSystemResource(resFile)

                Resource r = applicationContext.getResource("classpath:" + "widgets/resources/${realRes}")
                if (r.exists())
                    return r

                break

            case ~/^\/?templates(.*)/:
                String realRes = Matcher.lastMatcher[0][1]

                String resPath = utils.getTenantResourcePath(request.getRequestURL().toString(), realRes)

                File resFile = new File(resPath)
                if (resFile.exists())
                    return new FileSystemResource(resFile)

                Resource r = applicationContext.getResource("classpath:templates/${realRes}")
                if (r.exists())
                    return r
                break

            case ~/^\/?admin(.*)/:
                Resource r = applicationContext.getResource("classpath:${requestPath}")
                if (r.exists())
                    return r

                break
        }

        log.info "Could not find resource ${requestPath}"

        return chain.resolveResource(request, requestPath, locations)
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return chain.resolveUrlPath(resourceUrlPath, locations) // TODO
    }

}
