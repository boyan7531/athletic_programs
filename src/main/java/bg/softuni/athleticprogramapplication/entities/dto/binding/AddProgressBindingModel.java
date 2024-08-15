package bg.softuni.athleticprogramapplication.entities.dto.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddProgressBindingModel {
    private double weight;
    private double bodyFatPercentage;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private double caloriesConsumption;


    public AddProgressBindingModel() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCaloriesConsumption() {
        return caloriesConsumption;
    }

    public void setCaloriesConsumption(double caloriesConsumption) {
        this.caloriesConsumption = caloriesConsumption;
    }
}
