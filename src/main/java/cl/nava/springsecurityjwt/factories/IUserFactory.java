package cl.nava.springsecurityjwt.factories;

import cl.nava.springsecurityjwt.models.Users;

import java.util.Optional;

public interface IUserFactory {
    void create(Users user);
    Optional<Users> findByUserName(String username);
    Optional<Users> findById(Long id);
    boolean existsByUserName(String username);
    void update(Users user);
    void deleteById(Long id);
}