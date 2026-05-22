package az.edu.ada.wm2.courseservice.exception;

public class PrerequisiteNotMetException extends RuntimeException {

    public PrerequisiteNotMetException(Long id, Long prerequisiteCourseId) {
        super("Prerequisite not met. Course ID: " + id + " . Prerequisite Course ID: " + prerequisiteCourseId);
    }
}
