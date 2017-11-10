Rinascimento CMS
================

This module contains the real web application that will run the CMS.

## Front-end development

There are two main front-end applications. The first one is the proper administration
panel, which allows any user to edit the website.
The second is the "sysadmin" control panel, which will be shown only once when the
website is not configured, and will trigger the configuration of the whole environment.

To work on either of the two you just need to edit sources in the relative directories:
under `/src/main/webapp/app` the directory `install` contains the website installer,
the directory `admin` contains the administration panel.

The front-end applications are developed with Angular2. The base was created with
the Angular-CLI, and later adapted to use Webpack and Typescript.

To launch any of the two applications (they can't be run together for the moment)
you first need to launch the CMS application, running the main class of the application
(`RinascimentoCmsApplication`), then you can run yarn from the `rinascimento-cms`
directory as follows:

* `yarn build`: you need to run this the first time you open the project
  or update from the central repository
* `yarn start-install`: runs the installer application (usual dev server mode
  that recompiles on changes). The CMS application will be proxied by Webpack.
* `yarn start-admin`: runs the admin application (usual dev server mode
  that recompiles on changes). The CMS application will be proxied by Webpack.
