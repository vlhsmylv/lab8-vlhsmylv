package az.edu.ada.wm2.studentservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {

    @Schema(description = "Tələbənin adı", example = "Aysel")
    @NotBlank(message = "Ad tələb olunur")
    private String firstName;

    @Schema(description = "Tələbənin soyadı", example = "Məmmədova")
    @NotBlank(message = "Soyad tələb olunur")
    private String lastName;

    @Schema(description = "Tələbənin e-poçt ünvanı", example = "aysel.mammadova@ada.edu.az")
    @NotBlank(message = "E-poçt tələb olunur")
    @Email(message = "E-poçt formatı yanlışdır")
    private String email;

    @Schema(description = "Tələbənin yaşı", example = "19", minimum = "16")
    @Min(value = 16, message = "Yaş ən azı 16 olmalıdır")
    private Integer age;
}
