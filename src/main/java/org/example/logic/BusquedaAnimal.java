package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.utilities.ingresoDatos;
import org.example.utilities.print;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BusquedaAnimal {
    private Arbol arbol;
    private Nodo<Informacion>  nodoRaiz;

    private Runnable endl = System.out::println;


    private Runnable printArboRunnable = () -> { // Runnable para que se mas facil estar imprimuiendo el arbol
        print.println("Raiz: "+ arbol.getRaizDato().getInfo());
        endl.run();
        print.yellowLine();//-------------------------
        print.printlnColor(print.BLUE,"InOrden: ");
        arbol.inOrden();
        endl.run();
        print.printlnColor(print.BLUE,"preOrden: ");
        arbol.preOrden();
        endl.run();
        print.printlnColor(print.BLUE,"postOrden: ");
        arbol.postOrden();
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
        this.nodoRaiz = arbol.getRaiz();
        //--------
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
        arbol.insertar(ave);              // Nivel raíz
        arbol.insertarHijoSi(ave, aguila);
        arbol.insertarHijoNo(ave, reptil);

        // Insertar hijos de "reptil"
        arbol.insertarHijoSi(reptil, lagarto);
        arbol.insertarHijoNo(reptil, mamifero);

        // Insertar hijos de "mamifero"
        arbol.insertarHijoSi(mamifero, acuatico);
        arbol.insertarHijoNo(mamifero, pez);

        // Insertar hijos de "acuatico"
        arbol.insertarHijoSi(acuatico, ballena);
        arbol.insertarHijoNo(acuatico, maulla);

        // Insertar hijos de "maulla"
        arbol.insertarHijoSi(maulla, gato);
        arbol.insertarHijoNo(maulla, perro);

        // Insertar hijos de "pez"
        arbol.insertarHijoSi(pez, beta);
        arbol.insertarHijoNo(pez, invertebrado);

        // Insertar hijos de "invertebrado"
        arbol.insertarHijoSi(invertebrado, arana);
        arbol.insertarHijoNo(invertebrado, anfibio);

        // Insertar hijos de "anfibio"
        arbol.insertarHijoSi(anfibio, rana);
        arbol.insertarHijoNo(anfibio, culebra);

    }
    public void pruebas(){
        print.printColor(print.GREEN,"Arbol creado\n");
        printArboRunnable.run();

//        printWordGreen.accept("Arbol podado\n");
//        arbol.podar();
//        printArboRunnable.run();

    }

    public void preguntar(){ //Aqui se hace la pregunta usando un arbol auxiliar para no tener que reiniciar el arbol en caso de volver a preguntar

        Nodo<Informacion> nodoAux = arbol.getRaiz();
        preguntarRec(nodoAux);

        //aqui hay que pone la parte de ingreso de caracteristica y animal
    }

    private void preguntarRec(Nodo<Informacion> nodo) {

        if (nodo.getDato() instanceof Animal) {
            print.printlnColor(print.GREEN, "El animal es: " + nodo.getDato().getInfo());
            boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");
            if (respuesta) {
                print.printlnColor(print.GREEN, "¡Soy un genio!");
            } else {
                print.printlnColor(print.GREEN, "¡Oh no! ¿Qué animal era?");
                String nuevoAnimal = ingresoDatos.validString("Por favor, ingresa el nombre del animal: ");
                String nuevaCaracteristica = ingresoDatos.validString("Ahora, ingresa una característica que diferencie a un(a) " + nodo.getDato().getInfo() + " de un(a) " + nuevoAnimal + ": ");

                Animal animal = new Animal(nuevoAnimal);
                Caracteristica caracteristica = new Caracteristica(nuevaCaracteristica);

                // Guardar el animal actual (nodo.getDato()) en una nueva referencia
                Animal animalAnterior = (Animal) nodo.getDato();

                // Actualizar el nodo actual con la nueva característica
                nodo.setDato(caracteristica);

                // Crear nodos hijos para el nuevo animal y el animal anterior
                nodo.setNodoSi(new Nodo<>(animal));        // "Sí" -> Nuevo animal
                nodo.setNodoNo(new Nodo<>(animalAnterior)); // "No" -> Animal anterior

                printArboRunnable.run();

            }
            return;
        }
        print.print("Tu animal tiene la siguiente característica? " + print.YELLOW );
        print.printColor(print.YELLOW,nodo.getDato().getInfo().toUpperCase());
        boolean respuesta = ingresoDatos.validBoolean(" (s/n): ");

        preguntarRec(respuesta ? nodo.getNodoSi() : nodo.getNodoNo());
    }

    public void jugar(){
        boolean seguirJugando = true;

        while(seguirJugando){
            preguntar();

            seguirJugando = ingresoDatos.validBoolean("Quieres seguir jugando? (s/n): ");

        }

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
