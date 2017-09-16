package it.ldsoftware.rinascimento.util

import groovy.util.logging.Slf4j
import it.ldsoftware.rinascimento.view.template.ChunkDTO
import it.ldsoftware.rinascimento.view.template.ChunkHolder
import it.ldsoftware.rinascimento.view.template.TemplateDTO

/**
 * Utility class to import templates created with the easy template notation (ezt).
 *
 */
@Slf4j
class TemplateImporter {

    def static final NONE = 0, CSS = 1, JS = 2, CHUNKS = 3

    def static final IS_CHUNK = { it.startsWith('|') }, IS_START_CHUNK = { it.startsWith('-') }

    def mode = NONE

    TemplateDTO template

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
        def finder = (header =~ / *(.*) \((.*)\) by (.*)/)
        template = new TemplateDTO(
                name: finder[0][1],
                templateVersion: finder[0][2],
                author: finder[0][3]
        )
        log.debug "Created template ${template.name}, version ${template.templateVersion} by ${template.author}"
    }

    private void parseLine(String line, int index, ChunkHolder level = template) {
        def actual = line.trim()
        switch (line) {
            case ~/(?:\| *)*/:
                doNothing index
                break
            case 'css:':
                startCssParsing index
                break
            case 'js:':
                startJsParsing index
                break
            case IS_START_CHUNK:
                startChunk index, actual, level
                break
            case IS_CHUNK:
                parseChunk index, actual, level
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

    private void startChunk(int index, String line, ChunkHolder level) {
        if (mode != CHUNKS) {
            log.debug "Found template start at line $index"
            mode = CHUNKS
        }

        def matcher = (line =~ /-+ *(?:([a-z]*), *)?([a-z\-0-9]*)? *-+/)

        matcher.each {
            def chunk = new ChunkDTO()
            if (it[1])
                chunk.type = it[1]
            if (it[2])
                chunk.cssClass = it[2]

            level.chunks.add chunk
            log.debug "Created new chunk"
        }
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    private void parseChunk(int index, String line, ChunkHolder level) {
        line = line.substring(2, line.length() - 2)
        def extensionMatcher = (line =~ /([\w_]+)(?:\((.*?)\))?/)
        def startChunkMatcher = (line =~ /-+ *(?:([a-z]*), *)?([a-z\-0-9]*)? *-+/)
        if (extensionMatcher && !startChunkMatcher && !line.startsWith("|")) {
            log.debug "Found extensions at line $index"
            extensionMatcher.eachWithIndex { entry, i ->
                ChunkDTO chunk = level.chunks[i]
                if (chunk.cssClass) {
                    log.debug "Chunk has a css-class, treating as a widget container"
                    chunk.chunks.add new ChunkDTO()
                    chunk = chunk.chunks.last()
                }

                chunk.widget = "${entry[1]}.groovy"
                chunk.params = entry[2]
                log.debug "Added widget ${chunk.widget} to chunk #$i"
            }
        } else {
            log.debug "Going down one level"
            parseLine line, index, level.chunks.last()
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
