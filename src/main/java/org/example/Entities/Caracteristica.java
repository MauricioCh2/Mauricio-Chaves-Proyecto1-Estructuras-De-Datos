package org.example.Entities;

import java.util.Objects;

public class Caracteristica extends InformacionConNivel {

    public Caracteristica(String texto) {
        super(texto); // Usa el constructor de InformacionConNivel para establecer la info
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caracteristica that = (Caracteristica) o;
        return Objects.equals(getInfo(), that.getInfo()) && getNivel() == that.getNivel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo(), getNivel());
    }
}
