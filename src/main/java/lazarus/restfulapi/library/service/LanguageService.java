package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.BookDTO;
import lazarus.restfulapi.library.model.dto.LanguageDTO;
import lazarus.restfulapi.library.model.entity.Language;
import lazarus.restfulapi.library.model.mapper.BookMapper;
import lazarus.restfulapi.library.model.mapper.LanguageMapper;
import lazarus.restfulapi.library.repository.BookRepository;
import lazarus.restfulapi.library.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    @Autowired private LanguageRepository languageRepository;
    @Autowired private LanguageMapper languageMapper;
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public LanguageDTO createALanguage(LanguageDTO languageDTO) throws UniqueViolationException {
        Language language = languageMapper.languageDTOToLanguage(languageDTO);
        if (!(languageRepository.existsByName(language.getName()))) {
            languageRepository.save(language);
            return languageMapper.languageToLanguageDTO(language);
        } else {
            throw new UniqueViolationException(ErrorInfo.ResourceType.LIBRARY, language.getName());
        }
    }

    public List<LanguageDTO> readLanguages(Integer page, Integer size, Sort.Direction direction, String sortBy) throws ResourceNotFoundException {
        if (!(languageRepository.findAll().isEmpty())) {
            return languageMapper.languagesToLanguageDTOs(languageRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE);
        }
    }

    public LanguageDTO readALanguage(Long languageId) throws ResourceNotFoundException {
        if (languageRepository.findById(languageId).isPresent()) {
            return languageMapper.languageToLanguageDTO(languageRepository.findById(languageId).get());
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, languageId);
        }
    }

    public List<BookDTO> readLanguageBooks(Long languageId) throws ResourceNotFoundException {
        if (languageRepository.findById(languageId).isPresent()) {
            if (!(languageRepository.findById(languageId).get().getBooks().isEmpty())) {
                return bookMapper.booksToBookDTOs(languageRepository.findById(languageId).get().getBooks());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "Found no books in the database for the language with the id: " + languageId + "!");
                //throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.LANGUAGE, languageId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, languageId);
        }
    }

    public List<BookDTO> readOriginalLanguageBooks(Long languageId) throws ResourceNotFoundException {
        if (languageRepository.findById(languageId).isPresent()) {
            if (!(languageRepository.findById(languageId).get().getBooksOriginal().isEmpty())) {
                return bookMapper.booksToBookDTOs(languageRepository.findById(languageId).get().getBooksOriginal());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, "Found no books in the database for the language with the id: " + languageId + "!");
                //throw new ResourceNotFoundException(ErrorInfo.ResourceType.BOOK, ErrorInfo.ResourceType.LANGUAGE, languageId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, languageId);
        }
    }

    public LanguageDTO updateALanguage(Long languageId, LanguageDTO languageDTO) throws ResourceNotFoundException, UniqueViolationException {
        if (languageRepository.findById(languageId).isPresent()) {
            Language oldLanguage = languageRepository.findById(languageId).get();
            Language newLanguage = languageMapper.languageDTOToLanguage(languageDTO);
            if (!(languageRepository.existsByName(newLanguage.getName()))) {
                oldLanguage.setName(newLanguage.getName());
                languageRepository.save(oldLanguage);
                return languageMapper.languageToLanguageDTO(oldLanguage);
            } else {
                throw new UniqueViolationException(ErrorInfo.ResourceType.LANGUAGE, newLanguage.getName());
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, languageId);
        }
    }

    public void deleteALanguage(Long languageId) throws ResourceNotFoundException {
        if (languageRepository.findById(languageId).isPresent()) {
            languageRepository.deleteById(languageId);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, languageId);
        }
    }
}