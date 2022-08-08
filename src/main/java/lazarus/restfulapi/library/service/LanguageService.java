package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.exception.UniqueViolationException;
import lazarus.restfulapi.library.model.dto.LanguageDTO;
import lazarus.restfulapi.library.model.entity.Language;
import lazarus.restfulapi.library.model.mapper.LanguageMapper;
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

    public LanguageDTO createLanguage(LanguageDTO languageDTO) throws UniqueViolationException {
        Language language = languageMapper.toLanguage(languageDTO);
        if (languageRepository.existsByIdOrName(language.getId(), language.getName())) {
            throw new UniqueViolationException(ErrorInfo.ResourceType.LIBRARY, language.getName());
        }
        else {
            languageRepository.save(language);
        }
        return languageMapper.toLanguageDTO(language);
    }

    public List<LanguageDTO> readLanguages(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        return languageMapper.toLanguageDTOs(languageRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
    }

    public LanguageDTO readLanguageById(Long id) throws ResourceNotFoundException {
        return languageMapper.toLanguageDTO(languageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, id)));
    }

    public LanguageDTO updateLanguage(Long id, LanguageDTO languageDTO) throws ResourceNotFoundException, UniqueViolationException {
        if(languageRepository.findById(id).isPresent()) {
            Language oldLanguage = languageRepository.findById(id).get();
            Language newLanguage = languageMapper.toLanguage(languageDTO);
            if(languageRepository.existsByIdOrName(newLanguage.getId(), newLanguage.getName())) {
                throw new UniqueViolationException(ErrorInfo.ResourceType.LANGUAGE, newLanguage.getName());
            }
            else {
                oldLanguage.setName(newLanguage.getName());
                oldLanguage.setBooks(newLanguage.getBooks());
                oldLanguage.setBooksOriginal(newLanguage.getBooksOriginal());
                languageRepository.save(oldLanguage);
                return languageMapper.toLanguageDTO(oldLanguage);
            }
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, id);
        }
    }

    public void deleteLanguage(Long id) throws ResourceNotFoundException {
        if(languageRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LANGUAGE, id);
        }
        else {
            languageRepository.deleteById(id);
        }
    }
}