package lazarus.restfulapi.library;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lazarus.restfulapi.library.model.embed.Address;
import lazarus.restfulapi.library.model.entity.*;
import lazarus.restfulapi.library.model.enums.FormatType;
import lazarus.restfulapi.library.model.enums.Gender;
import lazarus.restfulapi.library.model.enums.Role;
import lazarus.restfulapi.library.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Time;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OnlineLibraryApplication {
	@Autowired private AuthorRepository authorRepository;
	@Autowired private BookRepository bookRepository;
	@Autowired private LanguageRepository languageRepository;
	@Autowired private GenreRepository genreRepository;
	@Autowired private LibraryRepository libraryRepository;
	@Autowired private LibraryWorkingTimeRepository libraryWorkingTimeRepository;
	@Autowired private PublisherRepository publisherRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private RentedRepository rentedRepository;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Bean
	public OpenAPI freelanceOpenAPI() {
		return new OpenAPI().info(new Info().title("Library API")
						.description("REST API for an online library")
						.version("v0.0.1"));
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineLibraryApplication.class, args);
	}

	Library libraryMedijana = Library.builder()
			.name("Biblioteka Medijana")
			.yearEstablished(Year.of(1895))
			.address(new Address("Serbia", "", "Nis", "Medijana Street", 26))
			.build();

	Library libraryPantelej = Library.builder()
			.name("Biblioteka Pantelej")
			.yearEstablished(Year.of(1923))
			.address(new Address("Serbia", "", "Nis", "Pantelej Street", 86))
			.build();

	Library libraryPalilula = Library.builder()
			.name("Biblioteka Palilula")
			.yearEstablished(Year.of(1932))
			.address(new Address("Serbia", "", "Nis", "Palilula Street", 17))
			.build();

	LibraryWorkingTime LWHMonday = LibraryWorkingTime.builder()
			.dayOfWeek(DayOfWeek.MONDAY)
			.openingTime(Time.valueOf("08:00:00"))
			.closingTime(Time.valueOf("20:00:00"))
			.library(libraryMedijana)
			.build();

	LibraryWorkingTime LWHTuesday = LibraryWorkingTime.builder()
			.dayOfWeek(DayOfWeek.TUESDAY)
			.openingTime(Time.valueOf("08:00:00"))
			.closingTime(Time.valueOf("20:00:00"))
			.library(libraryMedijana)
			.build();

	LibraryWorkingTime LWHWednesday = LibraryWorkingTime.builder()
			.dayOfWeek(DayOfWeek.WEDNESDAY)
			.openingTime(Time.valueOf("08:00:00"))
			.closingTime(Time.valueOf("20:00:00"))
			.library(libraryMedijana)
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

	Author author2 = Author.builder()
			.fullName("Ivo Andrić")
			.nationality("Yugoslav")
			.gender(Gender.MALE)
			.build();

	List<Language> languages = new ArrayList<>();
	Language languageEnglish = Language.builder().name("English").build();
	Language languageFrench = Language.builder().name("French").build();
	Language languageSpanish = Language.builder().name("Spanish").build();
	Language languageSerbian = Language.builder().name("Serbian").build();

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
			.library(libraryMedijana)
			.languageOriginal(List.of(languageFrench))
			.publisherOriginal(publisher1)
			.build();

	Book book2 = Book.builder()
			.title("Harry Potter and the Philosopher's Stone")
			.author(List.of(author1))
			.language(List.of(languageEnglish))
			.publisher(publisher1)
			.formatType(FormatType.PAPERBACK)
			.genre(List.of(genreFantasy))
			.library(libraryMedijana)
			.adult(true)
			.build();

	User user1 = User.builder()
			.email("shepard@gmail.com")
			.password(passwordEncoder.encode("spectre"))
			.firstName("Commander")
			.lastName("Shepard")
			.dateOfBirth(LocalDate.of(2000, Month.APRIL, 11))
			.gender(Gender.OTHER)
			.role(Role.ADMIN)
			.build();

	User user2 = User.builder()
			.email("joker@gmail.com")
			.password(passwordEncoder.encode("vrolik"))
			.firstName("Jeff")
			.lastName("Moreau")
			.dateOfBirth(LocalDate.of(2001, Month.MAY, 14))
			.gender(Gender.MALE)
			.role(Role.STAFF)
			.build();

	User user3 = User.builder()
			.email("tali@gmail.com")
			.password(passwordEncoder.encode("keelahselai"))
			.firstName("Tali'Zorah")
			.lastName("nar Rayya")
			.dateOfBirth(LocalDate.of(2002, Month.DECEMBER, 21))
			.gender(Gender.FEMALE)
			.role(Role.USER)
			.build();

	User user4 = User.builder()
			.email("allers@gmail.com")
			.password(passwordEncoder.encode("allers"))
			.firstName("Diana")
			.lastName("Allers")
			.dateOfBirth(LocalDate.of(2006, Month.NOVEMBER, 13))
			.gender(Gender.FEMALE)
			.role(Role.GUEST)
			.build();

	Rented rented1 = Rented.builder()
			.dateRented(LocalDateTime.of(LocalDate.of(2022, Month.AUGUST, 12), LocalTime.of(12, 56, 17)))
			.dateReturned(LocalDateTime.of(LocalDate.of(2022, Month.AUGUST, 18), LocalTime.of(18, 14, 56)))
			.user(user1)
			.book(book1)
			.build();

	Rented rented2 = Rented.builder()
			.dateRented(LocalDateTime.of(LocalDate.of(2022, Month.AUGUST, 12), LocalTime.of(14, 15, 56)))
			.user(user1)
			.book(book2)
			.build();

	Rented rented3 = Rented.builder()
			.dateRented(LocalDateTime.of(LocalDate.of(2022, Month.AUGUST, 19), LocalTime.of(11, 56, 54)))
			.user(user2)
			.book(book1)
			.build();

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			log.info("Initializing Library database...");

			log.info("Initializing Libraries...");
			libraryRepository.save(libraryMedijana);
			libraryRepository.save(libraryPantelej);
			libraryRepository.save(libraryPalilula);
			log.info("Libraries successfully initialized!");

			log.info("Initializing Working Time for Libraries...");
			libraryWorkingTimeRepository.save(LWHMonday);
			libraryWorkingTimeRepository.save(LWHTuesday);
			libraryWorkingTimeRepository.save(LWHWednesday);
			log.info("Library Working Time successfully initialized!");

			log.info("Initializing Publishers...");
			publisherRepository.save(publisher1);
			log.info("Publishers successfully initialized!");

			log.info("Initializing Authors...");
			authorRepository.save(author1);
			authorRepository.save(author2);
			log.info("Authors successfully initialized!");

			log.info("Initializing Languages...");
			languages.add(languageEnglish);
			languages.add(languageFrench);
			languages.add(languageSpanish);
			languages.add(languageSerbian);
			languageRepository.saveAll(languages);
			log.info("Languages successfully initialized!");

			log.info("Initializing Genres...");
			genreRepository.save(genreFantasy);
			log.info("Genres successfully initialized!");

			log.info("Initializing Books...");
			bookRepository.save(book1);
			bookRepository.save(book2);
			log.info("Books successfully initialized!");

			log.info("Initializing Users...");
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);
			log.info("Users successfully initialized!");

			log.info("Initializing Renting...");
			rentedRepository.save(rented1);
			book1.setRented(true);
			bookRepository.save(book1);
			book1.setRented(false);
			bookRepository.save(book1);

			rentedRepository.save(rented2);
			book2.setRented(true);
			bookRepository.save(book2);

			rentedRepository.save(rented3);
			book1.setRented(true);
			bookRepository.save(book1);
			log.info("Renting successfully initialized!");

			log.info("Library database successfully initialized!");
		};
	}
}