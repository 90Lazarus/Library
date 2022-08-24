package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.LanguageDTO;
import lazarus.restfulapi.library.model.entity.Language;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageDTO languageToLanguageDTO(Language language);
    List<LanguageDTO> languagesToLanguageDTOs(List<Language> languages);
    Language languageDTOToLanguage(LanguageDTO languageDTO);
}