# Student Attendance Tracker

[![Java CI with Maven](https://github.com/junaid0000/student-attendance-tracker/actions/workflows/maven.yml/badge.svg?branch=main&v=4)](https://github.com/junaid0000/student-attendance-tracker/actions/workflows/maven.yml)
[![Coverage Status](https://img.shields.io/coveralls/github/junaid0000/student-attendance-tracker/main?label=Coveralls&v=4)](https://coveralls.io/github/junaid0000/student-attendance-tracker?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=alert_status&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=code_smells&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=coverage&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=sqale_index&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=junaid0000_student-attendance-tracker&metric=vulnerabilities&branch=main&bust=1)](https://sonarcloud.io/summary/new_code?id=junaid0000_student-attendance-tracker)

Student Attendance Tracker is a Java-based desktop application designed to manage student records and attendance in a simple and organized way. It allows users to add, update, and delete students, mark attendance as present or absent, view attendance records, and generate attendance summaries. The project uses Maven, MongoDB, Swing, and a test-driven development approach with unit, integration, end-to-end, coverage, and mutation testing to keep the application reliable and maintainable.



## Commands

| Task | Command |
| --- | --- |
| Build without tests | `mvn clean package -DskipTests` |
| Run unit tests | `mvn clean test` |
| Run all tests | `mvn clean verify` |
| Run coverage | `mvn clean verify -Pjacoco` |
| Run mutation testing | `mvn clean verify -Ppit` |
| Run without Docker tests | `mvn clean verify -Pskip-testcontainers` |
