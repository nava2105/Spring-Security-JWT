package cl.nava.springsecurityjwt.repositories;

import cl.nava.springsecurityjwt.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {
    // Method to search for a role by name in our database
    Optional<Roles> findByName(String name);
}
