package lazarus.restfulapi.library.service;

import lazarus.restfulapi.library.model.security.LibraryUserDetails;
import lazarus.restfulapi.library.model.entity.User;
import lazarus.restfulapi.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            return new LibraryUserDetails(user);
        } else {
                throw new UsernameNotFoundException("Could not find user");
            }
    }
}
