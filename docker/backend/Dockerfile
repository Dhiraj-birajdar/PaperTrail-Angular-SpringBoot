# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk
ARG PROFILE=prod
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/PaperTrail-SpringBoot-RestAPI-*.jar /app/


EXPOSE 8080

ENV DB_URL="jdbc:mysql://mysql-papertrail:3306/papertrail?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true"
ENV MAILDEV_URL=localhost

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL}  PaperTrail-SpringBoot-RestAPI-*.jar