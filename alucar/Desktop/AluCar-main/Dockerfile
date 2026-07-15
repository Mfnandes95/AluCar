FROM gradle:8.14-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Remove o bloco de toolchain do build.gradle antes de compilar
RUN sed -i '/toolchain {/,/}/d' build.gradle

RUN gradle build --no-daemon -x test

# Estágio 2: Execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]