package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import ru.valerykorzh.springdemo.domain.Image;
import ru.valerykorzh.springdemo.dto.ImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    Image toImage(ImageDto imageDto);

    ImageDto toImageDto(Image image);

}
