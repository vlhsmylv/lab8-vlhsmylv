package az.edu.ada.wm2.studentservice.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Tələbə tapılmadı. ID: " + id);
    }
}
