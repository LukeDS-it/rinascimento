package it.ldsoftware.rinascimento.service

import it.ldsoftware.primavera.presentation.base.AppPropertyDTO
import it.ldsoftware.primavera.presentation.people.UserVM
import it.ldsoftware.rinascimento.exception.DatabaseCreationException
import it.ldsoftware.rinascimento.exception.DirectoryCreationException
import it.ldsoftware.rinascimento.exception.SiteConfigurationException
import it.ldsoftware.rinascimento.exception.UserConfigurationException
import it.ldsoftware.rinascimento.view.install.DatabaseConfig

/**
 * This service provides methods to configure a new instance of the CMS
 *
 * @author Luca Di Stefano
 */
interface InstallationService {
    /**
     * This function creates the basic directory structure of the CMS.
     * <p>
     *     The directory path will match the tenant's name, e.g. in case of tenant my.example.org, the base
     *     path will be <code>${cms-base-path}/org/example/my/_ROOT</code>.
     * </p>
     *
     * @throws DirectoryCreationException If something goes wrong the error will be wrapped in this exception
     */
    void mkdirs() throws DirectoryCreationException

    /**
     * This function writes the database configuration in the _ROOT/guest.properties file, then proceeds to create
     * the DDL of the database in the coordinates it received.
     *
     * @param config the configuration of the database
     * @throws DatabaseCreationException If something goes wrong the error will be wrapped in this exception
     */
    void installDatabase(DatabaseConfig config) throws DatabaseCreationException

    /**
     * This function adds the default data that is necessary to the CMS, such as user roles, default templates, etc
     *
     * @throws SiteConfigurationException If something goes wrong the error will be wrapped in this exception
     */
    void addBaseData() throws SiteConfigurationException

    /**
     * This function configures the CMS properties in the database
     *
     * @param properties the properties received that need to be saved
     * @throws SiteConfigurationException If something goes wrong the error will be wrapped in this exception
     */
    void configureWebsite(List<AppPropertyDTO> properties) throws SiteConfigurationException

    /**
     * This function creates the main administrator user for the CMS
     *
     * @param user the user data
     * @throws UserConfigurationException If something goes wrong the error will be wrapped in this exception
     */
    void configureUser(UserVM user) throws UserConfigurationException

    void createHomePage()
}