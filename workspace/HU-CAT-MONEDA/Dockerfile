# Usa la imagen base de OpenJDK para ejecutar tu aplicación
FROM openjdk:17-jdk-slim

# Crea un directorio temporal para almacenar datos en el contenedor
VOLUME /tmp

# Define un argumento para especificar el JAR a copiar
ARG JAR_FILE=target/HU-CAT-MONEDA-0.1.jar

# Copia el archivo JAR generado en el contenedor
COPY ${JAR_FILE} app.jar

# Comando para ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]
