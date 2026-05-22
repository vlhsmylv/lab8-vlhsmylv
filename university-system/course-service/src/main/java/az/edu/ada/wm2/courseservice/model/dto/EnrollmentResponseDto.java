package az.edu.ada.wm2.courseservice.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDto {

    @Schema(description = "Enrollment id", example = "10")
    private Long enrollmentId;

    @Schema(description = "Course id", example = "1")
    private Long courseId;

    @Schema(description = "Student id", example = "15")
    private Long studentId;

    // TODO: Az translation
    @Schema(description = "Enrollment date", example = "2026-05-21")
    private LocalDate enrollmentDate;

    @Schema(description = "Operation result message", example = "Student enrolled successfully.")
    private String message;
}
