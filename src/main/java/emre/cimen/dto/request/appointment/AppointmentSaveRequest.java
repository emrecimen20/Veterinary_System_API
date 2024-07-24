package emre.cimen.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {

    @NotNull(message = "Please enter date")
    private LocalDateTime dateTime;

    @Positive(message = "ID must be positive")
    @NotNull(message = "Please enter id")
    private Integer animalId;

    @Positive(message = "ID must be positive")
    @NotNull(message = "Please enter id")
    private Integer doctorId;
}
