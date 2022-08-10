package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.PublisherDTO;
import lazarus.restfulapi.library.model.entity.Publisher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDTO toPublisherDTO(Publisher publisher);
    List<PublisherDTO> toPublisherDTOs(List<Publisher> publishers);
    Publisher toPublisher(PublisherDTO publisherDTO);
}
