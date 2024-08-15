package bg.softuni.athletic_program_side_project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "runs")
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
    private Integer kilometers;
    private Integer meters;

    @ManyToOne
    @JoinColumn(name = "user_id")  // The foreign key column in the runs table
    private User user;

    public Run() {
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

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public Integer getMeters() {
        return meters;
    }

    public void setMeters(Integer meters) {
        this.meters = meters;
    }
}
