package com.mimi.mlibrary.mapper.publication;

import com.mimi.mlibrary.model.dto.publication.PublicationDto;
import com.mimi.mlibrary.model.entity.publication.Publication;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

@MapperConfig
public interface PublicationMapperConfig {

    void mapPublication( Publication mean, @MappingTarget PublicationDto publicationDto );
}