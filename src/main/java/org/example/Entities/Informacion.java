package org.example.Entities;

import java.util.Objects;

public abstract class Informacion implements Comparable<Informacion> {
    private final String info;
    private final int nivel; // Campo para almacenar el nivel

    public Informacion(String info) {
        this.info = info;
        this.nivel = 0; // Nivel inicial
    }
    public Informacion(String info, int nivel) {
        this.info = info;
        this.nivel = nivel;
    }


    public String getInfo() {
        return info;
    }


    public int getNivel() {
        return nivel;
    }


    @Override
    public String toString() {
        return "\tInfo: " + info + ", Nivel: " + nivel + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Informacion that)) return false;
        return nivel == that.nivel && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, nivel);
    }

    @Override
    public int compareTo(Informacion other) {
        return Integer.compare(this.getNivel(), other.getNivel());
    }
}
