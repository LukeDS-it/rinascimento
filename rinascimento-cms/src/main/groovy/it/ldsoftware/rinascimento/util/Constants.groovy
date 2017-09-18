package it.ldsoftware.rinascimento.util

import it.ldsoftware.primavera.presentation.security.GroupDTO
import it.ldsoftware.primavera.presentation.security.RoleDTO

abstract class Constants {
    final static String HTML_START = "<!DOCTYPE html>\n"

    abstract class Messages {
        final static String TITLE_SERVER_ERROR = "title.500",
                            TITLE_NOT_FOUND = "title.404"

        final static String CONTENT_SERVER_ERROR = "content.500",
                            CONTENT_NOT_FOUND = "content.404"
    }

    abstract class ConfKeys {
        final static String KEY_500_TEMPLATE = "rinascimento.error.template.500",
                            KEY_404_TEMPLATE = "rinascimento.error.template.404",
                            KEY_SITE_TITLE = "rinascimento.site.title",
                            KEY_SITE_DESCRIPTION = "rinascimento.site.description",
                            KEY_SITE_TAGS = "rinascimento.site.tags",
                            KEY_GOOGLE_ANALYTICS = "rinascimento.google.analytics",
                            KEY_GOOGLE_CLIENT_ID = "rinascimento.google.apps.clientId",
                            KEY_RECAPTCHA_SECRET = "rinascimento.recaptcha.secret",
                            KEY_RECAPTCHA_SITE = "rinascimento.recaptcha.site"
    }

    abstract class Roles {
        /**
         * This permission denotes that the user can access the website configuration
         */
        final static String ROLE_SITE_CONFIGURE = "ROLE_SITE_CONFIGURE"
        /**
         * An editor is someone who can access the page editor. All extended permissions (CRUD) are relative only
         * to the user's posts. To be able to edit/delete other people's posts, an user must have the MODERATOR role
         */
        final static String ROLE_EDITOR = "ROLE_EDITOR"
        /**
         * A moderator is someone who can access the moderation part, can edit or delete content of other users
         */
        final static String ROLE_MODERATOR = "ROLE_MODERATOR"
        /**
         * Who has the template role can:
         * <ul>
         *     <li>In the control panel (if allowed to go there) the user can install/edit/remove templates</li>
         *     <li>In the web page editor (if allowed to go there) the user can change the page templates</li>
         *     <li>In the mailing list editor (if allowed to go there) the user can change the mailing list templates</li>
         * </ul>
         */
        final static String ROLE_TEMPLATE = "ROLE_TEMPLATE"
        /**
         * This role allows access to the mailing list administration
         */
        final static String ROLE_MAILING_LIST = "ROLE_MAILING_LIST"
        /**
         * The user can view the static resources management. Fine-grain permission is available through primavera
         */
        final static String ROLE_RESOURCES = "ROLE_RESOURCES"
        /**
         * The user can access the redirect management.
         */
        final static String ROLE_REDIRECT = "ROLE_REDIRECT"
        /**
         * The user can see the contact management
         */
        final static String ROLE_CONTACTS = "ROLE_CONTACTS"
    }

    final static List<RoleDTO> BASE_ROLES = [

    ]

    final static List<GroupDTO> BASE_GROUPS = [

    ]
}
