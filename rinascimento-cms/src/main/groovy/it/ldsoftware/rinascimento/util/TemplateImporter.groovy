package it.ldsoftware.rinascimento.util

import groovy.util.logging.Slf4j
import it.ldsoftware.rinascimento.view.template.ChunkDTO
import it.ldsoftware.rinascimento.view.template.TemplateDTO

/**
 * Utility class to import templates created with the easy template notation (ezt).
 *
 */
@Slf4j
class TemplateImporter {

    def static final HEADER_REGEX = / *(.*) \((.*)\) by (.*)/
    def static final NONE = 0, CSS = 1, JS = 2, CHUNKS = 3

    def static final IS_CHUNK = { it.startsWith('|') }, IS_START_CHUNK = { it.startsWith('-') }

    static {
        String.metaClass.stripRowDashes = {
            while (delegate.startsWith('-')) {
                delegate = delegate.substring(1)
            }
            while (delegate.endsWith('-')) {
                delegate = delegate.substring(0, delegate.length() - 1)
            }
            delegate.trim()
        }
    }

    def mode = NONE

    TemplateDTO template
    int currChunk, currLevel

    static TemplateDTO importTemplate(String text) {
        def importer = new TemplateImporter()
        importer.importTemplateInternal(text)
    }

    private TemplateImporter() {

    }

    private TemplateDTO importTemplateInternal(String text) {
        text.eachLine { line, index ->
            log.debug "Parsing line number ${index + 1}:\n${line}"
            if (index == 0)
                initTemplate line
            else
                parseLine line, index
        }
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
            case IS_START_CHUNK:
                startChunk index, actual
                break
            case IS_CHUNK:
                parseChunk index, actual
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
        if (mode == CHUNKS)
            throw new ParsingException("Found CSS declaration in the middle of a chunk at line ${index + 1}")
        mode = CSS
    }

    private void startJsParsing(int index) {
        log.debug "Found custom js definitions starting from line ${index + 1}"
        if (mode == CHUNKS)
            throw new ParsingException("Found CSS declaration in the middle of a chunk at line ${index + 1}")
        mode = JS
    }

    private void startChunk(int index, String line) {
        if (mode != CHUNKS) {
            log.debug "Found template start at line $index"
            mode = CHUNKS
        }
        def chunk = new ChunkDTO()
        template.chunks.add chunk

        String details = line.stripRowDashes()

        switch (details) {
            case {details.contains(',')}:
                chunk.type = details.split(/,/)[0]
                chunk.cssClass = details.split(/,/)[1]
                break
            default:
                chunk.cssClass = details
                break
        }
    }

    private void parseChunk(int index, String line) {
        log.debug "Parsing chunk at linke $index"
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
            case CHUNKS:
                log.error "I was looking for a row but I found something else at line ${index + 1}"
                throw new ParsingException("Unexpected row definition at line ${index + 1}")
                break
        }
    }
/*



    private void startNewRow(int index, String line) {
        def cssClass = stripRowDashes(line)
        log.debug "Creating new template row with classs ${cssClass} at line ${index + 1}"
        mode = ROWS
        currentRow = new TemplateRowDTO(cssClass: cssClass)
        template.rows += currentRow
        currRowNavigation = 0
    }


    @SuppressWarnings("GroovyAssignabilityCheck")
    private void parseRow(String line, int index) {
        currRowNavigation++
        def matcher = (line =~ NESTED_CHUNK_REGEX)
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

*/
}
