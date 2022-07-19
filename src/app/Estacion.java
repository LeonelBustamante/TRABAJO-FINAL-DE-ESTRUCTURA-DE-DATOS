package app;

public class Estacion {
    private final String NOMBRE, calle, ciudad, codigoPostal;
    private final int numero;
    private int cantidadVias, cantidadPlataformas;

    public Estacion(String NOMBRE, String calle, int numero, String ciudad, String codigoPostal, int cantidadVias,
            int cantidadPlataformas) {
        this.NOMBRE = NOMBRE;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.cantidadVias = cantidadVias;
        this.cantidadPlataformas = cantidadPlataformas;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public int getCantidadVias() {
        return cantidadVias;
    }

    public int getCantidadPlataformas() {
        return cantidadPlataformas;
    }

    public void setCantidadVias(int cantidadVias) {
        this.cantidadVias = cantidadVias;
    }

    public void setCantidadPlataformas(int cantidadPlataformas) {
        this.cantidadPlataformas = cantidadPlataformas;
    }

    public String toString() {
        return "Estacion:" + NOMBRE + ": " + calle + ", " + numero + ", " + ciudad + ", " + codigoPostal + ", "
                + cantidadVias + ", " + cantidadPlataformas;

    }
}
