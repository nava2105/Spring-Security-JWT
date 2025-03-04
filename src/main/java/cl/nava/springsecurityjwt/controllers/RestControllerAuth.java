package cl.nava.springsecurityjwt.controllers;

import cl.nava.springsecurityjwt.dtos.*;
import cl.nava.springsecurityjwt.factories.IRoleFactory;
import cl.nava.springsecurityjwt.factories.IUserFactory;
import cl.nava.springsecurityjwt.models.RolesModel;
import cl.nava.springsecurityjwt.models.UsersModel;
import cl.nava.springsecurityjwt.security.JwtGenerador;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final IRoleFactory roleFactory;
    private final IUserFactory userFactory;
    private final JwtGenerador jwtGenerador;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                              IRoleFactory roleFactory, IUserFactory userFactory, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleFactory = roleFactory;
        this.userFactory = userFactory;
        this.jwtGenerador = jwtGenerador;
    }

    // Method to register users with the "USER" role
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody DtoRegister dtoRegister) {
        if (userFactory.existsByUserName(dtoRegister.getUsername())) {
            return new ResponseEntity<>("The user already exists, try another one", HttpStatus.BAD_REQUEST);
        }
        UsersModel users = new UsersModel();
        users.setUserName(dtoRegister.getUsername());
        users.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));
        RolesModel roles = roleFactory.findByName("USER").orElseThrow(() -> new IllegalArgumentException("Role USER not found"));
        users.setRoles(Collections.singletonList(roles));
        userFactory.create(users);
        return new ResponseEntity<>("Successful user registration", HttpStatus.OK);
    }

    // Method to register users with the "ADMIN" role
    @PostMapping("register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody DtoRegister dtoRegister) {
        if (userFactory.existsByUserName(dtoRegister.getUsername())) {
            return new ResponseEntity<>("The user already exists, try another one", HttpStatus.BAD_REQUEST);
        }
        UsersModel users = new UsersModel();
        users.setUserName(dtoRegister.getUsername());
        users.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));
        RolesModel roles = roleFactory.findByName("ADMIN").orElseThrow(() -> new IllegalArgumentException("Role ADMIN not found"));
        users.setRoles(Collections.singletonList(roles));
        userFactory.create(users);
        return new ResponseEntity<>("Successful admin registration", HttpStatus.OK);
    }

    // Method to log in a user and generate a token
    @PostMapping("login")
    public ResponseEntity<DtoAuthResponse> login(@RequestBody DtoLogin dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dtoLogin.getUsername(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generateToken(authentication);
        return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
    }

    // Method to update password from user ID stored in token
    @PostMapping("password/update")
    public ResponseEntity<String> updatePassword(HttpServletRequest request, @RequestBody DtoChangePassword dtoChangePassword) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Missing or invalid Authorization header", HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);
        try {
            if (!jwtGenerador.validateToken(token)) {
                return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
            }
            String username = jwtGenerador.getUserNameFromJwt(token);
            UsersModel user = userFactory.findByUserName(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            String encodedPassword = passwordEncoder.encode(dtoChangePassword.getPassword());
            user.setPassword(encodedPassword);
            userFactory.update(user);
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating password: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to extract user ID from token
    @GetMapping("user_id/token")
    public ResponseEntity<DtoUserIdFromToken> userIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            if (jwtGenerador.validateToken(token)) {
                String username = jwtGenerador.getUserNameFromJwt(token);
                Long userId = userFactory.findByUserName(username)
                        .map(UsersModel::getUserId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                DtoUserIdFromToken dtoUserIdFromToken = new DtoUserIdFromToken(userId);
                return new ResponseEntity<>(dtoUserIdFromToken, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}