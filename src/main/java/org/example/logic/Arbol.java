package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.example.Entities.InformacionConNivel;
import org.example.utilities.print;


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
        }
    }

    // Inserción de hijos ----------------------------------------------------------------------------------------
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


    //Altura------------------------------------------------------------------------------------------------------------
    private int obtenerAltura(Nodo<Informacion> nodo) {
        if (nodo == null) {
            return 0;
        }

        int alturaNo = obtenerAltura(nodo.getNodoNo());
        int alturaSi = obtenerAltura(nodo.getNodoSi());

        return Math.max(alturaNo, alturaSi) + 1;
    }

    //Impresion---------------------------------------------------------------------------------------------------------
    //Recorridos convencionales
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

    //Impresion por nivel
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

    //Obtencion de datos------------------------------------------------------------------------------------------------
    //Estos metodos son usados especificamente para la parte 2
    public Contenedor<Informacion> obtenerDatosInOrder(boolean agregarInicio) {
        Contenedor<Informacion> datos = new Contenedor<>();
        obtenerDatosRec(raiz, datos, agregarInicio);
        return datos;
    }

    private void obtenerDatosRec(Nodo<Informacion> nodo, Contenedor<Informacion> datos, boolean agregarInicio) {
        if (nodo == null) {
            return;
        }
        if (agregarInicio) {//Pre-Orden
            agregarDato(nodo, datos, true);
        }
        obtenerDatosRec(nodo.getNodoNo(), datos, agregarInicio);
        obtenerDatosRec(nodo.getNodoSi(), datos, agregarInicio);
        if (!agregarInicio) {//Post-Orden
            agregarDato(nodo, datos, false);
        }
    }

    private void agregarDato(Nodo<Informacion> nodo, Contenedor<Informacion> datos, boolean agregarInicio) {
        Informacion dato = nodo.getDato();
        if (dato instanceof Animal) {
            Animal animal = new Animal(dato.getInfo(), nodo.getNivel());
            if (agregarInicio) {
                datos.addFirst(animal);
            } else {
                datos.addLast(animal);
            }
        } else if (dato instanceof Caracteristica) {
            Caracteristica caracteristica = new Caracteristica(dato.getInfo(), nodo.getNivel());
            if (agregarInicio) {
                datos.addFirst(caracteristica);
            } else {
                datos.addLast(caracteristica);
            }
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



