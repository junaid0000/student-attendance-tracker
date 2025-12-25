# Student Attendance Tracker i am adding build status and codecoverage for badges.

## Build Status
[![Java CI with Maven](https://github.com/junaid0000/student-attendance-tracker/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/junaid0000/student-attendance-tracker/actions)

## Code Coverage
[![Coverage Status](https://coveralls.io/repos/github/junaid0000/student-attendance-tracker/badge.svg?branch=main)](https://coveralls.io/github/junaid0000/student-attendance-tracker?branch=main)


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
Testing Coveralls PR

