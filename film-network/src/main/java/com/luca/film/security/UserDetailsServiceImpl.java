//package com.luca.film.security;
//
//import com.luca.film.user.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
////@Service
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    private final UserRepository repository;
//
//    public UserDetailsServiceImpl(UserRepository repository) {
//        this.repository = repository;
//    }
//
//    /**
//     * Load the user by email from the database
//     * @param userEmail the email of the user
//     * @return the user details
//     * @throws UsernameNotFoundException if the user is not found
//     */
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        return repository.findByEmail(userEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
//}
