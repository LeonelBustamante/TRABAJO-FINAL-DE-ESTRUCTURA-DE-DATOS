package tools;

public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public Lista clone() {
        Lista clon = new Lista();
        if (!esVacia()) {
            clon.cabecera = new Nodo(this.cabecera.getElem(), null);
            Nodo auxOrig = this.cabecera.getEnlace();
            Nodo auxClon = clon.cabecera;
            while (auxOrig != null) {
                auxClon.setEnlace(new Nodo(auxOrig.getElem(), null));
                auxOrig = auxOrig.getEnlace();
                auxClon = auxClon.getEnlace();
            }
            clon.longitud = this.longitud;
        }
        return clon;
    }

    public boolean eliminar(int pos) {

        boolean exito = true;
        if (pos < 1 || pos > this.longitud) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
                this.longitud--;
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
                this.longitud--;
            }
        }
        return exito;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud++;
        }
        return exito;
    }

    public int localizar(Object buscado) {
        int res = -1;
        if (!esVacia()) {
            Nodo aux = this.cabecera;
            int i = 1;
            boolean corte = false;
            while (aux != null && !corte) {
                if (aux.getElem().equals(buscado)) {
                    res = i;
                    corte = true;
                }
                i++;
                aux = aux.getEnlace();
            }
        }
        return res;
    }

    public int longitud() {
        return this.longitud;
    }

    public Object recuperar(int pos) {
        Object res;
        if (pos < 1 || pos > this.longitud) {
            res = null;
        } else {
            int i = 1;
            Nodo aux = this.cabecera;
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            res = aux.getElem();
        }
        return res;
    }

    public String toString() {
        String cadena = "ESTRUCTURA VACIA";
        if (!esVacia()) {
            cadena = "";
            Nodo aux = this.cabecera;
            while (aux != null) {
                cadena += aux.getElem() + " \n";
                aux = aux.getEnlace();
            }

        }
        return cadena;
    }

    public void vaciar() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public void invertir() {
        // Metodo que envia la cabecera y se comienza a recorrer recursivamente
        // la lista. Cuando se llegue al ultimo nodo se asignara a la cabecera
        // y se retornara para setear como enlace el nodo anterior
        Nodo ultimo = invertirAux(this.cabecera);
        ultimo.setEnlace(null);
    }

    private Nodo invertirAux(Nodo nodoActual) {
        if (nodoActual.getEnlace() == null) {
            // Cuando llegue al ultimo nodo lo asigno a la cabecera
            this.cabecera = nodoActual;
        } else {
            Nodo nodoVolviendo = invertirAux(nodoActual.getEnlace());
            nodoVolviendo.setEnlace(nodoActual);
        }
        return nodoActual;
    }

    public Lista obtenerMultiplos(int num) {
        /**
         * Agregar al TDA Lista la operación obtenerMultiplos(int num) que
         * recibe un número y devuelve una lista nueva que contiene todos los
         * elementos de las posiciones múltiplos de num, en el mismo orden
         * encontrado, haciendo un único recorrido de las estructuras original y
         * copia; y sin usar otras operaciones del TDA. Ejemplo: si se invoca
         * con la lista <A,B,C,D,E,F,G,H,I,J> y num=3, el método debe devolver
         * la lista <C,F,I>
         */

        /**
         * <res> es la lista que se retornara con elementos solamente si la
         * lista {this} no esta vacia y <num> es un numero dentro del rango
         *
         * Eficiencia: Como para obtener los multiplos necesito saber todos los
         * elementos, se recorre toda la lista por ende es orden n
         */
        Lista res = new Lista();

        if (this.cabecera != null && num > 0 && num < this.longitud) {
            // Si no esta vacio y el numero esta dentro del rango
            Nodo puntero = this.cabecera;
            int posActual = 1;

            while (puntero != null) {

                if (posActual % num == 0) {
                    // Se encontro un nodo multiplo
                    Nodo nuevo = new Nodo(puntero.getElem(), null); // Nodo a insertar

                    if (res.cabecera == null) {
                        // res se encuentra vacia
                        res.cabecera = nuevo;
                    } else {
                        // res no se encuentra vacia
                        nuevo.setEnlace(res.cabecera);
                        res.cabecera = nuevo;
                    }

                    res.longitud += 1;
                }

                posActual += 1;
                puntero = puntero.getEnlace();

            } // fin while
        } // fin cuerpo if
        return res;
    }

    public void eliminarApariciones(Object buscado) {
        /**
         * Agregar al TDA Lista la operación eliminarApariciones(TipoElemento x)
         * que elimine todas las apariciones de elementos iguales a x, haciendo
         * un único recorrido de la estructura y sin usar otras operaciones del
         * TDA.
         */

        /**
         * <buscado> sera el elemento buscado en la lista y que debe ser
         * eliminado
         *
         * Eficiencia: Para poder realizar la eliminación de todas las
         * apariciones se debio recorrer toda la estructura con 2 punteros 1 vez
         * sola por ende es de orden n
         */
        Nodo punteroAvanzado = cabecera;
        Nodo punteroPrevio = null;

        while (punteroAvanzado != null) {
            if (punteroAvanzado.getElem().equals(buscado)) {
                // Si encuentro al elemento reviso si es la cabecera
                punteroAvanzado = punteroAvanzado.getEnlace();

                if (punteroAvanzado == cabecera) {
                    cabecera = punteroAvanzado;
                } else {
                    punteroPrevio.setEnlace(punteroAvanzado);
                }

                this.longitud--;
            } else {
                // No se encontro el elemento
                punteroPrevio = punteroAvanzado;
                punteroAvanzado = punteroAvanzado.getEnlace();
            }
        }
    }

    public void insertarAnterior(Object elem1, Object elem2) {
        if (!esVacia()) {
            Nodo aux = this.cabecera;
            while (aux != null) {
                if (aux.getElem().equals(elem1)) {

                }

            }

        }

    }

}
