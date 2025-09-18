# Meditime

**Meditime** is a web application that helps you **remember, organize, and track your medication intake**, preventing missed doses that could put your treatment and well-being at risk.

# Features

## Medication
- **Create medication** with name, dose, start date, and frequency.
- **Update or delete** existing medications.
- **List medications**:
    - All medications
    - Medications scheduled for **today**, **yesterday**, and full **history**.
- **Confirm intake**: record every time the user takes a dose (Medication Intake).

## Testing
- **Coverage:** **91 % classes** and **82 % lines**.
- Complete unit tests for the **Service** layer.

# Tech Stack

## Backend
- Java 17
- Spring Boot 3
- Spring Data JPA & Hibernate
- Lombok
- MySQL
- Maven
- IntelliJ IDEA CE
- JUnit & Mockito for testing

## Project Structure
```
├── mvnw
├── pom.xml
├── src
│ ├─── main
│ │ ├──── java
│ │ │ └──── com.example.sanitas
│ │ │ ├───── SanitasApplication.java
│ │ │ ├───── config
│ │ │ │ ├────── CorsConfig.java
│ │ │ │ └───── DataSeeder.java
│ │ │ ├───── controller
│ │ │ │ └───── MedicationController.java
│ │ │ ├───── dtos
│ │ │ │ ├───── MedicationMapper.java
│ │ │ │ ├───── MedicationRequest.java
│ │ │ │ └───── MedicationResponse.java
│ │ │ ├───── models
│ │ │ │ ├───── Medication.java
│ │ │ │ ├───── MedicationIntake.java
│ │ │ │ └───── Status.java
│ │ │ ├───── repository
│ │ │ │ ├───── MedicationIntakeRepository.java
│ │ │ │ └───── MedicationRepository.java
│ │ │ └───── service
│ │ │ └─────── MedicationService.java
│ │ └───── resources
│ │ └──────── application.yml
│ └───── test
│ └──────── java
│ └─────────── com.example.sanitas
│ ├────────────── controller
│ └────────────── service
└── target
```

# Getting Started

## Backend Setup
### Clone the repository
```
git clone https://github.com/<your-org-or-user>/meditime-backend.git
cd meditime-backend
```
### Copy and edit environment variables
```
cp .env.example .env
```
### Edit .env with your MySQL credentials
### Run the application
```
./mvnw spring-boot:run
```
# Endpoints overview
| HTTP Method | Endpoint                       | Description                                  |
| ----------- |---------------------------------|---------------------------------------------- |
| `POST`      | `/api/medications`             | Create a new medication.                       |
| `POST`      | `/api/medications/{id}/take`   | Record that the user took the medication.      |
| `GET`       | `/api/medications`             | Retrieve all medications.                      |
| `GET`       | `/api/medications/today`       | Retrieve medications scheduled for today.       |
| `GET`       | `/api/medications/tomorrow`    | Retrieve medications scheduled for tomorrow.    |
| `GET`       | `/api/medications/week`        | Retrieve medications scheduled for the week.    |
| `GET`       | `/api/medications/history`     | Retrieve full medication history.               |
| `PUT`       | `/api/medications/{id}`        | Update an existing medication.                 |
| `DELETE`    | `/api/medications/{id}`        | Delete a medication.                           |

# Tests

- 91 % class coverage
- 82 % line coverage

# Team & Credits
Developed during the Sanitas Hackathon by:
- Angela Bello – @AngelaBello
- Gabriel ML – @gabrielml
- Gema Y.C. – @gemayc
- Maryori Cruz – @MaryoriCruz
- Milca Ponce – @milcaponce
- Paola Pulga – @Pao-Pul
- Sofía Santos – @sofianutria