package it.ldsoftware.rinascimento

import it.ldsoftware.rinascimento.util.TemplateImporter
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import org.junit.Test

class TemplateImportTest {

    def importer = new TemplateImporter()

    def static expected = new TemplateDTO(
            name: 'ThreeColumns',
            templateVersion: '1.0',
            author: 'Luca Di Stefano',
            css: ['https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'],
            js: [
                    'https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js',
                    'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'
            ],

    )

    @Test
    void importValidTemplate() {
//        def t = importer.importTemplate(this.class.getClassLoader().getResource('ThreeColumns.ezt').text)
//        assertThat t isEqualToComparingFieldByField expected
//        assertThat t.rows hasSameElementsAs expected.rows
    }

    @Test
    void importInvalidTemplate() {

    }

}
