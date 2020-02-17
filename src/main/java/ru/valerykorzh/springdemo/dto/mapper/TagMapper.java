package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.dto.TagDto;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toTagDto(Tag tag);

    Tag toTag(TagDto tagDto);

    Set<TagDto> toTagsDto(Set<Tag> tags);

    Set<Tag> toTags(Set<TagDto> tagsDto);
}
