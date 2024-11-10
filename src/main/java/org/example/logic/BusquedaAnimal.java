package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.utilities.ConsoleUtils;
import org.example.utilities.ingresoDatos;
import org.example.utilities.print;

import java.util.HashMap;
import java.util.Map;


public class BusquedaAnimal {
    private Arbol arbol;
    private Contenedor<Informacion> lista = new Contenedor<>();
    private final Map<String,Contenedor<String>> map;

    //Runnable para imprimir el árbol
    private final Runnable endl = System.out::println;
    private final Runnable printArboRunnable = () -> { // Runnable para que sea más fácil estar imprimiendo el árbol
        print.println("Raíz: "+ arbol.getRaizDato().getInfo());
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



    //Constructor----------------------------------------------------------------------------------------------------------------------------------------------
    public BusquedaAnimal(){
        this.arbol = new Arbol();
        preCarga();
        //-------------------------------
        pruebas();
        this.map = new HashMap<>();
    }

    public void preCarga(){ //Próximamente con json
        arbol = JSON.cargarArbol();
    }
    public void pruebas(){
        print.printColor(print.GREEN,"Árbol creado\n");
        printArboRunnable.run();

    }

    //Inicio de juego------------------------------------------------------------------------------------------------------------------------------------------
    public void iniciar(){
        print.printlnColor(print.PURPLE, "Iniciando el programa");
        menuInicio();
    }
    public void menuInicio(){
        int opcion;
        do{
            print.printlnColor(print.Orange,"Que quieres hacer?");
            print.println( print.YELLOW+ "[1]." + print.RESET + " Jugar a adivinar el animal");
            print.println( print.YELLOW+ "[2]." + print.RESET + " Verificar animales y características por medio de litas");
            print.println( print.YELLOW+ "[3]." + print.RESET + " Recargar el árbol con datos quemados");
            print.println( print.YELLOW+ "[4]." + print.RESET + " Salir");
            opcion = ingresoDatos.validOpcionNum(4, "Ingrese una opción: ");
            ejecutarOpcionInicio(opcion);
        }while(opcion != 4);

    }
    public void ejecutarOpcionInicio(int opcion){
        switch (opcion) {
            case 1:
                jugar();
                if(ingresoDatos.validBoolean("¿Deseas guardar el árbol actual? (s/n): ")){
                    JSON.guardarArbol(arbol);
                }
                break;
            case 2:
                menuLista();
                break;
            case 3:
                if(ingresoDatos.validBoolean("¿Estas seguro que deseas sobreescribir la lista, perderás el árbol actual? (s/n): ")) {
                    cargaQuemado();
                }
                break;
            case 4:
                print.printlnColor(print.RED, "Saliendo del programa");
                break;
        }
        endl.run();
        endl.run();
    }


    //Juego de adivinar el animal(Parte 1)---------------------------------------------------------------------------------------------------------------------
    public void jugar(){
        print.yellowLine();
        print.printlnColor(print.GREEN, "Piensa en un animal y yo adivinaré cuál es!");
        print.printlnColor(print.GREEN, "Responde con 's' para sí y 'n' para no");
        print.yellowLine();

        do {
            ConsoleUtils.clear();
            preguntar();
            print.printlnColor(print.GREEN,"Estado actual del árbol");
            printArboRunnable.run();
        } while (ingresoDatos.validBoolean("¿Quieres seguir jugando? (s/n): "));
    }

    //Pregunta-------------------------------------------------------------------------------------------------------------------------------------------------
    public void preguntar(){ //Aquí se hace la pregunta usando un árbol auxiliar para no tener que reiniciar el árbol en caso de volver a preguntar

        Nodo<Informacion> nodoAux = arbol.getRaiz();
        preguntarRec(nodoAux);

        //aquí hay que pone la parte de ingreso de característica y animal
    }

    private void preguntarRec(Nodo<Informacion> nodo) {//dependiendo de si es un animal o una característica se procesará de manera diferente
        if (nodo.getDato() instanceof Animal) {
            procesarAnimal((Animal) nodo.getDato(), nodo);
        } else {
            preguntarCaracteristica(nodo);
        }
    }

    private void procesarAnimal(Animal animal, Nodo<Informacion> nodo) {//Procesa la opción del usuario
        print.printlnColor(print.GREEN, "El animal es: " + animal.getInfo());
        boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");
        if (respuesta) {
            print.printlnColor(print.GREEN, "¡Soy un genio!");
        } else {
            agregarNuevoAnimal(nodo);//En caso de que se agregue un nuevo animal se procesara la solicitud
        }
        ConsoleUtils.timeOut(3);

    }

    private void agregarNuevoAnimal(Nodo<Informacion> nodo) {//Ingreso de nuevo animal y característica por medio de una rotación en el árbol
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

        // Insertar la nueva característica y el nuevo animal en el árbol (rotación izquierda)
        arbol.actualizarConNuevaCaracteristica(nodo, nuevoAnimal, nuevaCaracteristica);
    }

    private void preguntarCaracteristica(Nodo<Informacion> nodo) {
        print.print("¿Tu animal tiene la siguiente característica? ");
        print.printColor(print.YELLOW, nodo.getDato().getInfo().toUpperCase());
        boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");
        preguntarRec(respuesta ? nodo.getNodoSi() : nodo.getNodoNo());
    }

    //Visualización con listas (parte 2)-------------------------------------------------------------------------------------------------------------------------------------------
    public void menuLista(){
        int opcion;
        endl.run();
        do {
            endl.run();
            print.printlnColor(print.Orange, "¿Qué deseas hacer?");
            print.println(print.YELLOW + "[1]." + print.RESET + "  Añadir animales a la lista de manera ascendente");
            print.println(print.YELLOW + "[2]." + print.RESET + "  Añadir animales a la lista de manera descendente");
            print.println(print.YELLOW + "[3]." + print.RESET + "  Ordenar la lista en orden de su código");
            print.println(print.YELLOW + "[4]." + print.RESET + "  Invertir lista");
            print.println(print.YELLOW + "[5]." + print.RESET + "  Mostrar la lista");
            print.println(print.YELLOW + "[6]." + print.RESET + "  Buscar un animal en la lista");
            print.println(print.YELLOW + "[7]." + print.RESET + "  Atrás");

            opcion = ingresoDatos.validOpcionNum(7, "Ingrese una opción: ");
            ejecutarOpcionLista(opcion);
        }while (opcion != 7);
    }

    private void agregarAnimalesALista(boolean agregarInicio) {
        if (!lista.isEmpty()) {
            print.printlnColor(print.RED, "La lista ya tiene datos, Deseas sobre escribir la lista?");
            if (!ingresoDatos.validBoolean("¿Deseas sobre escribir la lista? (s/n): ")) {
                return;
            }
        }

        lista = arbol.obtenerDatosYMapear(agregarInicio, map);
        imprimirLista();
        print.printlnColor(print.GREEN, "Agregando animales a la lista en " + (agregarInicio ? "pre Orden con addFirst()" : "post Orden con addLast()"));


    }

    private void imprimirLista() {
        if(lista.isEmpty()){
            print.printlnColor(print.RED, "Error: La lista está vacía");
            if(ingresoDatos.validBoolean("¿Deseas agregar animales a la lista? (s/n): ")){
                int op = ingresoDatos.validOpcionNum(2,
                        "Agregar animales:"+
                                print.Orange+ "\n[1]." + print.RESET+ " Inicio "+
                                print.Orange+ "\n[2]." + print.RESET+ " Al final \n");
                    agregarAnimalesALista(op == 1);

            }

            return;
        }
        print.printlnColor(print.GREEN, "Lista de animales: ");
        lista.display();
    }



    private void search() {
        if (lista.isEmpty()) {
            print.printlnColor(print.RED, "La lista está vacía");
            return;
        }

        String animal = ingresoDatos.validString("Ingrese el nombre del animal a buscar: ");
        lista.features(animal, map);

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
                print.printlnColor(print.GREEN, "Lista actual...");
                imprimirLista();
                lista.sort();
                print.printlnColor(print.GREEN, "Lista ordenada...");
                imprimirLista();
                break;
            case 4:
                //reverse();
                print.printlnColor(print.GREEN, "Lista actual...");
                imprimirLista();
                lista.reverse();
                print.printlnColor(print.GREEN, "Lista reversa...");
                imprimirLista();
                break;
            case 5:
                //display();
                imprimirLista();
                break;
            case 6:
                //features();
                search();
                break;
            case 7:
                break;

        }
    }








    //Carga de lista con datos quemados por si acaso--------------------------------------------------------------------
    private void cargaQuemado(){
        arbol = new Arbol();//Se reinicia el árbol
        print.printlnColor(print.GREEN,"Cargando datos del árbol!");
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
        arbol.insertar(ave);//Raíz
        // Nivel 2
        arbol.insertarHijo(ave, aguila, true);      // ave -> Sí -> aguila
        arbol.insertarHijo(ave, reptil, false);     // ave -> No -> reptil

        // Nivel 2
        arbol.insertarHijo(reptil, lagarto, true);  // reptil -> Sí -> lagarto
        arbol.insertarHijo(reptil, mamifero, false);// reptil -> No -> mamífero

        // Nivel 3
        arbol.insertarHijo(mamifero, acuatico, true);   // mamífero -> Sí -> acuático
        arbol.insertarHijo(mamifero, pez, false);       // mamífero -> No -> pez

        // Nivel 4
        arbol.insertarHijo(acuatico, ballena, true);    // acuático -> Sí -> ballena
        arbol.insertarHijo(acuatico, maulla, false);    // acuático -> No -> maúlla

        // Nivel 5
        arbol.insertarHijo(maulla, gato, true);         // maúlla -> Sí -> gato
        arbol.insertarHijo(maulla, perro, false);       // maúlla -> No -> perro

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

}
