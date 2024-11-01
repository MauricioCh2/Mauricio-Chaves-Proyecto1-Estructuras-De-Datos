package org.example.logic;

public class NodoL<T> {
    private  T dato;
    private  NodoL<T> nodoSig ;
    private  NodoL<T> nodoAnt ;

    public NodoL(){
        dato = null;
        nodoSig = null;
        nodoAnt = null;
    }
    public NodoL(T dato, NodoL<T> nodoAnt, NodoL<T> nodoSig){
        this.dato = dato;
        this.nodoAnt = nodoAnt;
        this.nodoSig = nodoSig;
    }

    public T getDato(){
        return dato;
    }

    public void setDato(T dato){
        this.dato = dato;
    }

    public NodoL<T> getNodoSig(){
        return nodoSig;
    }


    public void setNodoSig (NodoL<T> nodoSig){
        this.nodoSig = nodoSig;
    }

    public NodoL<T> getNodoAnt(){
        return nodoAnt;
    }

    public void setNodoAnt (NodoL<T> nodoAnt){
        this.nodoAnt = nodoAnt;
    }

    @Override
    public String toString() {
        return dato.toString();
    }

}
