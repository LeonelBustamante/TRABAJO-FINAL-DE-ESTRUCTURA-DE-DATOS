package app;

import java.io.*;
import java.util.StringTokenizer;
import tools.*;

public class Archivos {
    private static int nroLinea = 1;

    public static void limpiarTXT() {
        try {
            FileWriter fw = new FileWriter(
                    "D:/Documentos/Leo/TRABAJO-FINAL-DE-ESTRUCTURA-DE-DATOS/log.txt");
            fw.write("");
            nroLinea = 1;
        } catch (IOException e) {
            System.out.println("Error al limpiar el archivo");
        }
    }

    public static void leer(String dir, Grafo map, DiccionarioAVL est, DiccionarioHash trenes, MapeoAMuchos lineas) {
        try {
            BufferedReader txt = new BufferedReader(new FileReader(dir));
            String linea = txt.readLine();

            while (linea != null) {
                cargaInicial(linea, map, est, trenes, lineas);
                linea = txt.readLine();
            }
            escribirLog("👍👍👍CARGA INICIAL DE DATOS COMPLETADA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void escribirLog(String txt) {
        File archivo = new File("D:/Documentos/Leo/TRABAJO-FINAL-DE-ESTRUCTURA-DE-DATOS/log.txt");
        try {
            if (!archivo.exists()) {
                archivo = new File("D:/Documentos/Leo/TRABAJO-FINAL-DE-ESTRUCTURA-DE-DATOS/log.txt");
            }
            FileWriter escribir = new FileWriter(archivo, true);
            escribir.write(nroLinea + "-\t\t" + txt + "\n");
            nroLinea++;
            escribir.close();
        } catch (Exception e) {
            System.out.println("ERROR AL ESCRIBIR EL LOG");
        }
    }

    private static void cargaInicial(String renglon, Grafo mapa, DiccionarioAVL estaciones, DiccionarioHash trenes,
            MapeoAMuchos lineas) {
        StringTokenizer tokens = new StringTokenizer(renglon, ";");
        switch (tokens.nextToken()) {
            case "T":
                String combustible = tokens.nextToken();
                int vPasajeros = Integer.parseInt(tokens.nextToken());
                int vCarga = Integer.parseInt(tokens.nextToken());
                String linea = tokens.nextToken();
                Tren tren = new Tren(combustible, vPasajeros, vCarga, linea);
                trenes.insertar(tren.getId(), tren);
                escribirLog("TREN CARGADO : " + tren.getId());
                break;
            case "E":
                String nombre = tokens.nextToken();
                String calle = tokens.nextToken();
                int altura = Integer.parseInt(tokens.nextToken());
                String ciudad = tokens.nextToken();
                String codigoPostal = tokens.nextToken();
                int vias = Integer.parseInt(tokens.nextToken());
                int plataformas = Integer.parseInt(tokens.nextToken());
                Estacion estacion = new Estacion(nombre, calle, altura, ciudad, codigoPostal, vias, plataformas);
                estaciones.insertar(nombre, estacion);
                mapa.insertarVertice(estacion);
                escribirLog("ESTACION CARGADA: " + nombre);
                break;
            case "L":
                Linea lineaNueva = new Linea(tokens.nextToken());
                lineas.asociar(lineaNueva, estaciones.obtenerDato(tokens.nextToken()));
                lineas.asociar(lineaNueva, estaciones.obtenerDato(tokens.nextToken()));
                lineas.asociar(lineaNueva, estaciones.obtenerDato(tokens.nextToken()));
                lineas.asociar(lineaNueva, estaciones.obtenerDato(tokens.nextToken()));
                lineas.asociar(lineaNueva, estaciones.obtenerDato(tokens.nextToken()));
                escribirLog("LINEA CARGADA: " + lineaNueva.getNombre());
                break;
            case "R":
                Estacion estacion1 = (Estacion) estaciones.obtenerDato(tokens.nextToken());
                Estacion estacion2 = (Estacion) estaciones.obtenerDato(tokens.nextToken());
                int distancia = Integer.parseInt(tokens.nextToken());
                mapa.insertarArco(estacion1, estacion2, distancia);
                escribirLog("RUTA CARGADA: " + estacion1.getNOMBRE() + " - " + estacion2.getNOMBRE());
                break;
        }
    }

}
