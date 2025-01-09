package cl.nava.springsecurityjwt.service;

import cl.nava.springsecurityjwt.models.Users;
import cl.nava.springsecurityjwt.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private IUsersRepository usuariosRepo;

    @Autowired
    public UserService(IUsersRepository usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
    }

    public void crear(Users users) {
        usuariosRepo.save(users);
    }

    public List<Users> readAll() {
        return usuariosRepo.findAll();
    }

    public Optional<Users> readOne(long id) {
        return usuariosRepo.findById(id);
    }

    public void update(Users users) {
        usuariosRepo.save(users);
    }

    public void delete(Long id) {
        usuariosRepo.deleteById(id);
    }

}
