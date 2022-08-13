package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.PublisherDTO;
import lazarus.restfulapi.library.model.entity.Publisher;
import lazarus.restfulapi.library.model.mapper.BookMapper;
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
    @Autowired private BookMapper bookMapper;

    public PublisherDTO createPublisher(PublisherDTO publisherDTO) throws UniqueViolationException {
        Publisher publisher = publisherMapper.toPublisher(publisherDTO);
        if (!publisherRepository.existsByName(publisher.getName())) {
            publisherRepository.save(publisher);
            return publisherMapper.toPublisherDTO(publisher);
        } else {
            throw new UniqueViolationException(ErrorInfo.ResourceType.PUBLISHER, publisher.getName());
        }
    }

    public List<PublisherDTO> readPublishers(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(publisherRepository.findAll().isEmpty())) {
            return publisherMapper.toPublisherDTOs(publisherRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER);
        }
    }

    public PublisherDTO readPublisherById(Long id) throws ResourceNotFoundException {
        return publisherMapper.toPublisherDTO(publisherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id)));
    }

    public List<BookDTO> readPublisherBooks(Long id) throws ResourceNotFoundException {
        if (publisherRepository.findById(id).isPresent()) {
            if (!(publisherRepository.findById(id).get().getBooks().isEmpty())) {
                return bookMapper.booksToBookDTOs(publisherRepository.findById(id).get().getBooks());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id);
        }
    }

    public PublisherDTO updatePublisherById(Long id, PublisherDTO publisherDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (publisherRepository.findById(id).isPresent()) {
            Publisher oldPublisher = publisherRepository.findById(id).get();
            Publisher newPublisher = publisherMapper.toPublisher(publisherDTO);
            if (!publisherRepository.existsByName(newPublisher.getName())) {
                oldPublisher.setName(newPublisher.getName());
                oldPublisher.setYearFounded(newPublisher.getYearFounded());
                oldPublisher.setAddress(newPublisher.getAddress());
                oldPublisher.setInfo(newPublisher.getInfo());
                oldPublisher.setWebsite(newPublisher.getWebsite());
                publisherRepository.save(oldPublisher);
                return publisherMapper.toPublisherDTO(oldPublisher);
            } else {
                throw new UniqueViolationException(ErrorInfo.ResourceType.PUBLISHER, newPublisher.getName());
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id);
        }
    }

    public void deletePublisherById(Long id) throws ResourceNotFoundException {
        if (publisherRepository.findById(id).isPresent()) {
            publisherRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, id);
        }
    }
}