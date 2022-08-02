package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.exception.ErrorInfo;
import lazarus.restfulapi.library.exception.ResourceNotFoundException;
import lazarus.restfulapi.library.model.entity.LibraryWorkingHour;
import lazarus.restfulapi.library.repository.LibraryWorkingHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryWorkingHourService {
    @Autowired
    private LibraryWorkingHourRepository libraryWorkHoursRepository;

    public LibraryWorkingHour createLibraryWorkingHour(LibraryWorkingHour libraryWorkingHour) {
        return libraryWorkHoursRepository.save(libraryWorkingHour);
    }

    public List<LibraryWorkingHour> readLibraryWorkingHours() {
        return libraryWorkHoursRepository.findAll();
    }

    public LibraryWorkingHour updateLibraryWorkingHour(Long id, LibraryWorkingHour newLibraryWorkingHour) throws ResourceNotFoundException {
        LibraryWorkingHour oldLibraryWorkingHour;
        if (libraryWorkHoursRepository.findById(id).isPresent()) {
            oldLibraryWorkingHour = libraryWorkHoursRepository.findById(id).get();
            oldLibraryWorkingHour.setDayOfWeek(newLibraryWorkingHour.getDayOfWeek());
            oldLibraryWorkingHour.setOpeningTime(newLibraryWorkingHour.getOpeningTime());
            oldLibraryWorkingHour.setClosingTime(newLibraryWorkingHour.getClosingTime());
            return libraryWorkHoursRepository.save(oldLibraryWorkingHour);
        }
        else {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_HOUR);
        }
    }

    public void deleteLibraryWorkingHourById(Long id) throws ResourceNotFoundException {
        if (libraryWorkHoursRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(ErrorInfo.ResourceType.LIBRARY_WORKING_HOUR);
        } else {
            libraryWorkHoursRepository.deleteById(id);
        }
    }
}
