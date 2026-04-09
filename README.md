# Student Attendance Tracker

#BUILD PROJECT [![Build Status](https://img.shields.io/github/actions/workflow/status/junaid0000/student-attendance-tracker/maven.yml?branch=main&label=Build&v=3)](https://github.com/junaid0000/student-attendance-tracker/actions/workflows/maven.yml)

#COVERAGE REPORT WITH COVERALLS [![Coverage Status](https://img.shields.io/coveralls/github/junaid0000/student-attendance-tracker/main?label=Coverage&v=3)](https://coveralls.io/github/junaid0000/student-attendance-tracker?branch=main)

#QUALITY GATE WITH SONARCLOUD [![Quality Gate](https://img.shields.io/sonar/quality_gate/junaid0000_student-attendance-tracker?sonar_url=https%3A%2F%2Fsonarcloud.io&label=Quality%20Gate&v=3)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

#CODE SMELLS WITH SONARCLOUD [![Code Smells](https://img.shields.io/sonar/violations/junaid0000_student-attendance-tracker?sonar_url=https%3A%2F%2Fsonarcloud.io&v=3)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

#COVERAGE REPORT WITH SONARCLOUD [![Sonar Coverage](https://img.shields.io/sonar/coverage/junaid0000_student-attendance-tracker?sonar_url=https%3A%2F%2Fsonarcloud.io&v=3)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

#TECHNICAL DEBT STATUS WITH SONARCLOUD [![Technical Debt](https://img.shields.io/sonar/tech_debt/junaid0000_student-attendance-tracker?sonar_url=https%3A%2F%2Fsonarcloud.io&v=3)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

#VULNERABILITIES IN SONARCLOUD [![Vulnerabilities](https://img.shields.io/sonar/vulnerabilities/junaid0000_student-attendance-tracker?sonar_url=https%3A%2F%2Fsonarcloud.io&v=3)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

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

