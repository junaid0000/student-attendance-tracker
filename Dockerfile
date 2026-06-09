# Build the application and copy runtime dependencies into the image.
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -Dmaven.test.skip=true dependency:go-offline

COPY src ./src
RUN mvn -B -Dmaven.test.skip=true package dependency:copy-dependencies -DincludeScope=runtime

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/target/student-attendance-tracker-*.jar app.jar
COPY --from=build /workspace/target/dependency ./lib

ENV MONGO_HOST=mongodb
ENV MONGO_PORT=27017
ENV DB_NAME=attendance_db
ENV DB_STUDENT_COLLECTION=students
ENV DB_ATTENDANCE_COLLECTION=attendance

ENTRYPOINT ["sh", "-c", "exec java -cp '/app/app.jar:/app/lib/*' com.example.attendance.app.AttendanceTrackerApp --mongo-host=${MONGO_HOST} --mongo-port=${MONGO_PORT} --db-name=${DB_NAME} --db-student-collection=${DB_STUDENT_COLLECTION} --db-attendance-collection=${DB_ATTENDANCE_COLLECTION} \"$@\"", "--"]
