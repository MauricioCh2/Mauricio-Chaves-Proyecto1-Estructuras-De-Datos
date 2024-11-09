# Autor
Mauricio Alberto Chaves Chaves

# Sistema Experto de Animales
Este proyecto implementa un sistema experto basado en árboles binarios que aprende sobre animales mediante interacción con el usuario. El sistema está desarrollado en Java y utiliza estructuras de datos personalizadas como arboles, nodos, listas dobles y JSON.

## Estructura del Proyecto
```
└── 📁Mauricio-Chaves-Proyecto1
    └── 📁.idea
    └── 📁lib
    └── 📁src
        └── 📁main
            └── 📁java
                └── 📁org
                    └── 📁example
                        └── 📁Entities
                            └── Animal.java
                            └── Caracteristica.java
                            └── Informacion.java
                        └── 📁logic
                            └── Arbol.java
                            └── BusquedaAnimal.java
                            └── Contenedor.java
                            └── JSON.java
                            └── Nodo.java
                            └── NodoL.java
                        └── 📁utilities
                            └── ConsoleUtils.java
                            └── ingresoDatos.java
                            └── print.java
                        └── Main.java
        └── 📁test
    └── 📁target
    └── .gitignore
    └── arbol.json
    └── build-run.bat
    └── build-run.sh
    └── pom.xml
    └── README.md
```

## Requisitos Previos

- Java JDK 8 o superior
- Apache Maven 3.8.1 o superior

## Instalación y Ejecución

### Windows
1. Asegúrate de tener Java y Maven instalados y configurados en las variables de entorno
2. Abre una terminal en el directorio del proyecto
3. Ejecuta el script build-run.bat:
```bash
.\build-run.bat
```

### Linux/MacOs
1. Asegúrate de tener Java y Maven instalados
2. Abre una terminal en el directorio del proyecto
3. Dale permisos de ejecución al script:
```bash
chmod +x build-run.sh
```
Ejecuta el script:
```bash
./build-run.sh
```

## Funcionamiento
El sistema experto permite:

- Aprender nuevos animales y sus características mediante preguntas al usuario
- Almacenar el conocimiento en un árbol binario
- Persistir la información en formato JSON
- Realizar búsquedas de animales basadas en sus características
- Visualizar el árbol de conocimiento con diferentes ordenamientos

## Estructura de Datos
El proyecto utiliza las siguientes estructuras de datos personalizadas:

- Árboles binarios para almacenar la jerarquía de características y animales
- Listas dobles generics personalizadas para manejar colecciones de datos 
- Nodos para representar cada elemento del árbol y la lista
- Formato JSON para persistencia de datos