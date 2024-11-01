package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.Entities.InformacionConNivel;
import org.example.utilities.print;

import java.util.Collections;
import java.util.Optional;//Para hacer mas legible las busquedas de nodos

public class Arbol {
    private Nodo<Informacion> raiz;
    public Arbol() {
        raiz = null;
    }


    //Inserciones ------------------------------------------------------------------------------------------------------
    public void insertar(Informacion dato) { // Inserta el primer nodo
        if (raiz == null) {
            print.printlnColor(print.PURPLE, "<<<<Instanciando la primera vez>>>>");
            raiz = new Nodo<>(dato, 0); // Nivel 0 para el primer nodo
        } else {
            print.println("Insertando recursivamente");
            if (dato instanceof Caracteristica) {
                insertarCaracteristica(dato, 1); // Inicia con nivel 1 para el siguiente nivel
            } else if (dato instanceof Animal) {
                insertarAnimal(dato, 1);
            }
        }
    }

    private void insertarCaracteristica(Informacion caracteristica, int nivel) {
        raiz = insertarNodo(raiz, caracteristica, true, nivel);
    }

    private void insertarAnimal(Informacion animal, int nivel) {
        raiz = insertarNodo(raiz, animal, false, nivel);
    }

    private Nodo<Informacion> insertarNodo(Nodo<Informacion> actual, Informacion dato, boolean esCaracteristica, int nivel) {
        if (actual == null) return new Nodo<>(dato, nivel); // Asigna el nivel actual
        if (esCaracteristica && actual.getDato() instanceof Caracteristica) {
            if (actual.getNodoNo() == null) {
                actual.setNodoNo(new Nodo<>(dato, nivel)); // Asigna nivel
            } else {
                actual.setNodoNo(insertarNodo(actual.getNodoNo(), dato, true, nivel + 1)); // Aumenta nivel recursivamente
            }
        } else if (!esCaracteristica && actual.getDato() instanceof Caracteristica) {
            if (actual.getNodoSi() == null) {
                actual.setNodoSi(new Nodo<>(dato, nivel)); // Asigna nivel
            } else if (actual.getNodoSi().getDato() instanceof Caracteristica) {
                actual.setNodoSi(insertarNodo(actual.getNodoSi(), dato, false, nivel + 1)); // Aumenta nivel recursivamente
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
        nodoPadre.ifPresentOrElse(n -> { // Evaluación de si el nodo padre existe
            int nivelHijo = n.getNivel() + 1; // Nivel del nuevo hijo
            Nodo<Informacion> nodoHijo = esHijoSi ? n.getNodoSi() : n.getNodoNo();
            if (nodoHijo == null) {
                if (esHijoSi) n.setNodoSi(new Nodo<>(hijo, nivelHijo));
                else n.setNodoNo(new Nodo<>(hijo, nivelHijo));
            } else {
                print.print("El nodo ya tiene un hijo en la rama " + (esHijoSi ? "'Sí'" : "'No'") + ".");
            }
        }, () -> print.print("No se encontró el nodo padre.")); // Mensaje de error si no se encuentra el nodo padre
    }


    //Utiles------------------------------------------------------------------------------------------------------------
    public void actualizarConNuevaCaracteristica(Nodo<Informacion> nodo, String nuevoAnimal, String nuevaCaracteristica) {
        // Convertir el dato actual en Animal para guardarlo como "No"
        Animal animalAnterior = (Animal) nodo.getDato();

        // Crear la nueva característica y el nuevo animal
        Caracteristica caracteristica = new Caracteristica(nuevaCaracteristica);
        Animal animal = new Animal(nuevoAnimal);

        // Rotación a la izquierda
        // Actualizar el nodo actual con la característica
        nodo.setDato(caracteristica);
        int nivelHijo = nodo.getNivel() + 1; // Calcula el nivel del nuevo hijo
        // Crear nodos hijos para el nuevo animal y el animal anterior
        nodo.setNodoSi(new Nodo<>(animal, nivelHijo));        // "Sí" -> Nuevo animal
        nodo.setNodoNo(new Nodo<>(animalAnterior, nivelHijo)); // "No" -> Animal anterior
    }


    public Optional<Nodo<Informacion>> buscarNodo(Nodo<Informacion> actual, Informacion info) {
        if (actual == null) return Optional.empty();
        if (actual.getDato().equals(info)) return Optional.of(actual);
        return Optional.ofNullable(buscarNodo(actual.getNodoSi(), info).orElse(buscarNodo(actual.getNodoNo(), info).orElse(null)));
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

    public boolean existeDato(Informacion dato) {
        return existeDatoRecursivo(raiz, dato);
    }

    private boolean existeDatoRecursivo(Nodo<Informacion> nodo, Informacion dato) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getDato().getInfo().toLowerCase().equals(dato.getInfo().toLowerCase())) {
            return true;
        }
        return existeDatoRecursivo(nodo.getNodoNo(), dato) || existeDatoRecursivo(nodo.getNodoSi(), dato);
    }

    //Obtencion de datos------------------------------------------------------------------------------------------------
    public Contenedor<Informacion> obtenerDatosInOrder(boolean agregarInicio){
        Contenedor<Informacion> datos = new Contenedor<>();
        if (agregarInicio) {
            obtenerDatosPreOrderRec(raiz, datos);
        } else {
            obtenerDatosPostOrderRec(raiz, datos);
        }

        return datos;
    }

    private void obtenerDatosPreOrderRec(Nodo<Informacion> raiz, Contenedor<Informacion> datos) {
        if (raiz == null) {
            return;
        }
        // Agrega el dato y el nivel actual del nodo
        datos.addFirst(new InformacionConNivel(raiz.getDato().getInfo(), raiz.getNivel()) {
        }); // suponiendo que InformacionConNivel es una clase que contiene el dato y el nivel


        obtenerDatosPreOrderRec(raiz.getNodoNo(), datos);
        obtenerDatosPreOrderRec(raiz.getNodoSi(), datos);
    }
    private void obtenerDatosPostOrderRec(Nodo<Informacion> raiz, Contenedor<Informacion> datos) {
        if (raiz == null) {
            return;
        }
        obtenerDatosPostOrderRec(raiz.getNodoNo(), datos);
        obtenerDatosPostOrderRec(raiz.getNodoSi(), datos);
        // Agrega el dato y el nivel actual del nodo
        datos.addLast(new InformacionConNivel(raiz.getDato().getInfo(), raiz.getNivel()) {
        }); // suponiendo que InformacionConNivel es una clase que contiene el dato y el nivel
    }




    private int obtenerAltura(Nodo<Informacion> nodo) {
        if (nodo == null) {
            return 0;
        }

        int alturaNo = obtenerAltura(nodo.getNodoNo());
        int alturaSi = obtenerAltura(nodo.getNodoSi());

        return Math.max(alturaNo, alturaSi) + 1;
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

    public void imprimirArbolXNivel() {
        int altura = obtenerAltura(raiz);
        for (int nivel = 1; nivel <= altura; nivel++) {
            obtenerDatosNivel(raiz, nivel);
        }
    }

    private void obtenerDatosNivel(Nodo<Informacion> nodo, int nivel) {
        if (nodo == null) return;

        if (nivel == 1) {
            print.print("\t"+ (nodo.getDato() instanceof Animal?"Animal: ": "Rasgo1: ")+ nodo.getDato().getInfo() + ", Nivel: " + nodo.getNivel() + "\n");
        } else if (nivel > 1) {
            obtenerDatosNivel(nodo.getNodoNo(), nivel - 1);
            obtenerDatosNivel(nodo.getNodoSi(), nivel - 1);
        }
    }

    //Getters-----------------------------------------------------------------------------------------------------------
    public Informacion getRaizDato() {
        return raiz.getDato();
    }
    public Nodo<Informacion> getRaiz() {
        return raiz;
    }


}



