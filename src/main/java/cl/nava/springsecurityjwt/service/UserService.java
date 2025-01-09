package cl.nava.springsecurityjwt.service;

import cl.nava.springsecurityjwt.models.Users;
import cl.nava.springsecurityjwt.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private IUsersRepository usersRepo;

    @Autowired
    public UserService(IUsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public void create(Users users) {
        usersRepo.save(users);
    }

    public List<Users> readAll() {
        return usersRepo.findAll();
    }

    public Optional<Users> readOne(long id) {
        return usersRepo.findById(id);
    }

    public void update(Users users) {
        usersRepo.save(users);
    }

    public void delete(Long id) {
        usersRepo.deleteById(id);
    }

}
