# Usa una imagen base con Java
FROM openjdk:21-jdk-slim

# Directorio dentro del contenedor
WORKDIR /app

# Copia el JAR generado por Maven
COPY target/autonomax-back-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto de la aplicación Spring Boot
EXPOSE 8082

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
