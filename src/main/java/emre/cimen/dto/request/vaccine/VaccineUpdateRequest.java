package emre.cimen.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {

    @Positive(message = "ID must be positive")
    @NotNull(message = "Please enter vaccine id")
    private int id;

    @NotNull(message = "Please enter vaccine name")
    private String name;

    @NotNull(message = "Please enter vaccine code")
    private String code;

    @NotNull(message = "Please enter vaccine start date")
    private LocalDate protectionStrtDate;

    @NotNull(message = "Please enter vaccine finish date")
    private LocalDate protectionFnshDate;

    private Integer animalId;
}
