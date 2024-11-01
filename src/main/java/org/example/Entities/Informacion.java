package org.example.Entities;

public interface Informacion extends Comparable<Informacion> {//El comparable sirve para poder comparar de manera generica a la hora de sortear
    String getInfo();
    int getNivel();
}
