package tools;

public class Cola {

    private Nodo fin;
    private Nodo frente;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public Cola clone() {
        // Retorna una copia de la cola.
        Cola clon = new Cola();
        if (!esVacia()) {
            clon.frente = new Nodo(this.frente.getElem(), null);
            Nodo aux1 = this.frente.getEnlace();
            Nodo aux2 = clon.frente;
            while (aux1 != null) {
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                aux2 = aux2.getEnlace();
                aux1 = aux1.getEnlace();
            }
            clon.fin = aux2;
        }
        return clon;
    }

    public boolean esVacia() {
        // Retorna true si la cola está vacía.
        return this.frente == null;
    }

    public Object obtenerFrente() {
        // Retorna el elemento del frente de la cola.
        return esVacia() ? null : this.frente.getElem();
    }

    public boolean poner(Object nuevoElem) {
        // Agrega un nuevo elemento al final de la cola.
        Nodo nuevo = new Nodo(nuevoElem, null);
        if (esVacia()) {
            this.frente = nuevo;
            this.fin = nuevo;
        } else {
            this.fin.setEnlace(nuevo);
            this.fin = nuevo;
        }
        return true;
    }

    public boolean sacar() {
        // Elimina el elemento del frente de la cola.
        boolean exito = false;
        if (!esVacia()) {
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
            exito = true;
        }
        return exito;
    }

    @Override
    public String toString() {
        // Retorna una representación en string de la cola.
        String cadena = "ESTRUCTURA VACIA";
        if (!esVacia()) {
            cadena = "COLA DINAMICA: (Frente)-> [ ";
            Nodo aux = this.frente;
            while (aux != null) {
                cadena += aux.getEnlace() == null ? aux.getElem() : aux.getElem() + ", ";
                aux = aux.getEnlace();
            }
            cadena += " ] <-(Fin)";
        }
        return cadena;
    }

    public void vaciar() {
        // Vacía la cola.
        this.frente = null;
        this.fin = null;
    }
}
