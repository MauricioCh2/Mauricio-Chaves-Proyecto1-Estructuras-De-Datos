package org.example.logic;


import org.example.Entities.Animal;
import org.example.Entities.Informacion;
import org.example.utilities.print;

import java.util.HashMap;
import java.util.Map;


public class Contenedor<T> {
    private  NodoL<T> inicio;
    private NodoL<T> actual;
    private NodoL<T> ultimo;
    private Map<String,Contenedor<String>> map;

    public Contenedor(){
        this.inicio = null;
        this.actual = null;
        this.ultimo = null;

        map = new HashMap<>();
    }

    public void addFirst(T dato){
        NodoL<T> nuevo = new NodoL<>(dato, null, inicio);
        if (inicio != null) {
            inicio.setNodoAnt(nuevo);
        } else {
            ultimo = nuevo; // Si la lista estaba vacía, el último será el nuevo nodo
        }
        inicio = nuevo;

        // Agrega al mapa el animal y sus características
        actualizarMapa(dato);


    }
    public void addLast(T dato) {
        NodoL<T> nuevo = new NodoL<T>(dato, ultimo, null);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            ultimo.setNodoSig(nuevo);
        }
        ultimo = nuevo;

        // Agrega al mapa el animal y sus características
        actualizarMapa(dato);

    }

    public void display() {
        if (inicio == null) {
            print.printlnColor(print.RED, "No hay datos para imprimir, la lista esta vacia");
        } else {
            actual = inicio;
            while (actual.getNodoSig() != null) {
                System.out.print(actual.getDato() + " ");
                actual = actual.getNodoSig();
            }
            print.print(actual.getDato() + " ");
        }
        print.println("");
    }

    public void sort(){
        if(inicio == null) {
            print.printlnColor(print.RED, "No hay datos para ordenar, la lista esta vacia");
        }
        else {
            quickSort(inicio, ultimo);
        }
    }
    private void quickSort(NodoL<T> inicio, NodoL<T> ultimo){
        if(inicio != ultimo && ultimo != null && inicio != ultimo.getNodoSig()){
            NodoL<T> pivote = partition(inicio, ultimo);
            quickSort(inicio, pivote.getNodoAnt());
            quickSort(pivote.getNodoSig(), ultimo);
        }
    }
    private NodoL<T> partition(NodoL<T> inicio, NodoL<T> ultimo) {
        NodoL<T> i = inicio.getNodoAnt();
        NodoL<T> j = inicio;
        T pivote = ultimo.getDato();
        while (j != ultimo) {
            if (((Comparable<T>) j.getDato()).compareTo(pivote) <= 0) {
                i = (i == null) ? inicio : i.getNodoSig();
                T temp = i.getDato();
                i.setDato(j.getDato());
                j.setDato(temp);
            }
            j = j.getNodoSig();
        }
        i = (i == null) ? inicio : i.getNodoSig();
        T temp = i.getDato();
        i.setDato(ultimo.getDato());
        ultimo.setDato(temp);
        return i;
    }


    public void reverse(){
        if(inicio == null){
            print.printlnColor(print.RED, "No hay datos para revertir, la lista esta vacia");
        }else{
            NodoL<T> temp = null;
            actual = inicio;
            while (actual != null){
                temp = actual.getNodoAnt();//guarda el nodo anterior o null si es el primero
                actual.setNodoAnt(actual.getNodoSig());//cambia el nodo anterior por el siguiente
                actual.setNodoSig(temp);//Cambia el nodo siguiente por el anterior
                actual = actual.getNodoAnt(); //vamos al anterior que antes era el siguiente y asi sucesivamente hasta llegar a null
            }
            if(temp != null){
                inicio = temp.getNodoAnt();
            }
        }
    }


    private void actualizarMapa(T dato) {
        if (dato instanceof Animal) {
            Informacion info = (Animal) dato;
            Contenedor<String> contenedorCaracteristicas = new Contenedor<>();
            // Aquí se debe agregar la lógica para llenar el contenedor con las características
            map.put(info.getInfo(), contenedorCaracteristicas);
        }
    }

    public Contenedor<String> search(String animal) {
        return map.getOrDefault(animal, null);
    }


    public boolean isEmpty() {
        return inicio == null;
    }
}
