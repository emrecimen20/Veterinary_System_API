package emre.cimen.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {

    @NotNull(message = "Please enter name")
    private String name;

    @NotNull(message = "Please enter vaccine code")
    private String code;

    @NotNull(message = "Please enter vaccine start date")
    private LocalDate protectionStrtDate;

    @NotNull(message = "Please enter vaccine finish date")
    private LocalDate protectionFnshDate;

    private Integer animalId;
}
