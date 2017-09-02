package it.ldsoftware.rinascimento.controller

import it.ldsoftware.rinascimento.util.PageMode
import it.ldsoftware.rinascimento.view.content.WebPageDTO
import it.ldsoftware.rinascimento.view.template.TemplateColumnDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import it.ldsoftware.rinascimento.view.template.TemplateRowDTO
import it.ldsoftware.rinascimento.view.template.TemplateWidgetDTO
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PublicPageController extends AbstractPageController {

    final String DEFAULT_PAGE = "home"
    final PageMode VIEWING_MODE = PageMode.VIEW

    @ResponseBody
    @RequestMapping("/{pageUID}")
    String loadPage(@PathVariable String pageUID, Locale locale) {
        def page = findPage pageUID
        buildPage page, locale, VIEWING_MODE
    }

    @ResponseBody
    @RequestMapping("/")
    String loadPage(Locale locale) {
        loadPage DEFAULT_PAGE, locale
    }

    @ResponseBody
    @RequestMapping("/testPage")
    String testPage(Locale locale) {
        WebPageDTO page = new WebPageDTO(
                title: 'Hello world',
                meta: ['hello': 'world'],
                template: defaultTemplate(),
                content: 'This page is working.'
        )
        pageBuilder.buildPage page, locale, PageMode.VIEW
    }

    static TemplateDTO defaultTemplate() {
        new TemplateDTO(
                name: 'default',
                css: ['https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css', 'css/default.css'],
                js: ['https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js', 'https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js', 'js/app.js'],
                rows: [
                        new TemplateRowDTO(cssClass: 'row',
                                columns: [
                                        new TemplateColumnDTO(cssClass: 'col-md-3'),
                                        new TemplateColumnDTO(cssClass: 'col-md-6', widgets: [
                                                new TemplateWidgetDTO(script: 'ContentWidget.groovy', params: '{}'),
                                                new TemplateWidgetDTO(script: 'TestWidget.groovy', params: '{"text": "Hello"}')
                                        ]),
                                        new TemplateColumnDTO(cssClass: 'col-md-3')

                                ]
                        )
                ]
        )
    }

}
