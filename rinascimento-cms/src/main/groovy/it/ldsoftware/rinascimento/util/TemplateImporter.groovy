package it.ldsoftware.rinascimento.util

import groovy.util.logging.Slf4j
import it.ldsoftware.rinascimento.view.template.TemplateColumnDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO
import it.ldsoftware.rinascimento.view.template.TemplateRowDTO
import it.ldsoftware.rinascimento.view.template.TemplateWidgetDTO

import static it.ldsoftware.rinascimento.util.TemplateImporter.ParsingMode.*

@Slf4j
class TemplateImporter {

    def static final HEADER_REGEX = / *(.*) \((.*)\) by (.*)/,
                     ROW_REGEX = /\| +([\w-_ ]+)(?:\((.*?)\))?/

    int currRowNavigation

    TemplateDTO template
    TemplateRowDTO currentRow
    ParsingMode mode = NONE

    TemplateDTO importTemplateFile(String file) {
        importTemplate(new File(file))
    }

    TemplateDTO importTemplate(File file) {
        importTemplate(file.text)
    }

    TemplateDTO importTemplate(String text) {

        text.eachLine { line, index ->
            log.debug "Parsing line number ${index + 1}:\n${line}"

            if (index == 0)
                initTemplate line
            else
                parseLine line, index
        }

        template.close()

        template
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    private void initTemplate(String header) {
        def finder = (header =~ HEADER_REGEX)
        template = new TemplateDTO(
                name: finder[0][1],
                templateVersion: finder[0][2],
                author: finder[0][3]
        )
        log.debug "Created template ${template.name}, version ${template.templateVersion} by ${template.author}"
    }

    private void parseLine(String line, int index) {
        def actual = line.trim()
        switch (line) {
            case 'css:':
                startCssParsing index
                break
            case 'js:':
                startJsParsing index
                break
            case { line.startsWith('-') }:
                startNewRow index, actual
                break
            case { line.startsWith('|') }:
                parseRow actual, index
                break
            case '':
                doNothing index
                break
            default:
                processLineAsResource actual, index
                break
        }
    }

    private void startCssParsing(int index) {
        log.debug "Found custom css definitions starting from line ${index + 1}"
        if (mode == ROWS)
            throw new ParsingException("Found CSS declaration in the middle of a row at line ${index + 1}")
        mode = CSS
    }

    private void startJsParsing(int index) {
        log.debug "Found custom js definitions starting from line ${index + 1}"
        if (mode == ROWS)
            throw new ParsingException("Found CSS declaration in the middle of a row at line ${index + 1}")
        mode = JS
    }

    private void startNewRow(int index, String line) {
        def cssClass = stripRowDashes(line)
        log.debug "Creating new template row with classs ${cssClass} at line ${index + 1}"
        mode = ROWS
        currentRow = new TemplateRowDTO(cssClass: cssClass)
        template.rows += currentRow
        currRowNavigation = 0
    }

    private static String stripRowDashes(String line) {
        while (line.startsWith('-')) {
            line = line.substring(1)
        }
        while (line.endsWith('-')) {
            line = line.substring(0, line.length() - 1)
        }
        line.trim()
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    private void parseRow(String line, int index) {
        currRowNavigation++
        def matcher = (line =~ ROW_REGEX)
        switch (currRowNavigation) {
            case 1:
                log.debug "Parsing columns of row #${template.rows.size()}"
                matcher.each {
                    TemplateColumnDTO column = new TemplateColumnDTO(cssClass: ((String) it[1]).trim())
                    log.debug "Creating column with class ${column.cssClass}"
                    currentRow.columns.add column
                }
                break
            default:
                log.debug "Parsing widgets of row #${template.rows.size()}"
                matcher.eachWithIndex { groups, i ->
                    TemplateColumnDTO column = currentRow[i]
                    String extension = ((String) groups[1]).trim()
                    if (extension) {
                        String script = "${extension}.groovy"
                        String params = ((String) groups[2])?.trim()
                        log.debug "Adding extension ${script} to column ${i} with parameters ${params}"
                        column.widgets.add(new TemplateWidgetDTO(script: script, params: params))
                    } else {
                        log.debug "Empty column found, doing nothing"
                    }
                }
                break
        }
    }

    private static void doNothing(int index) {
        log.debug "Encountered blank line at ${index + 1}"
    }

    private void processLineAsResource(String line, int index) {
        log.debug "Processing resource line..."
        switch (mode) {
            case CSS:
                log.debug "Adding a custom css (${line})"
                template.css += line
                break
            case JS:
                log.debug "Adding a custom js (${line})"
                template.js += line
                break
            case NONE:
                doNothing index
                break
            case ROWS:
                log.error "I was looking for a row but I found something else at line ${index + 1}"
                throw new ParsingException("Unexpected row definition at line ${index + 1}")
                break
        }
    }

    enum ParsingMode {
        CSS, JS, ROWS, NONE
    }

}
