package it.ldsoftware.rinascimento.multitenancy

import it.ldsoftware.rinascimento.config.RinascimentoProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

import java.util.regex.Matcher

import static it.ldsoftware.rinascimento.util.PathConstants.*

@Service
@EnableConfigurationProperties(RinascimentoProperties.class)
class MultiTenancyUtils {

    static final String DEFAULT_ID = "default", SCOPE_REQUEST = "", CURRENT_TENANT = "currentTenant",
        ROOT_DIR = "_ROOT"

    @Autowired
    private RinascimentoProperties intersectProperties
    @Autowired
    private TenantResolver resolver

    String getTenantProperties() {
        getTenantRootDir() + PATH_GUEST_PROPERTIES
    }

    String getTenantTemplateDir() {
        getTenantRootDir() + PATH_TEMPLATES + "/"
    }

    String getTenantExtensionDir() {
        getTenantRootDir() + PATH_EXTENSIONS + "/"
    }

    String getTenantResourceDir() {
        getTenantRootDir() + PATH_RESOURCES + "/"
    }

    String getTenantTemplateDir(String url) {
        getTenantRootDirByUrl(url) + PATH_TEMPLATES + "/"
    }

    String getTenantResourcePath(String url, String resource) {
        return getTenantTemplateDir(url) + (resource.startsWith('/') ? '' : '/') + resource
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    static String getTenant(String url) {
        (url =~ "https?://(www\\.)?([\\w.]*)/?.*") ? Matcher.lastMatcher[0][2] : ""
    }

    String getTenantRootDir() {
        getTenantRootDirByTenant(resolver.resolveCurrentTenantIdentifier())
    }

    String getTenantRootDirByUrl(String url) {
        intersectProperties.filePath + tenantToPath(getTenant(url)) + PATH_GUEST_ROOT
    }

    String getTenantRootDirByTenant(String tenant) {
        intersectProperties.filePath + tenantToPath(tenant) + PATH_GUEST_ROOT
    }

    private static String tenantToPath(String tenant) {
        tenant.split("\\.").reverse().join("/")
    }

}
