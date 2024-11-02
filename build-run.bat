@echo off
echo Limpiando proyecto...
mvn clean

echo Resolviendo dependencias...
mvn dependency:resolve

echo Instalando proyecto...
mvn install

echo Ejecutando aplicación...
mvn exec:java -Dexec.mainClass="org.example.Main"

pause
