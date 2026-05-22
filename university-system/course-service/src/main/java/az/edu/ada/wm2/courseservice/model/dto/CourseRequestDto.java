package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
// import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @Schema(description = "Kursun adı", example = "Verilənlər Bazasının Əsasları")
    // @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Kursun kodu", example = "CS101")
    // @NotBlank(message = "Code is required")
    private String code;

    @Schema(description = "Kredit sayı", example = "3")
    @Positive(message = "Kredit sayı müsbət olmalıdır")
    private Integer credits;

    @Schema(description = "İlkin şərtləndirilən kursun ID-si", example = "1")
    private Long prerequisiteCourseId;
}
