package az.edu.ada.wm2.courseservice.exception;

public class RemoteStudentNotFoundException extends RuntimeException {

    public RemoteStudentNotFoundException(Long id) {
        super("Tələbə servisində tələbə tapılmadı. ID: " + id);
    }
}
