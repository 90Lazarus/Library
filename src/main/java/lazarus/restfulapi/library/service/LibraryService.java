package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.Book;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    @Autowired private LibraryRepository libraryRepository;

    public Library createLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    public Library getLibraryById(Long id) throws ResourceNotFoundException {
        return libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY));
    }

    public void addBook(Library library, Book book) {
        //
    }

    public Library updateLibrary(Long id, Library newLibrary) {
        Library oldLibrary = libraryRepository.findById(id).get();
        oldLibrary.setName(newLibrary.getName());
        //
        return libraryRepository.save(oldLibrary);
    }

    public void deleteLibrary(Library library) {
        libraryRepository.delete(library);
    }

    public void deleteLibraryById(Long id) {
        if (!libraryRepository.existsById(id)) {
            //
        }
        else libraryRepository.deleteById(id);
    }
}