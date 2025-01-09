package cl.nava.springsecurityjwt.factories;

import cl.nava.springsecurityjwt.models.Users;
import cl.nava.springsecurityjwt.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFactory implements IUserFactory {

    private final IUsersRepository usersRepository;

    @Autowired
    public UserFactory(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void create(Users user) {
        usersRepository.save(user);
    }

    @Override
    public Optional<Users> findByUserName(String username) {
        return usersRepository.findByUserName(username);
    }

    @Override
    public Optional<Users> findById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public boolean existsByUserName(String username) {
        return usersRepository.existsByUserName(username);
    }

    @Override
    public void update(Users user) {
        usersRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }
}