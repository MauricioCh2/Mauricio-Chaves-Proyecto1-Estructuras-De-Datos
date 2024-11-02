package org.example.logic;


import org.example.utilities.print;

import java.util.HashMap;
import java.util.Map;


public class Contenedor<T extends Comparable<? super T>>{
    private  NodoL<T> dummy;
    private NodoL<T> actual;
    private NodoL<T> ultimo;
    private final Map<String,Contenedor<String>> map;

    public Contenedor(){
        this.dummy = null;
        this.actual = null;
        this.ultimo = null;

        map = new HashMap<>();
    }
    public Contenedor(Contenedor<T> otro) {
        this(); // Inicializar el contenedor vacío
        NodoL<T> actual = otro.dummy;
        while (actual != null) {
            this.addLast(actual.getDato()); // Copiar cada dato al nuevo contenedor
            actual = actual.getNodoSig();
        }
    }

    public void addFirst(T dato){
        NodoL<T> nuevo = new NodoL<>(dato, null, dummy);
        if (dummy != null) {
            dummy.setNodoAnt(nuevo);
        } else {
            ultimo = nuevo; // Si la lista estaba vacía, el último será el nuevo nodo
        }
        dummy = nuevo;



    }

    public void addLast(T dato) {
        NodoL<T> nuevo = new NodoL<>(dato, ultimo, null);
        if (dummy == null) {
            dummy = nuevo;
        } else {
            ultimo.setNodoSig(nuevo);
        }
        ultimo = nuevo;

    }

    public void sort(){
        if(dummy == null) {
            print.printlnColor(print.RED, "No hay datos para ordenar, la lista esta vacía");
        }
        else {
            quickSort(dummy, ultimo);
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
            // Usando el método compareTo genérico
            if (j.getDato().compareTo(pivote) <= 0) {//si el dato es menor o igual al pivote se intercambian (el compare ayuda a que sea genérico)
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
        if(dummy == null){
            print.printlnColor(print.RED, "No hay datos para revertir, la lista esta vacía");
        }else{
            NodoL<T> temp = null;
            actual = dummy;
            while (actual != null){
                temp = actual.getNodoAnt();//guarda el nodo anterior o null si es el primero
                actual.setNodoAnt(actual.getNodoSig());//cambia el nodo anterior por el siguiente
                actual.setNodoSig(temp);//Cambia el nodo siguiente por el anterior
                actual = actual.getNodoAnt(); //vamos al anterior que antes era el siguiente y asi sucesivamente hasta llegar a null
            }
            if(temp != null){
                dummy = temp.getNodoAnt();
            }
        }
    }


    public void display() {
        if (dummy == null) {
            return;
        } else {
            actual = dummy;
            while (actual.getNodoSig() != null) {
               print.print(actual.getDato() + " ");
                actual = actual.getNodoSig();
            }
            print.print(actual.getDato() + " ");
        }
        print.println("");
    }



    public void features(String animal, Map<String, Contenedor<String>> map) {
        String animalL = animal.toLowerCase();

        if (!map.containsKey(animalL)) {
            print.println("No existe");
            return;
        }

        // Obtener las características del animal
        Contenedor<String> caracteristicas = map.get(animalL);
        print.print("'" + animalL + "'" + "-->[");
        for (NodoL<String> nodo = caracteristicas.getFirst(); nodo != null; nodo = nodo.getNodoSig()) {
            print.print("'" + nodo.getDato()+"'" + (nodo.getNodoSig() != null ? ", " : ""));
        }
        print.println("]");


    }


    public boolean isEmpty() {
        return dummy == null;
    }

    public NodoL<T> getFirst() {
        return dummy;
    }
}
