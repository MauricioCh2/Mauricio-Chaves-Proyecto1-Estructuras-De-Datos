package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.utilities.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Arbol {
    private Nodo<Informacion> raiz;

    public Arbol() {
        raiz = null;
    }


    //Inserciones ------------------------------------------------------------------------------------------------------
    public void insertar(Informacion dato) {
        if (raiz == null) {
            print.print("Instanciando la primera vez");
            raiz = new Nodo<>(dato);
        } else {
            print.print("Insertando recursivamente");
            if (dato instanceof Caracteristica) {
                insertarCaracteristica(dato);
            } else if (dato instanceof Animal) {
                insertarAnimal(dato);
            }
        }
    }

    private void insertarCaracteristica(Informacion caracteristica) {
        raiz = insertarNodo(raiz, caracteristica, true);
    }

    private void insertarAnimal(Informacion animal) {
        raiz = insertarNodo(raiz, animal, false);
    }

    private Nodo<Informacion> insertarNodo(Nodo<Informacion> actual, Informacion dato, boolean esCaracteristica) {
        if (actual == null) return new Nodo<>(dato);
        if (esCaracteristica && actual.getDato() instanceof Caracteristica) {
            if (actual.getNodoNo() == null) {
                actual.setNodoNo(new Nodo<>(dato));
            } else {
                actual.setNodoNo(insertarNodo(actual.getNodoNo(), dato, true));
            }
        } else if (!esCaracteristica && actual.getDato() instanceof Caracteristica) {
            if (actual.getNodoSi() == null) {
                actual.setNodoSi(new Nodo<>(dato));
            } else if (actual.getNodoSi().getDato() instanceof Caracteristica) {
                actual.setNodoSi(insertarNodo(actual.getNodoSi(), dato, false));
            } else {
                print.print("Ya existe un animal en este camino. Se requiere una nueva característica.");
            }
        } else {
            print.print("No se puede agregar un elemento bajo un animal.");
        }
        return actual;
    }

    // Inserción de hijos (DRY) ----------------------------------------------------------------------------------------
    public void insertarHijo(Informacion padre, Informacion hijo, boolean esHijoSi) {
        Optional<Nodo<Informacion>> nodoPadre = buscarNodo(raiz, padre);
        nodoPadre.ifPresentOrElse(n -> {
            Nodo<Informacion> nodoHijo = esHijoSi ? n.getNodoSi() : n.getNodoNo();
            if (nodoHijo == null) {
                if (esHijoSi) n.setNodoSi(new Nodo<>(hijo));
                else n.setNodoNo(new Nodo<>(hijo));
            } else {
                print.print("El nodo ya tiene un hijo en la rama " + (esHijoSi ? "'Sí'" : "'No'") + ".");
            }
        }, () -> print.print("No se encontró el nodo padre."));
    }

    //Utiles------------------------------------------------------------------------------------------------------------
    private Optional<Nodo<Informacion>> buscarNodo(Nodo<Informacion> actual, Informacion info) {
        if (actual == null) return Optional.empty();
        if (actual.getDato().equals(info)) return Optional.of(actual);
        return Optional.ofNullable(buscarNodo(actual.getNodoSi(), info).orElse(buscarNodo(actual.getNodoNo(), info).orElse(null)));
    }

    public void actualizarConNuevaCaracteristica(Nodo<Informacion> nodo, String nuevoAnimal, String nuevaCaracteristica) {
        // Convertir el dato actual en Animal para guardarlo como "No"
        Animal animalAnterior = (Animal) nodo.getDato();

        // Crear la nueva característica y el nuevo animal
        Caracteristica caracteristica = new Caracteristica(nuevaCaracteristica);
        Animal animal = new Animal(nuevoAnimal);

        //Rotacion a la izquierda
        // Actualizar el nodo actual con la característica
        nodo.setDato(caracteristica);
        // Crear nodos hijos para el nuevo animal y el animal anterior
        nodo.setNodoSi(new Nodo<>(animal));        // "Sí" -> Nuevo animal
        nodo.setNodoNo(new Nodo<>(animalAnterior)); // "No" -> Animal anterior
    }

    public int nivel(Informacion dato) {
        return nivelRec(raiz, dato, 0);
    }

    private int nivelRec(Nodo<Informacion> nodo, Informacion dato, int nivel) {
        if (nodo == null) return -1;
        if (nodo.getDato().equals(dato)) return nivel;
        int izq = nivelRec(nodo.getNodoNo(), dato, nivel + 1);
        return izq != -1 ? izq : nivelRec(nodo.getNodoSi(), dato, nivel + 1);
    }

    //Obtencion de datos------------------------------------------------------------------------------------------------
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

    //Impresion---------------------------------------------------------------------------------------------------------
    public void imprimirRecorrido(String tipo) {
        if (tipo.equalsIgnoreCase("inOrden")) recorrer(raiz, "inOrden");
        else if (tipo.equalsIgnoreCase("preOrden")) recorrer(raiz, "preOrden");
        else if (tipo.equalsIgnoreCase("postOrden")) recorrer(raiz, "postOrden");
    }

    private void recorrer(Nodo<Informacion> nodo, String tipo) {
        if (nodo == null) return;
        //Si es preOrden se imprime primero el nodo, luego el izquierdo y luego el derecho
        if ("preOrden".equals(tipo)) print.print(nodo.getDato().getInfo() + " ");
        recorrer(nodo.getNodoNo(), tipo);
        //Si es inOrden se imprime primero el izquierdo, luego el nodo y luego el derecho
        if ("inOrden".equals(tipo)) print.print(nodo.getDato().getInfo() + " ");
        recorrer(nodo.getNodoSi(), tipo);
        //Si es preOrden se imprime primero el izquierdo, luego el derecho y por ultimo el nodo
        if ("postOrden".equals(tipo)) print.print(nodo.getDato().getInfo() + " ");
    }


    //Getters-----------------------------------------------------------------------------------------------------------
    public Informacion getRaizDato() {
        return raiz.getDato();
    }
    public Nodo<Informacion> getRaiz() {
        return raiz;
    }


}



