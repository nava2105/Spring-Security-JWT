package cl.nava.springsecurityjwt.controllers;

import cl.nava.springsecurityjwt.dtos.DtoAuthResponse;
import cl.nava.springsecurityjwt.dtos.DtoLogin;
import cl.nava.springsecurityjwt.dtos.DtoRegister;
import cl.nava.springsecurityjwt.models.Roles;
import cl.nava.springsecurityjwt.models.Users;
import cl.nava.springsecurityjwt.repositories.IRolesRepository;
import cl.nava.springsecurityjwt.repositories.IUsersRepository;
import cl.nava.springsecurityjwt.security.JwtGenerador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository rolesRepository;
    private IUsersRepository usuariosRepository;
    private JwtGenerador jwtGenerador;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsersRepository usuariosRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuariosRepository = usuariosRepository;
        this.jwtGenerador = jwtGenerador;
    }
    // Method to be able to register users with role user
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody DtoRegister dtoRegister){
        if (usuariosRepository.existsByUserName(dtoRegister.getUsername())) {
            return new ResponseEntity<>("The user already exists, try another one", HttpStatus.BAD_REQUEST);
        }
        Users users = new Users();
        users.setUserName(dtoRegister.getUsername());
        users.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));
        Roles roles = rolesRepository.findByName("USER").get();
        users.setRoles(Collections.singletonList(roles));
        usuariosRepository.save(users);
        return new ResponseEntity<>("Successful user registration", HttpStatus.OK);
    }
    // Method to be able to register users with admin role
    @PostMapping("registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody DtoRegister dtoRegister){
        if (usuariosRepository.existsByUserName(dtoRegister.getUsername())) {
            return new ResponseEntity<>("The user already exists, try another one", HttpStatus.BAD_REQUEST);
        }
        Users users = new Users();
        users.setUserName(dtoRegister.getUsername());
        users.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));
        Roles roles = rolesRepository.findByName("ADMIN").get();
        users.setRoles(Collections.singletonList(roles));
        usuariosRepository.save(users);
        return new ResponseEntity<>("Successful admin registration", HttpStatus.OK);
    }
    // Method to be able to log in a user and get a token
    @PostMapping("login")
    public ResponseEntity<DtoAuthResponse> login(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(),dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generateToken(authentication);
        return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
    }
}
