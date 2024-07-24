package emre.cimen.dto.response.availableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {
    private int id;
    private LocalDate date;
    private Integer doctorId;
}
