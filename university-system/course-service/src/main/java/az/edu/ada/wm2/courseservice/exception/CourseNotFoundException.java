package az.edu.ada.wm2.courseservice.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Kurs tapılmadı. ID: " + id);
    }
}
