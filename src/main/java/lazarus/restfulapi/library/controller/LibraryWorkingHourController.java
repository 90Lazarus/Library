package lazarus.restfulapi.library.controller;

import lazarus.restfulapi.library.service.LibraryWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryWorkingHourController {

    @Autowired
    private LibraryWorkingHourService libraryWorkingHourService;
}
