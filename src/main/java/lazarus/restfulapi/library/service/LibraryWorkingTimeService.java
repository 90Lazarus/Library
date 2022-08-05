package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.LibraryWorkingTime;
import lazarus.restfulapi.library.repository.LibraryRepository;
import lazarus.restfulapi.library.repository.LibraryWorkingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryWorkingTimeService {

    @Autowired private LibraryRepository libraryRepository;
    @Autowired private LibraryWorkingTimeRepository libraryWorkingTimeRepository;

    public List<LibraryWorkingTime> createLibraryWorkingTimeById(Long libraryId, LibraryWorkingTime libraryWorkingTime) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
                libraryWorkingTime.setLibrary(libraryRepository.findById(libraryId).get());
                libraryWorkingTimeRepository.save(libraryWorkingTime);
                return libraryRepository.findById(libraryId).get().getWorkingTime();
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }

    }

    public List<LibraryWorkingTime> readLibraryWorkingTime(Long libraryId) throws ResourceNotFoundException {
        if(libraryRepository.findById(libraryId).isPresent()) {
            return libraryRepository.findById(libraryId).get().getWorkingTime();
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public LibraryWorkingTime readLibraryWorkingTimeById(Long libraryId, Long libraryWorkingTimeId) throws ResourceNotFoundException {
        if(libraryRepository.findById(libraryId).isPresent()) {
            if(libraryWorkingTimeRepository.existsByIdAndLibrary_Id(libraryWorkingTimeId, libraryId)) {
                return libraryWorkingTimeRepository.findByIdAndLibrary_Id(libraryWorkingTimeId, libraryId);
            }
            else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
            }
        }
            else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public LibraryWorkingTime updateLibraryWorkingTime(Long libraryId, Long libraryWorkingTimeId, LibraryWorkingTime newLibraryWorkingTime) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (libraryWorkingTimeRepository.findById(libraryWorkingTimeId).isPresent()) {
                if(libraryWorkingTimeRepository.existsByIdAndLibrary_Id(libraryWorkingTimeId, libraryId)) {
                    LibraryWorkingTime oldLibraryWorkingTime;
                    oldLibraryWorkingTime = libraryWorkingTimeRepository.findById(libraryWorkingTimeId).get();
                    oldLibraryWorkingTime.setDayOfWeek(newLibraryWorkingTime.getDayOfWeek());
                    oldLibraryWorkingTime.setOpeningTime(newLibraryWorkingTime.getOpeningTime());
                    oldLibraryWorkingTime.setClosingTime(newLibraryWorkingTime.getClosingTime());
                    return libraryWorkingTimeRepository.save(oldLibraryWorkingTime);
                }
                else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
                }
            }
            else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
            }
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }

    public void deleteLibraryWorkingTime(Long libraryId, Long libraryWorkingTimeId) throws ResourceNotFoundException {
        if (libraryRepository.findById(libraryId).isPresent()) {
            if (libraryWorkingTimeRepository.findById(libraryWorkingTimeId).isPresent()) {
                if(libraryWorkingTimeRepository.existsByIdAndLibrary_Id(libraryWorkingTimeId, libraryId)) {
                    libraryWorkingTimeRepository.deleteById(libraryWorkingTimeId);
                }
                else {
                    throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId, ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
                }
            }
            else {
                throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_TIME, libraryWorkingTimeId);
            }
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY, libraryId);
        }
    }
}
