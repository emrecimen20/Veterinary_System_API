# Veterinary Management System Project

This project is an API project used to manage the veterinary system.
The purpose of this project is to manage veterinary staff's doctors, clients, animals, vaccinations and appointments.

[UML Diagram ](images/UML%20Diagram.png)


### Used technologies 
- Spring Boot
- JDK 22
- PostgreSQL

## Relationships
- **Customer** - **Animal**: OneToMany
- **Animal** - **Vaccine**: OneToMany
- **Doctor** - **AvailableDate**: OneToMany
- **Doctor** - **Appointment**: OneToMany
- **Animal** - **Appointment**: OneToMany


## API Endpoints

### Animal 
- **GET   /v1/animals/byName/{name}**: Display animals by name.
- **GET   /v1/animals**: Display all animals.
- **GET   /v1/animals/{id}**: Display animal by ID.
- **POST   /v1/animals**: New animal registeration.
- **PUT    /v1/animals**: Update animals.
- **DELETE  /v1/animals/{id}**: Delete animals
- **GET /v1/animals/customer/{id}** : Display animals by customer ID.
- [Animal Table ](images/animaltable.png)


### Doctor
- **GET /v1/doctors**: Display all doctor.
- **GET /v1/doctors/{id}**: Display doctor by ID.
- **POST /v1/doctors**: Add doctor.
- **PUT /v1/doctors**: Update doctor.
- **DELETE  /v1/doctors/{id}**: Delete doctor.
- [Doctor Table ](images/doctortable.png)


### Available Date
- **GET   /v1/available-dates**: Display available dates.
- **GET   /v1/available-dates/{id}**: Display available dates by ID.
- **POST   /v1/available-dates**: Add available day.
- **PUT    /v1/available-dates**: Update available day.
- **DELETE  /v1/available-dates/{id}**: Delete available day.
- [Available Date Table ](images/availabledatetable.png)


### Customer
- **GET /v1/customers/byName/{name}**: Display customers by name.
- **GET /v1/customers/{id}**: Display customers by ID.
- **GET /v1/customers**: Display  all customers.
- **POST /v1/customers**: Add customer.
- **PUT /v1/customers**: Update customer
- **DELETE  /v1/customers/{id}**: Delete customer.
- [Customer Table ](images/customertable.png)


### Appointment

- **GET   /v1/appointments**: Display all appointment.
- **GET   /v1/appointments/{id}**: Display appointment by ID.
- **GET   /v1/appointments/filterbyDrDate/{doctorId}**: Display appointment by doctor ID.
- **GET   /v1/appointments/filterbyAnmlDate/{animalId}**: Display appointment by animal ID.
- **POST   /v1/appointments**: Add appointment.
- **PUT    /v1/appointments**: Update appointment.
- **DELETE  /v1/appointments/{id}**: Delete appointment.
- [Appointment Table ](images/appointmenttable.png)



### Vaccine

- **GET /v1/vaccines/findbyDate**: Display vaccine start and finish day.
- **GET /v1/vaccines/{id}**: Display vaccine by ID.
- **GET v1/vaccines**: Display all vaccines.
- **POST /v1/vaccines**: Add new vaccine.
- **PUT /v1/vaccines**: Update vaccine.
- **DELETE  /v1/vaccines/{id}**: Delete vaccine
- **GET /v1/vaccines/animal/{id}** : Display vaccine by animal ID.
- [Vaccine Table ](images/vaccinetable.png)