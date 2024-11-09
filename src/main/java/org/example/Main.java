/*
Autor: Mauricio Alberto Chaves Chaves

Nota: Leer el arvhivo Readme.md para una mejor comprendion de la ejecucion del proyecto por medio de maven si se desea.
 */
package org.example;
import org.example.logic.*;

public class Main {
   BusquedaAnimal busquedaAnimal = new BusquedaAnimal();

   public  static void main(String[] args) {
      Main main = new Main();
      main.busquedaAnimal.iniciar();
   }
}