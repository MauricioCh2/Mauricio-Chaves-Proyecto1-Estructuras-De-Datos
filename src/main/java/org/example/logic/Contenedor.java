package org.example.logic;

import java.util.function.Consumer;

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

    public void display(){
        if(inicio == null){
            System.out.println("No hay datos para imprimir, la lista esta vacia");
        }else{
            actual = inicio;
            while (actual.getNodoSig()!= null) {
                System.out.print(actual.getDato() + " ");
                actual = actual.getNodoSig();
            }
            System.out.print(actual.getDato() + " ");

        }
        System.out.println();
    }

    public void sort(){

    }

    public void reverse(){

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

    public void forEach(Consumer<? super T> action) {
        if (inicio != null) {
            actual = inicio;
            while (actual != null) {
                action.accept(actual.getDato());
                actual = actual.getNodoSig();
            }
        }
    }
}
