package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.utilities.ingresoDatos;
import org.example.utilities.print;


import java.util.List;


public class BusquedaAnimal {
    private Arbol arbol;
    private final Runnable endl = System.out::println;
    private final Runnable printArboRunnable = () -> { // Runnable para que se mas facil estar imprimuiendo el arbol
        print.println("Raiz: "+ arbol.getRaizDato().getInfo());
        endl.run();
        print.yellowLine();//-------------------------
        print.printlnColor(print.BLUE,"InOrden: ");
        arbol.imprimirRecorrido("inOrden");
        endl.run();
        print.printlnColor(print.BLUE,"preOrden: ");
        arbol.imprimirRecorrido("preOrden");
        endl.run();
        print.printlnColor(print.BLUE,"postOrden: ");
        arbol.imprimirRecorrido("postOrden");

        endl.run();
        print.yellowLine();//-------------------------
        List<Informacion> infoAnimales = arbol.obtenerDatosEnOrden();
        infoAnimales.forEach(valor -> {
            print.print("\nNivel de " + valor.getInfo() + ": ");
            print.print(arbol.nivel(valor));
        });
        print.println("");
        print.yellowLine();//-------------------------
    };

    public BusquedaAnimal(){
        this.arbol = new Arbol();
        preCarga();
        //-------------------------------
        pruebas();
    }

    public void preCarga(){
        Caracteristica ave = new Caracteristica("ave");
        Caracteristica reptil = new Caracteristica("reptil");
        Caracteristica mamifero = new Caracteristica("mamifero");
        Caracteristica acuatico = new Caracteristica("acuatico");
        Caracteristica maulla = new Caracteristica("maulla");
        Caracteristica pez = new Caracteristica("pez");
        Caracteristica invertebrado = new Caracteristica("invertebrado");
        Caracteristica anfibio = new Caracteristica("anfibio");

        Animal aguila = new Animal("Águila");
        Animal lagarto = new Animal("Lagarto");
        Animal ballena = new Animal("Ballena");
        Animal gato = new Animal("Gato");
        Animal perro = new Animal("Perro");
        Animal beta = new Animal("Beta");
        Animal arana = new Animal("Araña");
        Animal rana = new Animal("Rana");
        Animal culebra = new Animal("Culebra");

        // Construcción manual del árbol siguiendo la estructura de la imagen
        arbol.insertar(ave);//Raiz
        // Nivel 2
        arbol.insertarHijo(ave, aguila, true);      // ave -> Sí -> aguila
        arbol.insertarHijo(ave, reptil, false);     // ave -> No -> reptil

        // Nivel 2
        arbol.insertarHijo(reptil, lagarto, true);  // reptil -> Sí -> lagarto
        arbol.insertarHijo(reptil, mamifero, false);// reptil -> No -> mamifero

        // Nivel 3
        arbol.insertarHijo(mamifero, acuatico, true);   // mamifero -> Sí -> acuatico
        arbol.insertarHijo(mamifero, pez, false);       // mamifero -> No -> pez

        // Nivel 4
        arbol.insertarHijo(acuatico, ballena, true);    // acuatico -> Sí -> ballena
        arbol.insertarHijo(acuatico, maulla, false);    // acuatico -> No -> maulla

        // Nivel 5
        arbol.insertarHijo(maulla, gato, true);         // maulla -> Sí -> gato
        arbol.insertarHijo(maulla, perro, false);       // maulla -> No -> perro

        // Nivel 4 - Rama "pez"
        arbol.insertarHijo(pez, beta, true);            // pez -> Sí -> beta
        arbol.insertarHijo(pez, invertebrado, false);   // pez -> No -> invertebrado

        // Nivel 5
        arbol.insertarHijo(invertebrado, arana, true);  // invertebrado -> Sí -> arana
        arbol.insertarHijo(invertebrado, anfibio, false); // invertebrado -> No -> anfibio

        // Nivel 6
        arbol.insertarHijo(anfibio, rana, true);        // anfibio -> Sí -> rana
        arbol.insertarHijo(anfibio, culebra, false);    // anfibio -> No -> culebra

    }
    public void pruebas(){
        print.printColor(print.GREEN,"Arbol creado\n");
        printArboRunnable.run();

    }

    //Pregunta-----------------------------------------------------------------------------------------------
    public void preguntar(){ //Aqui se hace la pregunta usando un arbol auxiliar para no tener que reiniciar el arbol en caso de volver a preguntar

        Nodo<Informacion> nodoAux = arbol.getRaiz();
        preguntarRec(nodoAux);

        //aqui hay que pone la parte de ingreso de caracteristica y animal
    }

    private void preguntarRec(Nodo<Informacion> nodo) {//dependiendo de si es un animal o una caracteristica se procesarad e amnera diferente
        if (nodo.getDato() instanceof Animal) {
            procesarAnimal((Animal) nodo.getDato(), nodo);
        } else {
            preguntarCaracteristica(nodo);
        }
    }

    private void procesarAnimal(Animal animal, Nodo<Informacion> nodo) {//Procesa la opcion del usuario
        print.printlnColor(print.GREEN, "El animal es: " + animal.getInfo());
        boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");
        if (respuesta) {
            print.printlnColor(print.GREEN, "¡Soy un genio!");
        } else {
            agregarNuevoAnimal(nodo);
        }
    }

    private void agregarNuevoAnimal(Nodo<Informacion> nodo) {
        print.printlnColor(print.GREEN, "¡Oh no! ¿Qué animal era?");
        String nuevoAnimal = ingresoDatos.validString("Por favor, ingresa el nombre del animal: ");
        String nuevaCaracteristica = ingresoDatos.validString("Ahora, ingresa una característica que diferencie a un(a) " + nodo.getDato().getInfo() + " de un(a) " + nuevoAnimal + ": ");

        Animal animal = new Animal(nuevoAnimal);
        Caracteristica caracteristica = new Caracteristica(nuevaCaracteristica);

        // Insertar la nueva característica y el nuevo animal en el árbol (rotacion izquierda)
        arbol.actualizarConNuevaCaracteristica(nodo, nuevoAnimal, nuevaCaracteristica);

        printArboRunnable.run();
    }

    private void preguntarCaracteristica(Nodo<Informacion> nodo) {
        print.print("¿Tu animal tiene la siguiente característica? ");
        print.printColor(print.YELLOW, nodo.getDato().getInfo().toUpperCase());
        boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");
        preguntarRec(respuesta ? nodo.getNodoSi() : nodo.getNodoNo());
    }


    //Iniciar el juego---------------------------------------------------------------------------------------
    public void jugar(){
        do {
            preguntar();
        } while (ingresoDatos.validBoolean("¿Quieres seguir jugando? (s/n): "));
    }

    public void iniciar(){
        print.printlnColor(print.BLUE, "Iniciando el programa");
        print.yellowLine();
        print.printlnColor(print.GREEN, "Piensa en un animal y yo adivinaré cuál es!");
        print.printlnColor(print.GREEN, "Responde con 's' para sí y 'n' para no");
        print.yellowLine();

        // Iniciar la búsqueda del animal
        jugar();

    }


}
