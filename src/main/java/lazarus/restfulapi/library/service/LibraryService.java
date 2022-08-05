package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    public Library createLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public List<Library> getAllLibraries(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        return libraryRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
    }

    public Library getLibraryById(Long id) throws ResourceNotFoundException {
        return libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id));
    }

    public Library updateLibrary(Long id, Library newLibrary) throws ResourceNotFoundException {
        Library oldLibrary;
        if (libraryRepository.findById(id).isPresent()) {
            oldLibrary = libraryRepository.findById(id).get();
            oldLibrary.setName(newLibrary.getName());
            oldLibrary.setYearEstablished(newLibrary.getYearEstablished());
            oldLibrary.setAddress(newLibrary.getAddress());
            oldLibrary.setWorkingTime(newLibrary.getWorkingTime());
            oldLibrary.setWebsite(newLibrary.getWebsite());
            return libraryRepository.save(oldLibrary);
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY);
        }
    }

    public void deleteLibrary(Library library) {
        libraryRepository.delete(library);
    }

    public void deleteLibraryById(Long id) throws ResourceNotFoundException {
        if(libraryRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY);
        }
        else {
            libraryRepository.deleteById(id);
        }
    }
}