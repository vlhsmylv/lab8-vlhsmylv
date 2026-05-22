package az.edu.ada.wm2.courseservice.exception;

public class PrerequisiteNotMetException extends RuntimeException {

    public PrerequisiteNotMetException(Long id, Long prerequisiteCourseId) {
        super("Tələbə " + id + ", " + prerequisiteCourseId + " kursuna qeydiyyatdan keçmək üçün ilkin şərtlərə cavab verməlidir.");
    }
}
