package lazarus.restfulapi.library.model.dto;

import com.fasterxml.jackson.annotation.*;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AuthorDTO {
    private Long id;
    private String fullName;
    private String penName;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String nationality;
    private Gender gender;
    private String occupation;
    private String shortBio;
    private byte[] photo;
    private String wikipediaPageAddress;
    private String goodreadsPageAddress;
    private String authorWebsiteAddress;
    @JsonIgnore
    private List<Book> books;
}
