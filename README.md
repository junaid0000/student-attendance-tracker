# Student Attendance Tracker

The project is a simple Student Attendance Tracker, designed to align with the course requirements and techniques presented in the textbook "Test-Driven Development, Build Automation, Continuous Integration with Java".

## Project Overview

The application will manage students' attendance records. It will include two main entities:

- **Student**: studentId, name, rollNumber
- **AttendanceRecord**: recordId, date, present, studentId

## Main Features

- Add, edit, or delete students
- Mark attendance (Present/Absent) for each student
- View attendance by date and student
- Display overall attendance summary

## Tools & Technologies

- Java 17
- MongoDB (managed via Docker/Testcontainers)
- Maven for build automation
- JUnit 4 for unit testing
- Mockito for mocking
- PIT for mutation testing
- JaCoCo and Coveralls for 100% code coverage
- SonarCloud for code quality (no technical debt)
- GitHub Actions for continuous integration
- Swing-based user interface

