package bg.softuni.athleticprogramapplication.entities;


import javax.persistence.*;

@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    @Column(unique = true, nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ProgramGoal programGoal;


    public Program() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProgramGoal getProgramGoal() {
        return programGoal;
    }

    public void setProgramGoal(ProgramGoal programGoal) {
        this.programGoal = programGoal;
    }
}
