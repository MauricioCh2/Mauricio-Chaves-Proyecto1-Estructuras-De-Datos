package org.example.Entities;

import java.util.Objects;

public abstract class InformacionConNivel implements Informacion {
    private String info;
    private int nivel; // Campo para almacenar el nivel

    public InformacionConNivel(String info) {
        this.info = info;
        this.nivel = 0; // Nivel inicial
    }
    public InformacionConNivel(String info, int nivel) {
        this.info = info;
        this.nivel = nivel; // Nivel inicial
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "\tInfo: " + info + ", Nivel: " + nivel + "\n";
    }

    @Override
    public int compareTo(InformacionConNivel info) {
        return Integer.compare(getNivel(), info.getNivel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformacionConNivel that)) return false;
        return nivel == that.nivel && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, nivel);
    }
}
