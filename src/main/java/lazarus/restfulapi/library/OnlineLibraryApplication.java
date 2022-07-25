package lazarus.restfulapi.library;

import lazarus.restfulapi.library.model.embeddable.Address;
import lazarus.restfulapi.library.model.entity.*;
import lazarus.restfulapi.library.model.enumerated.FormatType;
import lazarus.restfulapi.library.model.enumerated.Gender;
import lazarus.restfulapi.library.repository.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

@SpringBootApplication
public class OnlineLibraryApplication {
	@Autowired private AuthorRepository authorRepository;
	@Autowired private BookRepository bookRepository;
	@Autowired private LanguageRepository languageRepository;
	@Autowired private LibraryRepository libraryRepository;
	@Autowired private PublisherRepository publisherRepository;
	@Autowired private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(OnlineLibraryApplication.class, args);
	}

	Library libraryBG = Library.builder()
			.name("Belgrade library")
			.yearEstablished(Year.of(1895))
			.address(new Address("Serbia", "Serbia", "Belgrade", "White Street", "26"))
			.build();

	Library libraryNS = Library.builder()
			.name("Novi Sad library")
			.yearEstablished(Year.of(1923))
			.address(new Address("Serbia", "Serbia", "Novi Sad", "New Street", "86"))
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

	Book book1 = Book.builder()
			.title("Harry Potter and the Philosopher's Stone")
			.author(Set.of(author1))
			.language(Set.of(languageEnglish))
			.publisher(publisher1)
			.formatType(FormatType.HARDCOVER)
			//.library(libraryBG)
			.build();

	Book book2 = Book.builder()
			.title("Harry Potter and the Philosopher's Stone")
			.author(Set.of(author1))
			.language(Set.of(languageEnglish))
			.publisher(publisher1)
			.formatType(FormatType.HARDCOVER)
			//.library(libraryBG)
			.build();

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			libraryRepository.save(libraryBG);
			libraryRepository.save(libraryNS);

			publisherRepository.save(publisher1);
			authorRepository.save(author1);
			languageRepository.save(languageEnglish);
			bookRepository.save(book1);
			bookRepository.save(book2);
		};
	}
}