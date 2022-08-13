package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "Author")
public class AuthorDTO {
    @Schema(description = "Author identifier", example = "1")
    private Long id;

    @Schema(description = "Author's full name", example = "Joanne Rowling")
    private String fullName;

    @Schema(description = "Author's pen name", example = "J. K. Rowling")
    private String penName;

    @Schema(description = "Author's date of birth", example = "31 July 1965")
    private LocalDate dateOfBirth;

    @Schema(description = "Author's date of death", example = "31 July 2065")
    private LocalDate dateOfDeath;

    @Schema(description = "Author's nationality", example = "British")
    private String nationality;

    @Schema(description = "Author's gender", example = "FEMALE")
    private Gender gender;

    @Schema(description = "Author's occupation", example = "Author, Philanthropist, Film producer, Television producer, Screenwriter")
    private String occupation;

    @Schema(description = "Short biography of the author's career", example = "J.K. Rowling, (born July 31, 1965, Yate, near Bristol, England), is a British author and the creator of the popular and critically acclaimed Harry Potter series about a young sorcerer in training.")
    private String shortBio;

    @Schema(description = "Author's photo")
    private byte[] photo;

    @Schema(description = "Author's Wikipedia website address", example = "https://en.wikipedia.org/wiki/J._K._Rowling")
    private String wikipediaPageAddress;

    @Schema(description = "Author's Goodreads website address", example = "https://www.goodreads.com/author/show/1077326.J_K_Rowling")
    private String goodreadsPageAddress;

    @Schema(description = "Author's website address", example = "http://jkrowling.com/")
    private String authorWebsiteAddress;

    //private List<Book> books;
}
