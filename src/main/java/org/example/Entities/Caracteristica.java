package org.example.Entities;

import java.util.Objects;

public class Caracteristica implements Informacion {
    private String caracteristica;

    public Caracteristica(String texto) {
        this.caracteristica = texto;
    }


    @Override
    public String getInfo() {
        return this.caracteristica;
    }

    @Override
    public void setInfo(String info) {
        this.caracteristica = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caracteristica that = (Caracteristica) o;
        return Objects.equals(caracteristica, that.caracteristica);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(caracteristica);
    }
}
