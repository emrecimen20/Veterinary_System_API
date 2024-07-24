package emre.cimen.dto.response.appointment;

import emre.cimen.entities.Animal;
import emre.cimen.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private int id;
    private LocalDateTime dateTime;
    private Animal animal;
    private Doctor doctor;
}
