package cl.nava.springsecurityjwt.initializers;

import cl.nava.springsecurityjwt.models.Roles;
import cl.nava.springsecurityjwt.models.Users;
import cl.nava.springsecurityjwt.repositories.IRolesRepository;
import cl.nava.springsecurityjwt.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Order(2)
public class AdminInitializer implements CommandLineRunner {

    private final IRolesRepository rolesRepository;
    private final IUsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInitializer(IRolesRepository rolesRepository, IUsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Check if the role "ADMIN" exists, if not, it is created.
        if (usersRepository.findByUserName("admin").isEmpty()) {
            Users adminUser = new Users();
            adminUser.setUserName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            Roles adminRole = rolesRepository.findByName("ADMIN").orElseThrow(() -> new IllegalArgumentException("Role USER not found"));
            adminUser.setRoles(Collections.singletonList(adminRole));
            usersRepository.save(adminUser);
            System.out.println("User 'ADMIN' created.");
        }
    }

}
