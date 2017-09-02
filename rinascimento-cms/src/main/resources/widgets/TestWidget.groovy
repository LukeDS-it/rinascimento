package widgets

import groovy.xml.MarkupBuilder
import it.ldsoftware.rinascimento.extension.Widget
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import org.springframework.context.ApplicationContext

class TestWidget extends Widget {

    TestWidget(ApplicationContext context) {
        super(context)
    }

    @Override
    def buildContent(MarkupBuilder builder, WebPageDTO page, Locale locale, def params) {
        builder.div(params.text)
    }
}
