package it.ldsoftware.rinascimento

import it.ldsoftware.rinascimento.util.TemplateImporter
import it.ldsoftware.rinascimento.view.template.ChunkDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import org.junit.Test

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

class TemplateImportTest {

    def static expected = new TemplateDTO(
            name: 'ThreeColumns',
            templateVersion: '1.0',
            author: 'Luca Di Stefano',
            css: ['https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'],
            js: [
                    'https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js',
                    'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'
            ],
            chunks: [
                    new ChunkDTO(widget: 'HeaderExtension.groovy'),
                    new ChunkDTO(
                            cssClass: 'container-fluid',
                            chunks: [
                                    new ChunkDTO(
                                            cssClass: 'row',
                                            chunks: [
                                                    new ChunkDTO(
                                                            cssClass: 'col-md-3',
                                                            chunks: [new ChunkDTO(widget: 'MenuExtension.groovy', params: '{"menu": 1}')]
                                                    ),
                                                    new ChunkDTO(
                                                            cssClass: 'col-md-6',
                                                            chunks: [new ChunkDTO(widget: 'ContentExtension.groovy')]
                                                    ),
                                                    new ChunkDTO(
                                                            cssClass: 'col-md-3'
                                                    )
                                            ]
                                    ),
                                    new ChunkDTO(
                                            type: 'footer',
                                            cssClass: 'row',
                                            chunks: [
                                                    new ChunkDTO(widget: 'FooterExtension.groovy')
                                            ]
                                    )
                            ]
                    )
            ]
    )

    @Test
    void importValidTemplate() {
        def t = TemplateImporter.importTemplate(this.class.getClassLoader().getResource('ThreeColumns.ezt').text)
        assertThat t isEqualToComparingFieldByField expected
        assertThat t.chunks hasSameElementsAs expected.chunks
    }

    @Test
    void importInvalidTemplate() {

    }

}
