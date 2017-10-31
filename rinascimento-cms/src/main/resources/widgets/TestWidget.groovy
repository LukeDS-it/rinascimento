package widgets

import it.ldsoftware.rinascimento.extension.Widget

class TestWidget extends Widget {

    @Override
    List<String> checkParameters() { [] }

    @Override
    void buildActualContent() {
        builder.div(params.text)
    }

    @Override
    void buildConfig() {

    }

    @Override
    List<String> getCss() {
        return ['css/test-widget.css']
    }

    @Override
    List<String> getJs() {
        return ['js/test-widget.js']
    }

}
