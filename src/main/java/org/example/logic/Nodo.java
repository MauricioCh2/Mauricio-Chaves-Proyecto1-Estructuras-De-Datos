package org.example.logic;

public class Nodo<T> {
    private  T dato;
    private int nivel;
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

    public int getNivel() {  // Nuevo método para obtener el nivel
        return nivel;
    }

    public void setNivel(int nivel) {  // Nuevo método para establecer el nivel
        this.nivel = nivel;
    }

}


