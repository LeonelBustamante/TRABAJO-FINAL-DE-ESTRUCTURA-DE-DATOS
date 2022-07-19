package tools;

import java.util.Arrays;

public class DiccionarioHash {
    // HASH ABIERTO
    private int TAMANIO;
    private NodoHashDicc[] tabla;
    private int cant;

    public DiccionarioHash(int longitud) {
        this.TAMANIO = longitud;
        this.tabla = new NodoHashDicc[this.TAMANIO];
        this.cant = 0;
    }

    public boolean existeClave(Object key) {
        boolean exito = false;
        NodoHashDicc aux = obtenerNodo(key);
        if (aux != null) {
            exito = true;
        }
        return exito;
    }

    private NodoHashDicc obtenerNodo(Object key) {
        NodoHashDicc exito = null;
        int pos = 0;
        if (!this.esVacio()) {

            while (exito == null && pos < this.TAMANIO) {
                NodoHashDicc aux = this.tabla[pos];
                while (aux != null) {
                    if (aux.getClave().equals(key)) {
                        exito = aux;
                    }
                    aux = aux.getEnlace();
                }
                pos++;
            }
        }
        return exito;
    }

    public Object obtenerDato(Object key) {
        NodoHashDicc nodo = obtenerNodo(key);
        Object dato = null;
        if (nodo != null) {
            dato = nodo.getDato();
        }
        return dato;
    }

    public boolean insertar(Object key, Object elem) {

        boolean exito = true;
        int pos = Math.abs(key.hashCode() % this.TAMANIO);
        NodoHashDicc nodo = this.tabla[pos];
        if (exito && this.tabla[pos] == null) {
            this.tabla[pos] = new NodoHashDicc(key, elem);
        } else {
            while (exito && nodo.getEnlace() != null) {
                if (nodo.getClave().equals(key)) {
                    exito = false;
                }
                nodo = nodo.getEnlace();
            }
            nodo.setEnlace(new NodoHashDicc(key, elem));
        }
        this.cant++;
        return exito;

    }

    public boolean eliminar(Object key) {

        boolean exito = false;
        int pos;
        NodoHashDicc nodo;
        pos = Math.abs(key.hashCode() % this.TAMANIO);
        nodo = this.tabla[pos];
        if (!exito && this.tabla[pos] != null) {
            if (this.tabla[pos].getClave().equals(key)) {
                this.tabla[pos] = null;
            } else {
                while (!exito && nodo.getEnlace() != null) {
                    if (nodo.getEnlace().getClave().equals(key)) {
                        nodo.setEnlace(nodo.getEnlace().getEnlace());
                        exito = true;
                    }
                    nodo = nodo.getEnlace();
                }
            }
        }
        this.cant--;
        return exito;
    }

    public boolean esVacio() {
        return (this.cant == 0);
    }

    public Lista listarClaves() {
        Lista lis = new Lista();
        int pos = 0;
        if (!this.esVacio()) {
            while (lis.longitud() < this.cant) {
                NodoHashDicc aux = this.tabla[pos];
                while (aux != null) {
                    lis.insertar(aux.getClave(), lis.longitud() + 1);
                    aux = aux.getEnlace();
                }
                pos++;
            }
        }
        return lis;
    }

    public Lista listarDatos() {
        Lista lis = new Lista();
        int pos = 0;
        if (!this.esVacio()) {
            while (lis.longitud() < this.cant) {
                NodoHashDicc aux = this.tabla[pos];
                while (aux != null) {
                    lis.insertar(aux.getDato(), lis.longitud() + 1);
                    aux = aux.getEnlace();
                }
                pos++;
            }
        }
        return lis;
    }

    public void vaciar() {
        int i;
        for (i = 0; i < this.TAMANIO; i++) {
            this.tabla[i] = null;
        }
        this.cant = 0;
    }

    public DiccionarioHash clone() {
        DiccionarioHash copia = new DiccionarioHash(this.TAMANIO);
        int pos = 0;
        if (!this.esVacio()) {
            while (copia.cant < this.cant) {
                NodoHashDicc aux = this.tabla[pos];

                NodoHashDicc aux2 = copia.tabla[pos];
                if (aux2 == null) {
                    copia.tabla[pos] = new NodoHashDicc(aux.getClave(), aux.getDato());
                    aux = aux.getEnlace();
                    aux2 = copia.tabla[pos];
                    copia.cant++;
                }

                while (aux != null) {
                    aux2.setEnlace(new NodoHashDicc(aux.getClave(), aux.getDato()));
                    copia.cant++;
                    aux2 = aux2.getEnlace();
                    aux = aux.getEnlace();

                }
                pos++;
            }
        }
        return copia;
    }

    public String toString() {
        String texto = "SIN INFORMACION";

        if (!this.esVacio()) {
            texto = "TABLA:";
            NodoHashDicc aux;
            for (int i = 0; i < this.TAMANIO; i++) {
                aux = this.tabla[i];
                texto += "\nPOS " + i + ": " + aux.getClave() + " - " + aux.getDato();
                int j = 1;
                while (aux.getEnlace() != null) {
                    aux = aux.getEnlace();
                    texto += "\nPOS " + i + "-" + j + ": " + aux.getClave() + " - " + aux.getDato();
                    j++;
                }
                aux = aux.getEnlace();
            }
        }
        return texto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.TAMANIO;
        hash = 97 * hash + Arrays.deepHashCode(this.tabla);
        hash = 97 * hash + this.cant;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiccionarioHash other = (DiccionarioHash) obj;
        if (this.TAMANIO != other.TAMANIO) {
            return false;
        }
        if (this.cant != other.cant) {
            return false;
        }
        if (!Arrays.deepEquals(this.tabla, other.tabla)) {
            return false;
        }
        return true;
    }

}
