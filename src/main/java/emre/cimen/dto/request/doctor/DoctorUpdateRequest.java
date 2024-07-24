package emre.cimen.dto.request.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    @Positive(message = "ID must be poesitive")
    @NotNull(message = "Please enter id")
    private int id;

    @NotNull(message = "Please enter name")
    private String name;

    @NotNull(message = "Please enter phone number")
    @Size(min = 11, max = 11, message = "Please enter 11 digit phone number")
    private String phone;

    @Email
    @NotNull(message = "Please enter mail")
    private String mail;

    @NotNull(message = "Please enter address")
    private String address;

    @NotNull(message = "Please enter city")
    private String city;
}
