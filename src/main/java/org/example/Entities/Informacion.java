package org.example.Entities;

public interface Informacion {
    String getInfo();
    void  setInfo(String info);


    int compareTo(InformacionConNivel info);
}
