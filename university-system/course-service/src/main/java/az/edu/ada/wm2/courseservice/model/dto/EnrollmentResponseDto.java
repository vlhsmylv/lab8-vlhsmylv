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

    @Schema(description = "Qeydiyyatın ID-si", example = "10")
    private Long enrollmentId;

    @Schema(description = "Kursun ID-si", example = "1")
    private Long courseId;

    @Schema(description = "Tələbənin ID-si", example = "15")
    private Long studentId;

    @Schema(description = "Qeydiyyat tarixi", example = "2026-05-21")
    private LocalDate enrollmentDate;

    @Schema(description = "Əməliyyat nəticəsi mesajı", example = "Tələbə uğurla qeydiyyatdan keçdi.")
    private String message;
}
