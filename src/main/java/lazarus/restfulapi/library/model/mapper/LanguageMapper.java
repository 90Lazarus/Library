package lazarus.restfulapi.library.model.mapper;

import lazarus.restfulapi.library.model.dto.LanguageDTO;
import lazarus.restfulapi.library.model.entity.Language;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageDTO toLanguageDTO(Language language);
    List<LanguageDTO> toLanguageDTOs(List<Language> languages);
    Language toLanguage(LanguageDTO languageDTO);
}
