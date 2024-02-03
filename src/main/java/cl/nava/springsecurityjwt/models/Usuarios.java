package cl.nava.springsecurityjwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String userName;
    private String correo;
    private String contrasena;
    private double pesoActual;
    private double pesoObjetivo;
    private double estatura;
    private int edad;
    private String genero;
    private String objetivo;
    private String ejercicioFrecuente;
    private String tipoEjerciocio;
    private double mediadaCintura;
    private double mediadaCadera;
    private double medidaCuello;
    private double grasaCorporal;
    private int iniciosSesion;
    private boolean premium;
    private double calorias;
    private double proteina;
    private double carbohidratos;
    private double grasas;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Roles> roles = new ArrayList<>();

}
