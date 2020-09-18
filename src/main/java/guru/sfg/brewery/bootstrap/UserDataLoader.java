package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        if(authorityRepository.count() == 0) {
            loadAuthorities();
            loadUsers();
        }
    }

    private void loadUsers() {

        User spring = User
                .builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .authority(
                        authorityRepository.findByRole("ROLE_ADMIN").get()
                )
                .build();

        User user = User
                .builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authority(
                        authorityRepository.findByRole("ROLE_USER").get()
                )
                .build();

        User scott = User
                .builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(
                        authorityRepository.findByRole("ROLE_CUSTOMER").get()
                )
                .build();

        userRepository.save(spring);
        userRepository.save(user);
        userRepository.save(scott);

        System.out.println("Saved " + userRepository.count() + " users");

    }

    private void loadAuthorities() {

        Authority admin = Authority.builder().role("ROLE_ADMIN").build();
        Authority user = Authority.builder().role("ROLE_USER").build();
        Authority customer = Authority.builder().role("ROLE_CUSTOMER").build();

        authorityRepository.save(admin);
        authorityRepository.save(user);
        authorityRepository.save(customer);
    }
}
