package widgets

import it.ldsoftware.rinascimento.extension.Widget

class TestWidget extends Widget {

    @Override
    List<String> checkParameters() { [] }

    @Override
    Map<String, Object> getModel() {
        return [text: params.text]
    }

    @Override
    String getTemplateName() {
        return "test-widget.html"
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
