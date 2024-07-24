package emre.cimen.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    @NotNull
    @Column(name = "customer_name")
    private String name;

    @NotNull
    @Column(name = "customer_phone",unique = true)
    private String phone;

    @NotNull
    @Column(name = "customer_mail",unique = true)
    @Email
    private String mail;

    @NotNull
    @Column(name = "customer_address")
    private String address;

    @NotNull
    @Column(name = "customer_city")
    private String city;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Animal> animals;

}
