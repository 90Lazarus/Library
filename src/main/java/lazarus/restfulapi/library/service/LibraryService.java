package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.LibraryDTO;
import lazarus.restfulapi.library.model.entity.Library;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lazarus.restfulapi.library.model.mapper.LibraryMapper;
import lazarus.restfulapi.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    @Autowired private LibraryRepository libraryRepository;
    @Autowired private LibraryMapper libraryMapper;

    private boolean checkIfLibraryIsOpen(Long id) {
        Library library = libraryRepository.getReferenceById(id);
        boolean isOpen = false;
        for (LibraryWorkingTime libraryWorkingTime : library.getWorkingTime()) {
            if (LocalDate.now().getDayOfWeek() == libraryWorkingTime.getDayOfWeek()) {
                if (LocalTime.now().isAfter(libraryWorkingTime.getOpeningTime().toLocalTime()) && LocalTime.now().isBefore(libraryWorkingTime.getClosingTime().toLocalTime())) {
                    isOpen = true;
                }
            }
        }
        return isOpen;
    }

    public LibraryDTO createLibrary(LibraryDTO libraryDTO) {
        Library library = libraryMapper.toLibrary(libraryDTO);
        libraryRepository.save(library);
        return libraryMapper.toLibraryDTO(library);
    }

    public List<LibraryDTO> getAllLibraries(Integer page, Integer size, Sort.Direction direction, String sortBy) {
        List<Library> libraries = libraryRepository.findAll(PageRequest.of(page, size, direction, sortBy)).toList();
        for (Library library : libraries) {
            library.setOpen(checkIfLibraryIsOpen(library.getId())); //sorting doesn't work on transient field
        }
        return libraryMapper.toLibraryDTOs(libraries);
    }

    public LibraryDTO getLibraryById(Long id) throws ResourceNotFoundException {
        Library library = libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, id));
        library.setOpen(checkIfLibraryIsOpen(library.getId()));
        return libraryMapper.toLibraryDTO(library);
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