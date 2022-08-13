package lazarus.restfulapi.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lazarus.restfulapi.library.model.entity.*;
import lazarus.restfulapi.library.model.enums.FormatType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(name = "Book")
public class BookDTO {
    @Schema(description = "Book's identifier", example = "1")
    private Long id;

    @Schema(description = "Book's title", example = "Spiders: Learning to Love Them")
    private String title;

    @Schema(description = "Book's authors", example = "Lynne Kelly")
    private List<Author> author;

    @Schema(description = "Languages the book is written in", example = "Esperanto")
    private List<Language> language;

    @Schema(description = "Book's publisher", example = "Auguste Poulet-Malassis")
    private Publisher publisher;

    @Schema(description = "Date of book's publication", example = "1965-07-31")
    private LocalDate publicationDate;

    @Schema(description = "Book's cover")
    private byte[] cover;

    @Schema(description = "Number of pages of the book", example = "322")
    private Integer numberOfPages;

    @Schema(description = "Book's format", example = "PAPERBACK")
    private FormatType formatType;

    @Schema(description = "Genres the book is written in", example = "Drama")
    private List<Genre> genre;

    @Schema(description = "Book's plot", example = "The book revolves around the Mehmed Paša Sokolović Bridge in Višegrad, which spans the Drina River and stands as a silent witness to history from its construction by the Ottomans in the mid-16th century until its partial destruction during World War I.")
    private String plot;

    @Schema(description = "Book's ISBN", example = "9780747532743")
    private Integer isbn;

    @Schema(description = "Library in which the book is currently present", example = "Belgrade library")
    private Library library;

    @Schema(description = "The person the book is currently rented to", example = "Peter Parker")
    private Rented rentedTo;
    //private boolean rented;

    @Schema(description = "Original title of the book", example = "Charlotte’s Web")
    private String titleOriginal;

    @Schema(description = "Language in which the book was originally published", example = "French")
    private List<Language> languageOriginal;

    @Schema(description = "Original publisher of the book", example = "Auguste Poulet-Malassis")
    private Publisher publisherOriginal;

    @Schema(description = "Original publication date of the book", example = "1965-07-31")
    private LocalDate publicationDateOriginal;

    @Schema(description = "Original book's cover")
    private byte[] coverOriginal;

    @Schema(description = "Number of pages of the original version of the book", example = "1337")
    private Integer numberOfPagesOriginal;
}
