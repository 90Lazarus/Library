package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.dto.LibraryWorkingTimeDTO;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lazarus.restfulapi.library.model.mapper.LibraryWorkingTimeMapper;
import lazarus.restfulapi.library.repository.LibraryRepository;
import lazarus.restfulapi.library.repository.LibraryWorkingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryWorkingTimeService {
    @Autowired private LibraryRepository libraryRepository;
    @Autowired private LibraryWorkingTimeRepository libraryWorkingTimeRepository;
    @Autowired private LibraryWorkingTimeMapper libraryWorkingTimeMapper;

    public LibraryWorkingTimeDTO createALibraryWorkingTime(Long libraryId, LibraryWorkingTimeDTO libraryWorkingTimeDTO) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
                LibraryWorkingTime libraryWorkingTime = libraryWorkingTimeMapper.libraryWorkingTimeDTOtoLibraryWorkingTime(libraryWorkingTimeDTO);
                libraryWorkingTime.setLibrary(libraryRepository.findById(libraryId).get());
                libraryWorkingTimeRepository.save(libraryWorkingTime);
                return libraryWorkingTimeMapper.libraryWorkingTimeToLibraryWorkingTimeDTO(libraryWorkingTime);
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public List<LibraryWorkingTimeDTO> readLibraryWorkingTime(Long libraryId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (!(libraryRepository.findById(libraryId).get().getWorkingTime().isEmpty())) {
                return libraryWorkingTimeMapper.libraryWorkingTimeToLibraryWorkingTimeDTOs(libraryRepository.findById(libraryId).get().getWorkingTime());
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_TIME);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public LibraryWorkingTimeDTO readALibraryWorkingTime(Long libraryId, Long libraryWorkingTimeId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if(libraryWorkingTimeRepository.existsByIdAndLibrary_Id(libraryWorkingTimeId, libraryId)) {
                return libraryWorkingTimeMapper.libraryWorkingTimeToLibraryWorkingTimeDTO(libraryWorkingTimeRepository.findByIdAndLibrary_Id(libraryWorkingTimeId, libraryId));
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public LibraryWorkingTimeDTO updateALibraryWorkingTime(Long libraryId, Long libraryWorkingTimeId, LibraryWorkingTimeDTO libraryWorkingTimeDTO) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (libraryWorkingTimeRepository.findById(libraryWorkingTimeId).isPresent()) {
                if (libraryWorkingTimeRepository.existsByIdAndLibrary_Id(libraryWorkingTimeId, libraryId)) {
                    LibraryWorkingTime newLibraryWorkingTime = libraryWorkingTimeMapper.libraryWorkingTimeDTOtoLibraryWorkingTime(libraryWorkingTimeDTO);
                    LibraryWorkingTime oldLibraryWorkingTime = libraryWorkingTimeRepository.findById(libraryWorkingTimeId).get();
                    oldLibraryWorkingTime.setDayOfWeek(newLibraryWorkingTime.getDayOfWeek());
                    oldLibraryWorkingTime.setOpeningTime(newLibraryWorkingTime.getOpeningTime());
                    oldLibraryWorkingTime.setClosingTime(newLibraryWorkingTime.getClosingTime());
                    libraryWorkingTimeRepository.save(oldLibraryWorkingTime);
                    return libraryWorkingTimeMapper.libraryWorkingTimeToLibraryWorkingTimeDTO(oldLibraryWorkingTime);
                } else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public void deleteALibraryWorkingTime(Long libraryId, Long libraryWorkingTimeId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (libraryWorkingTimeRepository.findById(libraryWorkingTimeId).isPresent()) {
                if (libraryWorkingTimeRepository.existsByIdAndLibrary_Id(libraryWorkingTimeId, libraryId)) {
                    libraryWorkingTimeRepository.deleteById(libraryWorkingTimeId);
                } else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
                }
            } else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
            }
        } else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }
}