package com.linkcut.shortener.modules.urls.mapper;

import com.linkcut.shortener.modules.urls.dto.LinkDto;
import com.linkcut.shortener.modules.urls.dto.UpdateLinkRequestDto;
import com.linkcut.shortener.modules.urls.entity.Link;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LinkMapper {

    @Mapping(target = "shortUrl", ignore = true)
    @Mapping(target = "clicks", ignore = true)
    LinkDto toDto(Link link);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    void updateEntityFromDto(UpdateLinkRequestDto dto, @MappingTarget Link link);
}