package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.utilities.print;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private Nodo<Informacion> raiz;

    public Arbol() {
        raiz = null;
    }


    //Inserciones ------------------------------------------------------------------------------------------------------
    public void insertar(Informacion dato) {
        if (raiz == null) {
            System.out.println("Instanciando la primera vez");
            raiz = new Nodo<>(dato);
        } else {
            System.out.println("Insertando recursivamente");
            if (dato instanceof Caracteristica) {
                raiz = insertarCaracteristica(raiz, (Caracteristica) dato);
            } else if (dato instanceof Animal) {
                raiz = insertarAnimal(raiz, (Animal) dato);
            }
        }
    }

    // Inserta una característica en el árbol recursivamente
    private Nodo<Informacion> insertarCaracteristica(Nodo<Informacion> actual, Caracteristica dato) {
        if (actual == null) {
            return new Nodo<>(dato);
        }

        // Si el nodo actual es una característica, inserta la nueva característica a la izquierda o derecha
        if (actual.getDato() instanceof Caracteristica) {
            if (actual.getNodoNo() == null) {
                actual.setNodoNo(new Nodo<>(dato));
            } else {
                actual.setNodoNo(insertarCaracteristica(actual.getNodoNo(), dato));
            }
        } else {
            // Si el nodo actual es un animal, no debería tener características como descendientes.
            System.out.println("No se puede agregar una característica bajo un animal.");
        }
        return actual;
    }

    // Inserta un animal en el árbol recursivamente
    private Nodo<Informacion> insertarAnimal(Nodo<Informacion> actual, Animal dato) {
        if (actual == null) {
            System.out.println("No se puede insertar un animal en una posición vacía sin una característica.");
            return null;
        }

        // Si el nodo actual es una característica, intenta insertar el animal a la derecha.
        if (actual.getDato() instanceof Caracteristica) {
            if (actual.getNodoSi() == null) {
                actual.setNodoSi(new Nodo<>(dato));
            } else if (actual.getNodoSi().getDato() instanceof Caracteristica) {
                // Si el nodo derecho es una característica, recurre por la derecha para buscar un lugar adecuado.
                actual.setNodoSi(insertarAnimal(actual.getNodoSi(), dato));
            } else {
                // Si ya hay un animal en el nodo derecho, no se puede insertar otro sin otra característica.
                System.out.println("Ya existe un animal en este camino. Se requiere una nueva característica.");
            }
        } else {
            System.out.println("No se puede agregar un animal bajo otro animal.");
        }
        return actual;
    }

    //Incerta a la derecha
    public void insertarHijoSi(Informacion padre, Informacion hijo) {
        Nodo nodoPadre = buscarNodo(raiz, padre);
        if (nodoPadre != null) {
            if (nodoPadre.getNodoSi() == null) {
                nodoPadre.setNodoSi(new Nodo(hijo));
            } else {
                System.out.println("El nodo ya tiene un hijo en la rama 'Sí'.");
            }
        } else {
            System.out.println("No se encontró el nodo padre.");
        }
    }

    //Incerta a la izquierda
    public void insertarHijoNo(Informacion padre, Informacion hijo) {
        Nodo nodoPadre = buscarNodo(raiz, padre);
        if (nodoPadre != null) {
            if (nodoPadre.getNodoNo() == null) {
                nodoPadre.setNodoNo(new Nodo(hijo));
            } else {
                System.out.println("El nodo ya tiene un hijo en la rama 'No'.");
            }
        } else {
            System.out.println("No se encontró el nodo padre.");
        }
    }

    //Utiles------------------------------------------------------------------------------------------------------------
    private Nodo buscarNodo(Nodo actual, Informacion info) {
        if (actual == null) {
            return null;
        }
        if (actual.getDato().equals(info)) {
            return actual;
        }
        Nodo encontrado = buscarNodo(actual.getNodoSi(), info);
        if (encontrado == null) {
            encontrado = buscarNodo(actual.getNodoNo(), info);
        }
        return encontrado;
    }

//    private Nodo<Informacion> rotarIzquierda(Nodo<Informacion> nodo) {
//        Nodo<Informacion> nuevaRaiz = nodo.getNodoSi();  // El nodo derecho se convierte en la nueva raíz.
//        nodo.setNodoSi(nuevaRaiz.getNodoNo());  // El subárbol izquierdo del nuevo nodo raíz se convierte en el derecho del nodo original.
//        nuevaRaiz.setNodoNo(nodo);  // El nodo original se convierte en el hijo izquierdo de la nueva raíz.
//
//        return nuevaRaiz;  // Retorna la nueva raíz tras la rotación.
//    }
    public void rotarIzquierda(Nodo<Informacion> nodo) {
        Nodo<Informacion> aux = nodo;
        nodo.setDato(nodo.getNodoSi().getDato());
        nodo.setNodoSi(nodo.getNodoSi().getNodoSi());
        nodo.setNodoNo(new Nodo<>(aux.getDato()));
    }


    private Nodo<Informacion> rotarDerecha(Nodo<Informacion> nodo) {
        Nodo<Informacion> nuevaRaiz = nodo.getNodoNo();  // El nodo izquierdo se convierte en la nueva raíz.
        nodo.setNodoNo(nuevaRaiz.getNodoSi());  // El subárbol derecho del nuevo nodo raíz se convierte en el izquierdo del nodo original.
        nuevaRaiz.setNodoSi(nodo);  // El nodo original se convierte en el hijo derecho de la nueva raíz.

        return nuevaRaiz;  // Retorna la nueva raíz tras la rotación.
    }
//    private void rotarIzquierda(){
//        Nodo<Informacion> ra = raiz;
//        Nodo<Informacion> izq = raiz.getNodoNo();
//        Nodo<Informacion> izq2 = raiz.getNodoNo().getNodoNo();
//        raiz = izq;
//        raiz.setNodoSi(ra);
//        raiz.setNodoNo(izq2);
//
//    }

    public int nivel(Informacion dato) {
        return nivelRec(raiz, dato, 0);
    }

    private int nivelRec(Nodo<Informacion> nodo, Informacion dato, int nivel) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.getDato().equals(dato)) {
            return nivel;
        }
        int izq = nivelRec(nodo.getNodoNo(), dato, nivel + 1);
        if (izq != -1) {
            return izq;
        }
        return nivelRec(nodo.getNodoSi(), dato, nivel + 1);
    }

    public void podar() {
        podarRec(raiz);
    }

    private Nodo<Informacion> podarRec(Nodo<Informacion> nodo) {
        if(nodo == null){
            return null;
        }
        if(nodo.getNodoNo() == null && nodo.getNodoSi() == null){

            return null;
        }
        nodo.setNodoNo(podarRec(nodo.getNodoNo()));
        nodo.setNodoSi(podarRec(nodo.getNodoSi()));

        return nodo;
    }

    public  Nodo<Informacion>  moverSi() {
        print.printColor(print.RED, "Mover a la derecha");


        return raiz.getNodoSi();
    }
    public  Nodo<Informacion> moverNo() {
        print.printColor(print.RED, "Mover a la izquierda");
        return raiz.getNodoNo();
    }



    //Impresion---------------------------------------------------------------------------------------------------------
    public void inOrden() {
        inOrdenRec(raiz);
    }
    private void inOrdenRec(Nodo<Informacion> nodo) {
        if (nodo != null) {
            inOrdenRec(nodo.getNodoNo());
            print.print(nodo.getDato().getInfo() + " ");
            inOrdenRec(nodo.getNodoSi());
        }
    }

    public void preOrden() {
        preOrden(raiz);
    }

    private void preOrden(Nodo<Informacion> nodo) {
        if (nodo != null) {
            print.print(nodo.getDato().getInfo() + " ");
            preOrden(nodo.getNodoNo());
            preOrden(nodo.getNodoSi());
        }
    }

    public void postOrden() {
        postOrden(raiz);
    }

    private void postOrden(Nodo<Informacion> nodo) {
        if (nodo != null) {
            postOrden(nodo.getNodoNo());
            postOrden(nodo.getNodoSi());
            print.print(nodo.getDato().getInfo() + " ");
        }
    }

    //Getters-----------------------------------------------------------------------------------------------------------


    public Informacion getRaizDato() {
        return raiz.getDato();
    }
    public Nodo<Informacion> getRaiz() {
        return raiz;
    }

    public List<Informacion> obtenerDatosEnOrden() {
        List<Informacion> datos = new ArrayList<>();
        obtenerDatosEnOrdenRec(raiz, datos);
        return datos;
    }

    private void obtenerDatosEnOrdenRec(Nodo<Informacion> nodo, List<Informacion> datos) {
        if (nodo != null) {
            datos.add(nodo.getDato());
            obtenerDatosEnOrdenRec(nodo.getNodoNo(), datos);
            obtenerDatosEnOrdenRec(nodo.getNodoSi(), datos);
        }
    }
}



