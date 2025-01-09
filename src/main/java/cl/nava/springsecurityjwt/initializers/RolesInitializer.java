package cl.nava.springsecurityjwt.initializers;

import cl.nava.springsecurityjwt.models.Roles;
import cl.nava.springsecurityjwt.repositories.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RolesInitializer implements CommandLineRunner {

    private final IRolesRepository rolesRepository;

    @Autowired
    public RolesInitializer(IRolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void run(String... args) {
        // Check if the role "ADMIN" exists, if not, it is created.
        if (rolesRepository.findByName("ADMIN").isEmpty()) {
            Roles adminRole = new Roles();
            adminRole.setName("ADMIN");
            rolesRepository.save(adminRole);
            System.out.println("Role 'ADMIN' created.");
        }
        // Check if the role "USER" exists, if not, it is created.
        if (rolesRepository.findByName("USER").isEmpty()) {
            Roles userRole = new Roles();
            userRole.setName("USER");
            rolesRepository.save(userRole);
            System.out.println("Role 'USER' created.");
        }
    }
}