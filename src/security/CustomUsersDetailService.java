package cl.nava.springsecurityjwt.security;

import cl.nava.springsecurityjwt.models.Roles;
import cl.nava.springsecurityjwt.models.Usuarios;
import cl.nava.springsecurityjwt.repositories.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailService implements UserDetailsService {
    private IUsuariosRepository usuariosRepo;

    @Autowired
    public CustomUsersDetailService(IUsuariosRepository usuariosRepo) {
        this.usuariosRepo = usuariosRepo;
    }

    //Metodo para traer una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(List<Roles> roles){
        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    //Metodo para traernos un usuario con todos sus datos por medio de un username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = usuariosRepo.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new User(usuarios.getUserName(), usuarios.getContrasena(), mapToAuthorities(usuarios.getRoles()));
    }
}
