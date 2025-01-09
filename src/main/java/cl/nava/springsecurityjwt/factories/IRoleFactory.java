package cl.nava.springsecurityjwt.factories;

import cl.nava.springsecurityjwt.models.Roles;

import java.util.Optional;

public interface IRoleFactory {
    Optional<Roles> findByName(String name);
    void save(Roles role);
}