package cl.nava.springsecurityjwt.factories;

import cl.nava.springsecurityjwt.models.Roles;
import cl.nava.springsecurityjwt.repositories.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleFactory implements IRoleFactory {

    private final IRolesRepository rolesRepository;

    @Autowired
    public RoleFactory(IRolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Optional<Roles> findByName(String name) {
        return rolesRepository.findByName(name);
    }

    @Override
    public void save(Roles role) {
        rolesRepository.save(role);
    }
}