package org.example.logic;

public class Nodo<T> {
    private  T dato;
    private Nodo<T> hijoNo;
    private Nodo<T> hijoSi;

    public Nodo(T dato) {
        this.dato = dato;
        hijoNo = null;
        hijoSi = null;
    }

    public T getDato() {
        return dato;
    }

    public Nodo<T> getNodoNo() {
        return hijoNo;
    }

    public Nodo<T> getNodoSi() {
        return hijoSi;
    }

    public void setNodoNo(Nodo<T> nodo) {
        hijoNo = nodo;
    }

    public void setNodoSi(Nodo<T> nodo) {
        hijoSi = nodo;
    }

    public void setDato(T dato){
        this.dato = dato;
    }

}


