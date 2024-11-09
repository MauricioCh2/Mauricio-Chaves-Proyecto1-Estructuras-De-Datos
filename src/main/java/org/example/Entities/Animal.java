package org.example.Entities;

import java.util.Objects;

public class Animal extends Informacion {

    public Animal(String nombre) {
        super(nombre);
    }
    public Animal(String nombre, int nivel) {
        super(nombre, nivel);
    }


    // La implementación de getInfo y setInfo ya está en InformacionConNivel
    // Métodos adicionales pueden ser añadidos aquí si es necesario

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(getInfo(), animal.getInfo()) && getNivel() == animal.getNivel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo(), getNivel());
    }



}
