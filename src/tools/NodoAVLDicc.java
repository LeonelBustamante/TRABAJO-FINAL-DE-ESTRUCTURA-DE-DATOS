package tools;

class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc izquierdo, NodoAVLDicc derecho) {
        this.clave = clave;
        this.dato = dato;
        this.altura = 0;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public NodoAVLDicc(Comparable clave, Object dato) {
        this.clave = clave;
        this.dato = dato;
        this.altura = 0;
        this.izquierdo = null;
        this.derecho = null;
    }

    public Comparable getClave() {
        return clave;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVLDicc getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVLDicc izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAVLDicc getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVLDicc derecho) {
        this.derecho = derecho;
    }

    public void recalcularAltura() {
        int alturaIzq = 0;
        int alturaDer = 0;
        if (izquierdo != null) {
            alturaIzq = izquierdo.getAltura();
        }
        if (derecho != null) {
            alturaDer = derecho.getAltura();
        }
        altura = Math.max(alturaIzq, alturaDer) + 1;
    }

}
