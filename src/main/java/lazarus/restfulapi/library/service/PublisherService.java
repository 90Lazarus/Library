package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.PublisherDTO;
import lazarus.restfulapi.library.model.entity.Publisher;
import lazarus.restfulapi.library.model.mapper.PublisherMapper;
import lazarus.restfulapi.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    @Autowired private PublisherRepository publisherRepository;
    @Autowired private PublisherMapper publisherMapper;

    public PublisherDTO createPublisher(PublisherDTO publisherDTO) throws UniqueViolationException {
        Publisher publisher = publisherMapper.toPublisher(publisherDTO);
        if (publisherRepository.existsByIdOrName(publisher.getId(), publisher.getName())) {
            throw new UniqueViolationException(ErrorInfo.ResourceType.PUBLISHER, publisher.getName());
        } else {
            publisherRepository.save(publisher);
        }
        return publisherMapper.toPublisherDTO(publisher);
    }

    public List<PublisherDTO> readPublishers(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        return publisherMapper.toPublisherDTOs(publisherRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
    }

    public PublisherDTO readPublisherById(Long id) throws ResourceNotFoundException {
        return publisherMapper.toPublisherDTO(publisherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id)));
    }

    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (publisherRepository.findById(id).isPresent()) {
            Publisher oldPublisher = publisherRepository.findById(id).get();
            Publisher newPublisher = publisherMapper.toPublisher(publisherDTO);
            if (publisherRepository.existsByIdOrName(newPublisher.getId(), newPublisher.getName())) {
                throw new UniqueViolationException(ErrorInfo.ResourceType.PUBLISHER, newPublisher.getName());
            } else {
                oldPublisher.setId(newPublisher.getId());
                oldPublisher.setName(newPublisher.getName());
                oldPublisher.setYearFounded(newPublisher.getYearFounded());
                oldPublisher.setAddress(newPublisher.getAddress());
                oldPublisher.setInfo(newPublisher.getInfo());
                oldPublisher.setWebsite(newPublisher.getWebsite());
                publisherRepository.save(oldPublisher);
                return publisherMapper.toPublisherDTO(oldPublisher);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id);
        }
    }

    public void deletePublisher(Long id) throws ResourceNotFoundException {
        if (publisherRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id);
        } else {
            publisherRepository.deleteById(id);
        }
    }
}