package bg.softuni.athleticprogramapplication.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mealNotFound) {
        super(mealNotFound);
    }
}
