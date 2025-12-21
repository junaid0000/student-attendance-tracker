# Student Attendance Tracker

Advanced Programming Exam Project - Student Attendance Tracker with TDD

## Project Overview
The application will manage students' attendance records with two main entities:

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
- SonarCloud for code quality
- GitHub Actions for continuous integration
- Swing-based user interface

## Development Approach
- Test-Driven Development (TDD)
- Unit, integration, and end-to-end tests
- Clean, reproducible GitHub repository
git add README.md
git commit -m "Test Coveralls PR"
