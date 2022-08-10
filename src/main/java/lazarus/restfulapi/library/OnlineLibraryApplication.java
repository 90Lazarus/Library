package lazarus.restfulapi.library;

import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.entity.*;
import lazarus.restfulapi.library.model.enums.FormatType;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.repository.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

@SpringBootApplication
public class OnlineLibraryApplication {
	@Autowired private AuthorRepository authorRepository;
	@Autowired private BookRepository bookRepository;
	@Autowired private LanguageRepository languageRepository;
	@Autowired private GenreRepository genreRepository;
	@Autowired private LibraryRepository libraryRepository;
	@Autowired private LibraryWorkingTimeRepository libraryWorkingTimeRepository;
	@Autowired private PublisherRepository publisherRepository;
	@Autowired private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(OnlineLibraryApplication.class, args);
	}

	Library libraryBG = Library.builder()
			.name("Belgrade library")
			.yearEstablished(Year.of(1895))
			.address(new Address("Serbia", "", "Belgrade", "White Street", 26))
			.build();

	Library libraryNS = Library.builder()
			.name("Novi Sad library")
			.yearEstablished(Year.of(1923))
			.address(new Address("Serbia", "", "Novi Sad", "New Street", 86))
			.build();

	Library libraryNI = Library.builder()
			.name("Nis library")
			.yearEstablished(Year.of(1932))
			.address(new Address("Serbia", "", "Nis", "Nisa", 17))
			.build();

	LibraryWorkingTime LWHMonday = LibraryWorkingTime.builder()
			.dayOfWeek(DayOfWeek.MONDAY)
			.openingTime(Time.valueOf("08:00:00"))
			.closingTime(Time.valueOf("20:00:00"))
			.library(libraryBG)
			.build();

	LibraryWorkingTime LWHTuesday = LibraryWorkingTime.builder()
			.dayOfWeek(DayOfWeek.TUESDAY)
			.openingTime(Time.valueOf("08:00:00"))
			.closingTime(Time.valueOf("20:00:00"))
			.library(libraryBG)
			.build();

	LibraryWorkingTime LWHWednesday = LibraryWorkingTime.builder()
			.dayOfWeek(DayOfWeek.WEDNESDAY)
			.openingTime(Time.valueOf("08:00:00"))
			.closingTime(Time.valueOf("20:00:00"))
			.library(libraryBG)
			.build();

	Publisher publisher1 = Publisher.builder()
			.name("Bloomsbury")
			.build();

	Author author1 = Author.builder()
			.fullName("Joanne Rowling")
			.penName("J. K. Rowling")
			.dateOfBirth(LocalDate.of(1965, Month.JULY,31))
			.nationality("British")
			.gender(Gender.FEMALE)
			.occupation("author, philanthropist, film producer, television producer, screenwriter")
			.shortBio("Joanne Rowling, born 31 July 1965, also known by her pen name J. K. Rowling, is a British author and philanthropist. She wrote a seven-volume children's fantasy series, Harry Potter, published from 1997 to 2007. The series has sold over 500 million copies, been translated into at least 70 languages, and spawned a global media franchise including films and video games.")
			.build();

	Language languageEnglish = Language.builder()
			.name("English")
			.build();

	Genre genreFantasy = Genre.builder()
			.name("Fantasy")
			.description("Fantasy is a genre of speculative fiction involving magical elements, typically set in a fictional universe and sometimes inspired by mythology and folklore")
			.build();

	Book book1 = Book.builder()
			.title("Harry Potter and the Philosopher's Stone")
			.author(List.of(author1))
			.language(List.of(languageEnglish))
			.publisher(publisher1)
			.formatType(FormatType.HARDCOVER)
			.genre(List.of(genreFantasy))
			.build();

	Book book2 = Book.builder()
			.title("Harry Potter and the Philosopher's Stone")
			.author(List.of(author1))
			.language(List.of(languageEnglish))
			.publisher(publisher1)
			.formatType(FormatType.PAPERBACK)
			.genre(List.of(genreFantasy))
			.build();

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			libraryRepository.save(libraryBG);
			libraryRepository.save(libraryNS);
			libraryRepository.save(libraryNI);

			libraryWorkingTimeRepository.save(LWHMonday);
			libraryWorkingTimeRepository.save(LWHTuesday);
			libraryWorkingTimeRepository.save(LWHWednesday);

			publisherRepository.save(publisher1);
			authorRepository.save(author1);
			languageRepository.save(languageEnglish);
			genreRepository.save(genreFantasy);
			bookRepository.save(book1);
			bookRepository.save(book2);
		};
	}
}