package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.utilities.ConsoleUtils;
import org.example.utilities.ingresoDatos;
import org.example.utilities.print;


public class BusquedaAnimal {
    private Arbol arbol;
    private Contenedor<Informacion> lista = new Contenedor<>();
    private final Runnable endl = System.out::println;
    private final Runnable printArboRunnable = () -> { // Runnable para que se mas facil estar imprimuiendo el arbol
        print.println("Raiz: "+ arbol.getRaizDato().getInfo());
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
        print.printlnColor(print.BLUE,"Por nivel: ");
        arbol.imprimirArbolXNivel();

        endl.run();
        print.yellowLine();//-------------------------
    };

    public BusquedaAnimal(){
        this.arbol = new Arbol();
        preCarga();
        //-------------------------------
        pruebas();
    }

    public void preCarga(){ //Proximamente con json
        print.printlnColor(print.GREEN,"Cargando datos del arbol!");
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

    //Pregunta-------------------------------------------------------------------------------------------------------------------------------------------------
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
        ConsoleUtils.timeOut(3);

    }

    private void agregarNuevoAnimal(Nodo<Informacion> nodo) {
        print.printlnColor(print.GREEN, "¡Oh no! ¿Qué animal era?");
        String nuevoAnimal = ingresoDatos.validString("Por favor, ingresa el nombre del animal: ");
        // Verificar si el animal ya existe
        Informacion nuevoAnimalInfo = new Animal(nuevoAnimal);
        if (arbol.existeDato(nuevoAnimalInfo)) {
            print.printlnColor(print.RED, "El animal ya existe en el árbol");
            return;
        }

        String nuevaCaracteristica = ingresoDatos.validString("Ahora, ingresa una característica que diferencie a un(a) "+print.YELLOW + nodo.getDato().getInfo() + print.RESET+" de un(a) " + print.YELLOW+nuevoAnimal + print.RESET+ ": ");
        Informacion nuevaCaracteristicaInfo = new Caracteristica(nuevaCaracteristica);

        // Verificar si la característica ya existe
        if (arbol.existeDato(nuevaCaracteristicaInfo)) {
            print.printlnColor(print.RED, "La característica ya existe en el árbol");
            return;
        }

        // Insertar la nueva característica y el nuevo animal en el árbol (rotacion izquierda)
        arbol.actualizarConNuevaCaracteristica(nodo, nuevoAnimal, nuevaCaracteristica);
    }

    private void preguntarCaracteristica(Nodo<Informacion> nodo) {
        print.print("¿Tu animal tiene la siguiente característica? ");
        print.printColor(print.YELLOW, nodo.getDato().getInfo().toUpperCase());
        boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");
        preguntarRec(respuesta ? nodo.getNodoSi() : nodo.getNodoNo());
    }

    //Visualizacion con listas (parte 2)-------------------------------------------------------------------------------------------------------------------------------------------
    public void menuLista(){
        endl.run();
        endl.run();
        print.printlnColor(print.Orange, "¿Qué deseas hacer?");
        print.println(print.YELLOW+ "[1]." + print.RESET + "  Añadir animales a la lista de manera acendente");
        print.println(print.YELLOW+ "[2]." + print.RESET + "  Añadir animales a la lista de manera descendente");
        print.println(print.YELLOW+ "[3]." + print.RESET + "  Ordenar la lista en orden de su codigo");
        print.println(print.YELLOW+ "[4]." + print.RESET + "  Invertir lista");
        print.println(print.YELLOW+ "[5]." + print.RESET + "  Mostrar la lista");
        print.println(print.YELLOW+ "[6]." + print.RESET + "  Buscar un animal en la lista");
        print.println(print.YELLOW+ "[7]." + print.RESET + "  Atras");

        int opcion = ingresoDatos.validOpcionNum(7, "Ingrese una opcion: ");
        ejecutarOpcionLista(opcion);
    }

    private void agregarAnimalesALista(boolean agregarInicio) {
        if (!lista.isEmpty()) {
            print.printlnColor(print.RED, "La lista ya tiene datos, Deseas sobre escribir la lista?");
            if (!ingresoDatos.validBoolean("¿Deseas sobre escribir la lista? (s/n): ")) {
                return;
            }
        }

        print.printlnColor(print.GREEN, "Agregando animales a la lista en " + (agregarInicio ? "pre Orden con addFirst()" : "post Orden con addLast()"));
        lista = arbol.obtenerDatosInOrder(agregarInicio);
    }




    public void ejecutarOpcionLista(int opcion){
        switch (opcion){
            case 1:
                //addFirst();
                agregarAnimalesALista(true);
                break;
            case 2:
                //addLast();
                agregarAnimalesALista(false);

                break;
            case 3:
                //sort();
                lista.sort();
                break;
            case 4:
                //reverse();
                lista.reverse();
                break;
            case 5:
                //display();
                print.printlnColor(print.GREEN,"Lista de animales: ");
                lista.display();
                break;
            case 6:
                //search();
                break;
            case 7:
                break;

        }
    }

    //Iniciar el juego-----------------------------------------------------------------------------------------------------------------------------------------
    public void jugar(){
        print.yellowLine();
        print.printlnColor(print.GREEN, "Piensa en un animal y yo adivinaré cuál es!");
        print.printlnColor(print.GREEN, "Responde con 's' para sí y 'n' para no");
        print.yellowLine();

        do {
            ConsoleUtils.clear();
            preguntar();
            print.printlnColor(print.GREEN,"Estado actual del arbol");
            printArboRunnable.run();
        } while (ingresoDatos.validBoolean("¿Quieres seguir jugando? (s/n): "));
    }

    public void iniciar(){
        int opcion;

        print.printlnColor(print.PURPLE, "Iniciando el programa");

        do {
            print.printlnColor(print.Orange,"Que quieres hacer?");

            print.println( print.YELLOW+ "[1]." + print.RESET + " Jugar a adivinar el animal");
            print.println( print.YELLOW+ "[2]." + print.RESET + " Verificar animales y caracteristicas por medio de litas");
            print.println( print.YELLOW+ "[3]." + print.RESET + " Salir");
            opcion = ingresoDatos.validOpcionNum(3, "Ingrese una opcion: ");
            switch (opcion) {
                case 1:
                    jugar();
                    break;
                case 2:

                    menuLista();
                    break;
                case 3:
                    print.printlnColor(print.RED, "Saliendo del programa");
                    break;
            }
            endl.run();
            endl.run();
        }while(opcion != 3);



    }


}
