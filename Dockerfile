FROM maven:3.9.4-amazoncorretto-21 AS build_step
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine3.18
RUN mkdir /application
COPY --from=build_step /project/target/InventoryManagementService-0.0.1.jar /application
WORKDIR /application
RUN addgroup --system juser
RUN adduser -S -s /bin/false -G juser juser
RUN chown -R juser:juser /application
USER juser
ENTRYPOINT ["java", "-XX:+UseSerialGC","-Xmx32m", "-Xss256k", "-jar", "InventoryManagementService-0.0.1.jar"]
