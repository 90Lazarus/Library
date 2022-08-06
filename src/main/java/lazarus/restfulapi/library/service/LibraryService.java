package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.LibraryDTO;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.model.mapper.LibraryMapper;
import lazarus.restfulapi.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    @Autowired private LibraryRepository libraryRepository;
    @Autowired private LibraryMapper libraryMapper;

    public LibraryDTO createLibrary(LibraryDTO libraryDTO) {
        Library library = libraryMapper.toLibrary(libraryDTO);
        libraryRepository.save(library);
        return libraryMapper.toLibraryDTO(library);
    }

    public List<LibraryDTO> getAllLibraries(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        return libraryMapper.toLibraryDTOs(libraryRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList());
    }

    public LibraryDTO getLibraryById(Long id) throws ResourceNotFoundException {
        return libraryMapper.toLibraryDTO(libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id)));
    }

    public LibraryDTO updateLibrary(Long id, LibraryDTO newLibraryDTO) throws ResourceNotFoundException {
        Library oldLibrary = libraryMapper.toLibrary(newLibraryDTO);
        if (libraryRepository.findById(id).isPresent()) {
            oldLibrary.setName(newLibraryDTO.getName());
            oldLibrary.setYearEstablished(newLibraryDTO.getYearEstablished());
            oldLibrary.setAddress(newLibraryDTO.getAddress());
            oldLibrary.setWorkingTime(newLibraryDTO.getWorkingTime());
            oldLibrary.setWebsite(newLibraryDTO.getWebsite());
            libraryRepository.save(oldLibrary);
            return libraryMapper.toLibraryDTO(oldLibrary);
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id);
        }
    }

    public void deleteLibraryById(Long id) throws ResourceNotFoundException {
        if(libraryRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id);
        }
        else {
            libraryRepository.deleteById(id);
        }
    }
}