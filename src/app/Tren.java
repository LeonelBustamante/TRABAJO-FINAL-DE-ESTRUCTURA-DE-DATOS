package app;

public class Tren {

    private static int creaID = 0;
    private String combustible, linea;
    private int id, cantidadVagonesPasajeros, cantidadVagonesCarga;

    public Tren(String combustible, int cantidadVagonesPasajeros, int cantidadVagonesCarga, String lineaAsignada) {
        this.id = creaID;
        this.combustible = combustible;
        this.cantidadVagonesPasajeros = cantidadVagonesPasajeros;
        this.cantidadVagonesCarga = cantidadVagonesCarga;
        this.linea = lineaAsignada;
        creaID++;
    }

    public static int ultimoID() {
        return creaID;
    }

    public int getId() {
        return id;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public int getCantidadVagonesPasajeros() {
        return cantidadVagonesPasajeros;
    }

    public void setCantidadVagonesPasajeros(int cantidadVagonesPasajeros) {
        this.cantidadVagonesPasajeros = cantidadVagonesPasajeros;
    }

    public int getCantidadVagonesCarga() {
        return cantidadVagonesCarga;
    }

    public void setCantidadVagonesCarga(int cantidadVagonesCarga) {
        this.cantidadVagonesCarga = cantidadVagonesCarga;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String toString() {
        return "Interno_" + id + ": " + combustible + ", " + cantidadVagonesPasajeros + ", " + cantidadVagonesCarga
                + ", " + linea;
    }

}
