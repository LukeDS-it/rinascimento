package it.ldsoftware.rinascimento.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix =  "it.ldsoftware.rinascimento")
class RinascimentoProperties {
    /**
     * Specifies a path in the file system that holds all the
     * information of the CMS. It will be used to store configuration
     * files, templates, resources, etc.
     */
    String filePath

    /**
     * This is the master username used to allow access to the website installer
     */
    String masterUsername

    /**
     * This is the master password used to allow access to the website installer
     */
    String masterPassword

}
