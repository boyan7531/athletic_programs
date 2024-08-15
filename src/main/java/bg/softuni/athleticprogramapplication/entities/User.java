package bg.softuni.athleticprogramapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToMany(mappedBy = "user")
    private Set<Run> runs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    @JsonIgnore
    private Set<Meal> favoriteMeals;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProgressTracker> progressTrackers;


    public User() {
        this.roles = new HashSet<>();
        this.runs = new HashSet<>();
        this.favoriteMeals = new HashSet<>();
        this.progressTrackers = new ArrayList<>();
    }


    public List<ProgressTracker> getProgressTrackers() {
        return progressTrackers;
    }

    public void setProgressTrackers(List<ProgressTracker> progressTrackers) {
        this.progressTrackers = progressTrackers;
    }

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Set<Run> getRuns() {
        return runs;
    }

    public void setRuns(Set<Run> runs) {
        this.runs = runs;
    }

    public Set<Meal> getFavoriteMeals() {
        return favoriteMeals;
    }

    public void setFavoriteMeals(Set<Meal> favoriteMeals) {
        this.favoriteMeals = favoriteMeals;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
