package it.ldsoftware.rinascimento.util

import groovy.json.JsonSlurper

abstract class ExtensionUtils {

    static def jsonToObj(String json) {
        return new JsonSlurper().parseText(json)
    }

}
