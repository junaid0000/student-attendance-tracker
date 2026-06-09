# Student Attendance Tracker

#BUILD PROJECT [![Java CI with Maven](https://github.com/junaid0000/student-attendance-tracker/actions/workflows/maven.yml/badge.svg?branch=main&v=4)](https://github.com/junaid0000/student-attendance-tracker/actions/workflows/maven.yml)
#COVERAGE REPORT WITH COVERALLS [![Coverage Status](https://img.shields.io/coveralls/github/junaid0000/student-attendance-tracker/main?label=Coverage&v=4)](https://coveralls.io/github/junaid0000/student-attendance-tracker?branch=main)
#QUALITY GATE WITH SONARCLOUD [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=alert_status&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
#CODE SMELLS WITH SONARCLOUD [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=code_smells&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
#COVERAGE REPORT WITH SONARCLOUD(The Badge color is #GREY by default) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=coverage&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
#TECHNICAL DEBT STATUS WITH SONARCLOUD [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=sqale_index&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
#VULNERABILITIES IN SONARCLOUD [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=vulnerabilities&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

Advanced Programming Exam Project - Student Attendance Tracker with TDD

## Build
1. Clean and Build (No Tests)
- mvn clean package -DskipTests

2. Run Standard Unit Tests Only
- mvn clean test

3. Run ALL Tests (Unit + Integration + E2E)
- mvn clean verify

4. Run ALL Tests + Generate Coverage Report (JaCoCo)
- mvn clean verify -Pjacoco

5. Run Mutation Testing (PIT)
- mvn clean verify -Ppit

6. Run Tests WITHOUT Docker
- mvn clean verify -Pskip-testcontainers


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

