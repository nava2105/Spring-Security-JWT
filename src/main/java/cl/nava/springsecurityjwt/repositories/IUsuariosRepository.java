package cl.nava.springsecurityjwt.repositories;

import cl.nava.springsecurityjwt.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    //Método para busar un usuario mediante su nombre
    Optional<Usuarios> findByUserName(String username);
    //Método para poder verificar si un usuario existe en nuestra base de datos
    Boolean existsByUserName(String username);
}
