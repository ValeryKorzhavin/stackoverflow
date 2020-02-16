package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.dto.TagDto;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toTag(TagDto tagDto);

    TagDto toTagDto(Tag tag);

    Set<TagDto> toTagsDto(Set<Tag> tags);

    Set<Tag> toTags(Set<TagDto> tagsDto);
}
