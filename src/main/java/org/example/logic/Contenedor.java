package org.example.logic;

import org.example.utilities.print;


public class Contenedor<T> {
    private  NodoL<T> inicio;
    private NodoL<T> actual;
    private NodoL<T> ultimo;

    public Contenedor(){
        this.inicio = null;
        this.actual = null;
        this.ultimo = null;
    }

    public void addFirst(T dato){
        NodoL<T> nuevo = new NodoL<T>(dato, null, inicio );
        if (inicio !=null){
            inicio.setNodoAnt(nuevo);
        }else{
            ultimo = nuevo; //si la lista estaba vacia el ultimo sera el nuevo
        }

        inicio = nuevo;

    }
    public void addLast(T dato) {
        NodoL<T> nuevo = new NodoL<T>(dato, ultimo, null);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            ultimo.setNodoSig(nuevo);
        }
        ultimo = nuevo;
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



    public Integer search(T dato){
        int cont = 0;
        if(inicio == null){
            System.out.println("No hay datos para buscar, la lista esta vacia");
        }else{
            actual = inicio;
            while (actual.getNodoSig()!= null) {
                if(actual.getDato().equals(dato)){
                    return cont;
                }
                cont++;
                actual = actual.getNodoSig();
            }

        }

        return -1;

    }


    public boolean isEmpty() {
        return inicio == null;
    }
}
