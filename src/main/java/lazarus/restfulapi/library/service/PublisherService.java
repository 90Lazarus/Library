package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.PublisherDTO;
import lazarus.restfulapi.library.model.entity.Publisher;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.model.mapper.PublisherMapper;
import lazarus.restfulapi.library.repository.BookRepository;
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
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public PublisherDTO createAPublisher(PublisherDTO publisherDTO) throws UniqueViolationException {
        Publisher publisher = publisherMapper.publisherDTOtoPublisher(publisherDTO);
        if (!(publisherRepository.existsByName(publisher.getName()))) {
            publisherRepository.save(publisher);
            return publisherMapper.publisherToPublisherDTO(publisher);
        } else {
            throw new UniqueViolationException(ErrorInfo.ResourceType.PUBLISHER, publisher.getName());
        }
    }

    public List<PublisherDTO> readPublishers(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(publisherRepository.findAll().isEmpty())) {
            return publisherMapper.publishersToPublisherDTOs(publisherRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER);
        }
    }

    public PublisherDTO readAPublisher(Long publisherId) throws ResourceNotFoundException {
        if (publisherRepository.findById(publisherId).isPresent()) {
            return publisherMapper.publisherToPublisherDTO(publisherRepository.findById(publisherId).get());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, publisherId);
        }
    }

    public List<BookDTO> readPublisherBooks(Long publisherId) throws ResourceNotFoundException {
        if (publisherRepository.findById(publisherId).isPresent()) {
            if (!(publisherRepository.findById(publisherId).get().getBooks().isEmpty())) {
                return bookMapper.booksToBookDTOs(publisherRepository.findById(publisherId).get().getBooks());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "Found no books in the database for the publisher with the id: " + publisherId + "!");
                //throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.PUBLISHER, publisherId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, publisherId);
        }
    }

    public List<BookDTO> readOriginalPublisherBooks(Long publisherId) throws ResourceNotFoundException {
        if (publisherRepository.findById(publisherId).isPresent()) {
            if (!(publisherRepository.findById(publisherId).get().getBooksOriginal().isEmpty())) {
                return bookMapper.booksToBookDTOs(publisherRepository.findById(publisherId).get().getBooksOriginal());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "Found no books in the database for the publisher with the id: " + publisherId + "!");
                //throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.PUBLISHER, publisherId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, publisherId);
        }
    }

    public PublisherDTO updateAPublisher(Long publisherId, PublisherDTO publisherDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (publisherRepository.findById(publisherId).isPresent()) {
            Publisher oldPublisher = publisherRepository.findById(publisherId).get();
            Publisher newPublisher = publisherMapper.publisherDTOtoPublisher(publisherDTO);
            if (!(publisherRepository.existsByName(newPublisher.getName()))) {
                oldPublisher.setName(newPublisher.getName());
                oldPublisher.setYearFounded(newPublisher.getYearFounded());
                oldPublisher.setAddress(newPublisher.getAddress());
                oldPublisher.setInfo(newPublisher.getInfo());
                oldPublisher.setWebsite(newPublisher.getWebsite());
                publisherRepository.save(oldPublisher);
                return publisherMapper.publisherToPublisherDTO(oldPublisher);
            } else {
                throw new UniqueViolationException(ErrorInfo.ResourceType.PUBLISHER, newPublisher.getName());
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, publisherId);
        }
    }

    public void deleteAPublisher(Long publisherId) throws ResourceNotFoundException {
        if (publisherRepository.findById(publisherId).isPresent()) {
            publisherRepository.deleteById(publisherId);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.PUBLISHER, publisherId);
        }
    }
}