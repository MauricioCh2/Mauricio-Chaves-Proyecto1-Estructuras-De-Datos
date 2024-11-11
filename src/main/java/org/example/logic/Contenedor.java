package org.example.logic;


import org.example.utilities.print;

import java.util.Map;


public class Contenedor<T extends Comparable<? super T>>{
    private   NodoL<T> dummy;
    private  NodoL<T> back;


    public Contenedor(){
        this.dummy = new NodoL<>(null, null, null);
        this.back = new NodoL<>(null, null, null);

        this.dummy.setNodoSig(back);
        this.back.setNodoAnt(dummy);

    }
    public Contenedor(Contenedor<T> otro) {
        this(); // Inicializar el contenedor vacío
        NodoL<T> actual = otro.dummy.getNodoSig();
        while (actual != otro.back) {
            this.addLast(actual.getDato()); // Copiar cada dato al nuevo contenedor
            actual = actual.getNodoSig();
        }
    }

    public void addFirst(T dato){
        NodoL<T> nuevo = new NodoL<>(dato, dummy, dummy.getNodoSig());
        dummy.getNodoSig().setNodoAnt(nuevo);
        dummy.setNodoSig(nuevo);

    }

    public void addLast(T dato) {
        NodoL<T> nuevo = new NodoL<>(dato, back.getNodoAnt(), back);
        back.getNodoAnt().setNodoSig(nuevo);
        back.setNodoAnt(nuevo);

    }

    public void sort(){
        if(isEmpty()) {
            print.printlnColor(print.RED, "No hay datos para ordenar, la lista esta vacía");
        }
        else {
            quickSort(dummy.getNodoSig(), back.getNodoAnt());
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

    public void reverse() {
        if (isEmpty()) {
            print.printlnColor(print.RED, "No hay datos para revertir, la lista está vacía");
            return;
        }

        NodoL<T> actual = dummy;
        NodoL<T> temp;

        // Recorre la lista desde dummy hasta back, invirtiendo las referencias
        while (actual != null) {
            temp = actual.getNodoAnt(); // Guarda la referencia anterior
            actual.setNodoAnt(actual.getNodoSig()); // Cambia la referencia anterior por la siguiente
            actual.setNodoSig(temp); // Cambia la referencia siguiente por la anterior
            actual = actual.getNodoAnt(); // Avanza al siguiente nodo (en la dirección invertida)
        }

        // Ajusta dummy y back al final del proceso
        temp = dummy;
        this.dummy = back;
        this.back = temp;
    }



    public void display() {
        if (isEmpty()) {
            return;
        } else {

            NodoL<T> actualD = dummy.getNodoSig();
            while (actualD!= back) {
               print.print(actualD.getDato() + " ");
                actualD = actualD.getNodoSig();
            }
        }
        print.println("");
    }



    public void features(String animal, Map<String, Contenedor<String>> map) {
        String animalL = animal.toLowerCase();

        if (!map.containsKey(animalL)) {
            print.printlnColor(print.RED,"No existe");
            return;
        }

        // Obtener las características del animal
        Contenedor<String> caracteristicas = map.get(animalL);
        print.print("'" + animalL + "'" + "-->[");
        for (NodoL<String> nodo = caracteristicas.getFirst(); nodo != caracteristicas.back; nodo = nodo.getNodoSig()) {
            print.print("'" + nodo.getDato()+"'" + (nodo.getNodoSig() != caracteristicas.back ? ", " : ""));
        }
        print.println("]");


    }


    public boolean isEmpty() {
        return dummy.getNodoSig() == back;
    }

    public NodoL<T> getFirst() {
        return isEmpty() ? null : dummy.getNodoSig();
    }
}
