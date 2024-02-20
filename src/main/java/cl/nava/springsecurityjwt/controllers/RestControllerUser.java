package cl.nava.springsecurityjwt.controllers;

import cl.nava.springsecurityjwt.models.Usuarios;
import cl.nava.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
public class RestControllerUser {
    private UserService userService;

    @Autowired
    public RestControllerUser(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void crearUsuario(@RequestBody Usuarios usuarios) {
        userService.crear(usuarios);
    }

    @GetMapping(value = "listar", headers = "Accept=application/json")
    public List<Usuarios> listarCelulares() {
        return userService.readAll();
    }

    @GetMapping(value = "listarId/{id}", headers = "Accept=application/json")
    public Optional<Usuarios> obtenerUsuarioPorId(@PathVariable Long id) {
        return userService.readOne(id);
    }

    @PutMapping(value = "actualizar", headers = "Accept=application/json")
    public void actualizarUsuario(@RequestBody Usuarios usuarios) {
        userService.update(usuarios);
    }

    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void eliminarUsuario(@PathVariable Long id) {
        userService.delete(id);
    }
}
