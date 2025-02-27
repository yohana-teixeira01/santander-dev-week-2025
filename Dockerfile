# Build stage: usando Gradle com JDK 17
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copia os arquivos necessários para o build
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Faz o build da aplicação gerando o JAR
RUN gradle bootJar --no-daemon --warning-mode all

# Runtime stage: usando Amazon Corretto 17 (Alpine)
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Copia o JAR gerado do estágio de build
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expondo a porta da aplicação (opcional)
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/app.jar"]
