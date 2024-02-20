package cl.nava.springsecurityjwt.service;

import cl.nava.springsecurityjwt.models.Usuarios;
import cl.nava.springsecurityjwt.repositories.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private IUsuariosRepository usuariosRepo;

    @Autowired
    public UserService(IUsuariosRepository usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
    }

    public void crear(Usuarios usuarios) {
        usuariosRepo.save(usuarios);
    }

    public List<Usuarios> readAll() {
        return usuariosRepo.findAll();
    }

    public Optional<Usuarios> readOne(long id) {
        return usuariosRepo.findById(id);
    }

    public void update(Usuarios usuarios) {
        usuariosRepo.save(usuarios);
    }

    public void delete(Long id) {
        usuariosRepo.deleteById(id);
    }

}
