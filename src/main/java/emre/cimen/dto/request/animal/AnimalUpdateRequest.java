package emre.cimen.dto.request.animal;

import emre.cimen.entities.Animal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalUpdateRequest {

    @Positive(message = "Animal ID must be positive")
    @NotNull(message = "Please enter animal id")
    private int id;

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
    private int customerId;
}
