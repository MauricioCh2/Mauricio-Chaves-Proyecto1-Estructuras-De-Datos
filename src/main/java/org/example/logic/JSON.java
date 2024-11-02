package org.example.logic;

import org.example.Entities.Animal;
import org.example.Entities.Caracteristica;
import org.example.Entities.Informacion;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSON {

    private static final String FILE_PATH = "arbol.json"; // Ruta al archivo JSON

    // Método para guardar el árbol en un archivo JSON
    public static void guardarArbol(Arbol arbol) {
        try {
            JSONObject jsonArbol = nodoAJson(arbol.getRaiz());
            try (FileWriter file = new FileWriter(FILE_PATH)) {
                file.write(jsonArbol.toString(4)); // Formato de impresión con sangría
                System.out.println("Árbol guardado exitosamente en " + FILE_PATH);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el árbol: " + e.getMessage());
        }
    }

    // Método recursivo para convertir un nodo en un JSONObject
    private static JSONObject nodoAJson(Nodo<Informacion> nodo) {
        if (nodo == null) {
            return null;
        }

        JSONObject jsonNodo = new JSONObject();

        jsonNodo.put("dato", nodo.getDato().getInfo());
        jsonNodo.put("nivel", nodo.getNivel());
        jsonNodo.put("tipo", (nodo.getDato() instanceof Caracteristica ? "Caracteristica" : "Animal"));

        // Llamadas recursivas para los nodos hijos
        jsonNodo.put("nodoSi", nodoAJson(nodo.getNodoSi()));
        jsonNodo.put("nodoNo", nodoAJson(nodo.getNodoNo()));


        return jsonNodo;
    }


    public static Arbol cargarArbol() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONObject jsonArbol = new JSONObject(json);
            return jsonANodo(jsonArbol);
        } catch (IOException e) {
            System.err.println("Error al cargar el árbol: " + e.getMessage());
            return null;
        }
    }


    // Método para cargar un árbol desde un archivo JSON
    private static Arbol jsonANodo(JSONObject jsonArbol) {
        Arbol arbol = new Arbol();
        Nodo<Informacion> raiz = jsonANodoRec(jsonArbol);
        arbol.setRaiz(raiz);
        return arbol;
    }

    private static Nodo<Informacion> jsonANodoRec(JSONObject jsonNodo) {
        if (jsonNodo == null) {
            return null;
        }

        String tipo = jsonNodo.getString("tipo");
        Informacion dato;
        if (tipo.equals("Caracteristica")) {
            dato = new Caracteristica(jsonNodo.getString("dato"));
        } else {
            dato = new Animal(jsonNodo.getString("dato"));
        }

        Nodo<Informacion> nodo = new Nodo<>(dato, jsonNodo.getInt("nivel"));
        nodo.setNodoSi(jsonANodoRec(jsonNodo.optJSONObject("nodoSi")));
        nodo.setNodoNo(jsonANodoRec(jsonNodo.optJSONObject("nodoNo")));

        return nodo;
    }



}
