package it.ldsoftware.rinascimento.multitenancy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * This class intercepts the tenant based on the address that the user
 * accesses.
 *
 * Note that tenants must be configured beforehand separately.
 *
 * @author Luca Di Stefano
 */
@Service
class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MultiTenancyUtils utils

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString()
        String tenant = utils.getTenant url
        request.setAttribute MultiTenancyUtils.CURRENT_TENANT, tenant
        true
    }

}
