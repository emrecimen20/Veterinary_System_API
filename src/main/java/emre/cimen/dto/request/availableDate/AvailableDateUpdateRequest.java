package emre.cimen.dto.request.availableDate;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {

    @Positive(message = "ID must be positive")
    @NotNull(message = "Please enter id")
    private int id;

    @NotNull(message = "Please enter date")
    private LocalDate date;
    private Integer doctorId;
}
