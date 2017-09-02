package it.ldsoftware.rinascimento.util

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
                            KEY_404_TEMPLATE = "rinascimento.error.template.404"
    }
}
