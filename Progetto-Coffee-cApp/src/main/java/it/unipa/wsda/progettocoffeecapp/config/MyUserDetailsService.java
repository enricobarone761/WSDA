package it.unipa.wsda.progettocoffeecapp.config;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.repository.UtenteRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NullMarked
public class MyUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;
    public MyUserDetailsService(UtenteRepository userRepository) {
        this.utenteRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        return User
                .withUsername(utente.getUsername())
                .password(utente.getPassword())
                .roles(utente.getRuolo().name())
                .build();
    }

}

