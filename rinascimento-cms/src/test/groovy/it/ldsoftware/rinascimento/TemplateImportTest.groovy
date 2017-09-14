package it.ldsoftware.rinascimento

import it.ldsoftware.rinascimento.util.TemplateImporter
import it.ldsoftware.rinascimento.view.template.TemplateColumnDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import it.ldsoftware.rinascimento.view.template.TemplateRowDTO
import it.ldsoftware.rinascimento.view.template.TemplateWidgetDTO
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

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
            rows: [
                    new TemplateRowDTO(
                            cssClass: 'row',
                            columns: [new TemplateColumnDTO(cssClass: 'col-md-12', widgets: [new TemplateWidgetDTO(script: 'HeaderExtension.groovy')])]
                    ),
                    new TemplateRowDTO(
                            cssClass: 'row content-line',
                            columns: [
                                    new TemplateColumnDTO(cssClass: 'col-md-3', widgets: [new TemplateWidgetDTO(script: 'MenuExtension.groovy', params: '{"menu": 1}')]),
                                    new TemplateColumnDTO(cssClass: 'col-md-6', widgets: [new TemplateWidgetDTO(script: 'ContentWidget.groovy')]),
                                    new TemplateColumnDTO(cssClass: 'col-md-3')
                            ]
                    ),
                    new TemplateRowDTO(
                            cssClass: 'row',
                            columns: [new TemplateColumnDTO(cssClass: 'col-md-12', widgets: [new TemplateWidgetDTO(script: 'FooterExtension.groovy')])]
                    )
            ]
    )

    @Test
    void importValidTemplate() {
        def t = importer.importTemplate(this.class.getClassLoader().getResource('ThreeColumns.ezt').text)
        assertThat t isEqualToComparingFieldByField expected
        assertThat t.rows hasSameElementsAs expected.rows
    }

    @Test
    void importInvalidTemplate() {

    }

}
