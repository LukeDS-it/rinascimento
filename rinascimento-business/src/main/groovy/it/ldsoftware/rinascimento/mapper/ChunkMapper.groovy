package it.ldsoftware.rinascimento.mapper

import it.ldsoftware.primavera.mapper.base.BaseMapper
import it.ldsoftware.rinascimento.model.template.Chunk
import it.ldsoftware.rinascimento.view.template.ChunkDTO
import org.springframework.stereotype.Component

@Component
class ChunkMapper extends BaseMapper<Chunk, ChunkDTO> {
    @Override
    Chunk getModelInstance(ChunkDTO chunkDTO) {
        Chunk model = new Chunk(
                chunks: chunkDTO.chunks.collect {getModelInstance(it)},
                widget: chunkDTO.widget,
                params: chunkDTO.params,
                cssClass: chunkDTO.cssClass
        )
        if (chunkDTO.type)
            model.setType(chunkDTO.type)
        model.chunks.eachWithIndex { it, ix ->
            it.chunk = model
            it.order = ix
        }
        model
    }

    @Override
    ChunkDTO getViewInstance(Chunk chunk) {
        def dto = new ChunkDTO(
                chunks: chunk.chunks.collect {getViewInstance(it)},
                widget: chunk.widget,
                params: chunk.params,
                cssClass: chunk.cssClass
        )
        if (chunk.type)
            dto.type = chunk.type
        dto
    }
}
