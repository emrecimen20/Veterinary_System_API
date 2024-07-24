package emre.cimen.dto.request.animal;

import emre.cimen.entities.Animal;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
    @NotNull(message = "Please enter animal name")
    private String name;

    @NotNull(message = "Please enter animal species")
    private String species;

    @NotNull(message = "Please enter animal breed")
    private String breed;

    @NotNull(message = "Please enter animal gender")
    private Animal.GENDER gender;

    @NotNull(message = "Please enter animal color")
    private String color;

    @NotNull(message = "Please enter animal birthday")
    private LocalDate birthday;

    private Integer  customerId;
}
