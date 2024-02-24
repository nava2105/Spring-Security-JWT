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
    private Long userId;
    private String userName;
    private String password;
    private double height;
    private double weight;
    private int age;
    private String genre;
    private String activityFactor;
    private String objective;
    private double hidratation;
    private double neck;
    private double hip;
    private double waist;
    private double fat_percent;
    private int sessionsHistory;
    private boolean premium;
    private double maintenanceCalories;
    private double objectiveCalories;
    private double protein;
    private double carbs;
    private double fat;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Roles> roles = new ArrayList<>();

}
