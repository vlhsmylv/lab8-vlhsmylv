package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    @Schema(description = "Kursun ID-si", example = "1")
    private Long id;

    @Schema(description = "Kursun adı", example = "Verilənlər Bazasının Əsasları")
    private String title;

    @Schema(description = "Kursun kodu", example = "CS101")
    private String code;

    @Schema(description = "Kredit sayı", example = "3")
    private Integer credits;

    @Schema(description = "İlkin şərtləndirilən kursun ID-si", example = "1")
    private Long prerequisiteCourseId;
}
