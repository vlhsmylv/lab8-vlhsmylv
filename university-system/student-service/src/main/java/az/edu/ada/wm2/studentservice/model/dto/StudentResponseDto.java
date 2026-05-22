package az.edu.ada.wm2.studentservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    @Schema(description = "Tələbənin unikal identifikatoru", example = "1")
    private Long id;
    @Schema(description = "Tələbənin adı", example = "Aysel")
    private String firstName;
    @Schema(description = "Tələbənin soyadı", example = "Məmmədova")
    private String lastName;
    @Schema(description = "Tələbənin e-poçt ünvanı", example = "aysel.mammadova@ada.edu.az")
    private String email;
    @Schema(description = "Tələbənin yaşı", example = "19")
    private Integer age;
}
