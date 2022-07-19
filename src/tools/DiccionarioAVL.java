package tools;

public class DiccionarioAVL {

    private NodoAVLDicc raiz;

    // CONSTRUCTOR
    public DiccionarioAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable key, Object elem) {
        boolean exito = true;
        if (this.esVacio()) {
            this.raiz = new NodoAVLDicc(key, elem);
        } else {
            exito = insertarAux(this.raiz, key, elem);
            if (Math.abs(verificarBalanceo(this.raiz)) > 1) {
                this.raiz = balancear(this.raiz);
            }
        }
        return exito;
    }

    private boolean insertarAux(NodoAVLDicc nodo, Comparable key, Object elem) {
        boolean exito = true;
        int balanceo;
        if (nodo != null) {
            if (key.compareTo(nodo.getClave()) == 0) { // ENCUENTRA UN ELEMENTO REPETIDO
                exito = false;
            } else if (key.compareTo(nodo.getClave()) < 0) {
                // elem es menor que nodo.getElemento()
                // si tiene HI baja a la izquierda, sino agrega elemento

                if (nodo.getIzquierdo() != null) {
                    exito = insertarAux(nodo.getIzquierdo(), key, elem);

                    nodo.recalcularAltura();
                    balanceo = verificarBalanceo(nodo.getIzquierdo());

                    if (Math.abs(balanceo) > 1) {

                        nodo.setIzquierdo(balancear(nodo.getIzquierdo()));
                        nodo.recalcularAltura();
                    }
                } else {
                    nodo.setIzquierdo(new NodoAVLDicc(key, elem));
                    nodo.recalcularAltura();
                }
            } else {
                // elem es mayor que n.getElem()
                // si tiene HD baja a la derecha, sino agrega elemento
                if (nodo.getDerecho() != null) {
                    exito = insertarAux(nodo.getDerecho(), key, elem);
                    nodo.recalcularAltura();
                    balanceo = verificarBalanceo(nodo.getDerecho());
                    if (Math.abs(balanceo) > 1) {

                        nodo.setDerecho(balancear(nodo.getDerecho()));
                        nodo.recalcularAltura();
                    }
                } else {
                    nodo.setDerecho(new NodoAVLDicc(key, elem));
                    nodo.recalcularAltura();
                }
            }
        }
        return exito;
    }

    private int verificarBalanceo(NodoAVLDicc nodo) {
        int balanceo, hI = -1, hD = -1;
        if (nodo.getIzquierdo() != null) {
            hI = nodo.getIzquierdo().getAltura();
        }

        if (nodo.getDerecho() != null) {
            hD = nodo.getDerecho().getAltura();
        }

        balanceo = hI - hD;

        return balanceo;
    }

    private NodoAVLDicc balancear(NodoAVLDicc nodo) {
        NodoAVLDicc h = null;
        int balanceo = verificarBalanceo(nodo);

        if (balanceo > 0 && verificarBalanceo(nodo.getIzquierdo()) >= 0) {

            h = rotarDerecha(nodo);
            h.recalcularAltura();
        } else {

            if (balanceo < 0 && verificarBalanceo(nodo.getDerecho()) <= 0) {
                h = rotarIzquierda(nodo);
                h.recalcularAltura();
            } else {
                if (balanceo > 0 && verificarBalanceo(nodo.getIzquierdo()) < 0) {
                    h = rotarIzquierdaDerecha(nodo);
                    h.recalcularAltura();
                } else {
                    if (balanceo < 0 && verificarBalanceo(nodo.getDerecho()) > 0) {
                        h = rotarDerechaIzquierda(nodo);
                        h.recalcularAltura();
                    }
                }
            }
        }

        return h;
    }

    private NodoAVLDicc rotarDerecha(NodoAVLDicc nodo) {

        NodoAVLDicc hijo = nodo.getIzquierdo();
        NodoAVLDicc temp = hijo.getDerecho();
        hijo.setDerecho(nodo);
        nodo.setIzquierdo(temp);
        nodo.recalcularAltura();
        return hijo;
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc nodo) {

        NodoAVLDicc hijo = nodo.getDerecho();
        NodoAVLDicc temp = hijo.getIzquierdo();
        hijo.setIzquierdo(nodo);
        nodo.setDerecho(temp);
        nodo.recalcularAltura();
        return hijo;
    }

    private NodoAVLDicc rotarIzquierdaDerecha(NodoAVLDicc nodo) {

        nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));

        return rotarDerecha(nodo);
    }

    private NodoAVLDicc rotarDerechaIzquierda(NodoAVLDicc nodo) {
        nodo.setDerecho(rotarDerecha(nodo.getDerecho()));

        return rotarIzquierda(nodo);
    }

    public boolean eliminar(Comparable key) {
        boolean exito = false;
        NodoAVLDicc encontrado = null;
        exito = eliminarAux(this.raiz, null, key);
        return exito;
    }

    private boolean eliminarAux(NodoAVLDicc nodo, NodoAVLDicc padre, Comparable key) {
        boolean exito = false;
        int balanceo;
        if (nodo != null) {
            if (nodo.getClave().compareTo(key) == 0) {
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    caso1(padre, nodo);
                    padre.recalcularAltura();

                } else {
                    if (nodo.getIzquierdo() != null && nodo.getDerecho() == null
                            || nodo.getIzquierdo() == null && nodo.getDerecho() != null) {
                        caso2(padre, nodo);
                        padre.recalcularAltura();

                    } else {
                        if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
                            caso3(nodo);
                            if (padre != null) {
                                padre.recalcularAltura();
                            }
                        }
                    }
                }
                exito = true;

                balanceo = verificarBalanceo(nodo);
                if (padre != null && padre.getIzquierdo() == nodo) {
                    if (balanceo > 1) {
                        padre.setIzquierdo(balancear(nodo));
                    }
                } else if (padre != null && padre.getDerecho() == nodo) {
                    if (balanceo > 1) {
                        padre.setDerecho(balancear(nodo));
                    }
                } else {
                    if (Math.abs(verificarBalanceo(this.raiz)) > 1) {
                        this.raiz = balancear(nodo);
                    }
                }

            } else {
                if (nodo.getIzquierdo() != null && nodo.getClave().compareTo(key) > 0 && !exito) {
                    exito = eliminarAux(nodo.getIzquierdo(), nodo, key);

                    nodo.recalcularAltura();
                    balanceo = verificarBalanceo(nodo);
                    if (Math.abs(balanceo) > 1) {
                        if (padre.getDerecho() == nodo) {
                            padre.setDerecho(balancear(nodo));
                        } else {
                            padre.setIzquierdo(balancear(nodo));
                        }

                    } else {
                        this.raiz.recalcularAltura();
                        if (Math.abs(verificarBalanceo(this.raiz)) > 1) {
                            this.raiz = balancear(nodo);
                        }
                    }
                } else {

                    if (nodo.getDerecho() != null && nodo.getClave().compareTo(key) < 0 && !exito) {
                        exito = eliminarAux(nodo.getDerecho(), nodo, key);

                        nodo.recalcularAltura();
                        balanceo = verificarBalanceo(nodo);
                        if (Math.abs(balanceo) > 1) {
                            if (padre.getIzquierdo() == nodo) {
                                padre.setIzquierdo(balancear(nodo));
                            } else {
                                padre.setDerecho(balancear(nodo));
                            }

                        } else {
                            this.raiz.recalcularAltura();
                            if (Math.abs(verificarBalanceo(this.raiz)) > 1) {
                                this.raiz = balancear(nodo);
                            }
                        }

                    }
                }
            }

        }
        return exito;
    }

    private void caso3(NodoAVLDicc nodo) {
        NodoAVLDicc candidato;
        candidato = buscarCandidato(nodo.getDerecho());
        nodo.setDato(candidato.getDato());
        nodo.setClave(candidato.getClave());
    }

    private NodoAVLDicc buscarCandidato(NodoAVLDicc nodo) {
        int balanceo;
        NodoAVLDicc candidato = null;
        NodoAVLDicc padreCandidato = nodo;
        NodoAVLDicc hijo = nodo.getIzquierdo();
        if (hijo != null) {

            if (hijo.getIzquierdo() == null) {
                candidato = hijo;
                if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
                    caso1(padreCandidato, hijo);

                } else {
                    if (hijo.getIzquierdo() != null && hijo.getDerecho() == null
                            || hijo.getIzquierdo() == null && hijo.getDerecho() != null) {
                        caso2(padreCandidato, hijo);
                    }
                }
            } else {
                candidato = buscarCandidato(nodo.getIzquierdo());
                padreCandidato.recalcularAltura();
                balanceo = verificarBalanceo(padreCandidato);
                if (Math.abs(balanceo) > 1) {
                    padreCandidato.setIzquierdo(balancear(hijo));
                }

            }

        }
        return candidato;
    }

    private void caso2(NodoAVLDicc padre, NodoAVLDicc nodo) {
        if (padre == null) {
            if (nodo.getIzquierdo() == null) {
                this.raiz = nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                this.raiz = nodo.getIzquierdo();
            }

        }
        if (padre.getIzquierdo() != null && padre.getIzquierdo().getClave().compareTo(nodo.getClave()) == 0) {
            if (nodo.getIzquierdo() == null) {
                padre.setIzquierdo(nodo.getDerecho());
            }
            if (nodo.getDerecho() == null) {
                padre.setIzquierdo(nodo.getIzquierdo());
            }
        }
        if (padre.getDerecho() != null && padre.getDerecho().getClave().compareTo(nodo.getClave()) == 0) {
            if (nodo.getIzquierdo() == null) {
                padre.setDerecho(nodo.getDerecho());
            }
            if (nodo.getDerecho() == null) {
                padre.setDerecho(nodo.getIzquierdo());
            }
        }
    }

    private void caso1(NodoAVLDicc padre, NodoAVLDicc nodo) {

        if (padre == null) {
            this.raiz = null;
        } else {
            if (padre.getIzquierdo() != null && padre.getIzquierdo().getClave().compareTo(nodo.getClave()) == 0) {
                padre.setIzquierdo(null);

            } else {
                if (padre.getDerecho() != null && padre.getDerecho().getClave().compareTo(nodo.getClave()) == 0) {
                    padre.setDerecho(null);

                }
            }
        }

    }

    private NodoAVLDicc obtenerNodo(NodoAVLDicc nodo, Comparable key) {
        NodoAVLDicc exito = null;
        if (nodo != null) {
            if (key.compareTo(nodo.getClave()) == 0) { // ENCUENTRA UN ELEMENTO REPETIDO
                exito = nodo;
            } else if (key.compareTo(nodo.getClave()) < 0) {
                // key es menor que nodo.getClave()
                // si tiene HI baja a la izquierda
                if (nodo.getIzquierdo() != null) {
                    exito = obtenerNodo(nodo.getIzquierdo(), key);
                }

            } else {
                // key es mayor que nodo.getClave()
                // si tiene HD baja a la derecha
                if (nodo.getDerecho() != null) {
                    exito = obtenerNodo(nodo.getDerecho(), key);
                }

            }
        }
        return exito;
    }

    public boolean existeClave(Comparable key) {
        boolean exito = false;
        if (!this.esVacio()) {
            exito = existeClaveAux(this.raiz, key);
        }
        return exito;
    }

    private boolean existeClaveAux(NodoAVLDicc nodo, Comparable key) {
        boolean exito = false;
        if (nodo != null) {
            if (key.compareTo(nodo.getClave()) == 0) { // ENCUENTRA UN ELEMENTO REPETIDO
                exito = true;
            } else if (key.compareTo(nodo.getClave()) < 0) {
                // elem es menor que nodo.getElemento()
                // si tiene HI baja a la izquierda
                if (nodo.getIzquierdo() != null) {
                    exito = existeClaveAux(nodo.getIzquierdo(), key);
                }

            } else {
                // elem es mayor que n.getElem()
                // si tiene HD baja a la derecha
                if (nodo.getDerecho() != null) {
                    exito = existeClaveAux(nodo.getDerecho(), key);
                }

            }
        }
        return exito;
    }

    public Object obtenerDato(Comparable key) {
        Object dato = null;
        NodoAVLDicc aux = obtenerNodo(this.raiz, key);
        if (aux != null) {
            dato = aux.getDato();
        }
        return dato;
    }

    public boolean esVacio() {
        return (this.raiz == null);
    }

    public Lista listarClaves() {
        /*
         * Llama a un metodo privado "ListarAux" donde se envia la raiz
         * del arbol mas una lista creada en este modulo
         */
        Lista lis = new Lista();
        listarClavesAux(this.raiz, lis);
        return lis;
    }

    private void listarClavesAux(NodoAVLDicc nodo, Lista lis) {
        /*
         * Metodo que recibe por parametro un nodo que es la raiz del subarbol
         * recorrido y una lista donde se listaran los elementos del arbol en in orden
         */

        if (nodo != null) {
            // RECORRE A SUS HIJOS EN INORDEN
            listarClavesAux(nodo.getIzquierdo(), lis);

            // VISITA EL ELEMENTO EN EL NODO
            lis.insertar(nodo.getClave(), lis.longitud() + 1);

            listarClavesAux(nodo.getDerecho(), lis);
        }

    }

    public Lista listarDatos() {
        /*
         * Llama a un metodo privado "ListarAux" donde se envia la raiz
         * del arbol mas una lista creada en este modulo
         */
        Lista lis = new Lista();
        listarDatosAux(this.raiz, lis);
        return lis;
    }

    private void listarDatosAux(NodoAVLDicc nodo, Lista lis) {
        /*
         * Metodo que recibe por parametro un nodo que es la raiz del subarbol
         * recorrido y una lista donde se listaran los elementos del arbol en in orden
         */

        if (nodo != null) {
            // RECORRE A SUS HIJOS EN INORDEN
            listarDatosAux(nodo.getIzquierdo(), lis);

            // VISITA EL ELEMENTO EN EL NODO
            lis.insertar(nodo.getDato(), lis.longitud() + 1);

            listarDatosAux(nodo.getDerecho(), lis);
        }

    }

    public DiccionarioAVL clone() {
        /*
         * Metodo que llama a metodo privado si el arbol no es vacio con elemento
         * raiz como parametro. Si el arbol esta vacio se retorna un arbol vacio
         */
        DiccionarioAVL copia = new DiccionarioAVL();

        if (!this.esVacio()) {
            copia.raiz = clonAux(this.raiz);
        }

        return copia;
    }

    private NodoAVLDicc clonAux(NodoAVLDicc nodo) {
        /*
         * Metodo privado que recibe nodo con raiz del arbol en primera instancia
         * clona un arbol recursivamente donde se va creando un nuevo nodo que sera
         * raiz del arbol clonado y se repite hasta las hojas del arbol original
         */
        NodoAVLDicc nuevoNodo = new NodoAVLDicc(nodo.getClave(), nodo.getDato());

        if (nodo.getIzquierdo() != null) {
            nuevoNodo.setIzquierdo(clonAux(nodo.getIzquierdo()));
        }
        if (nodo.getDerecho() != null) {
            nuevoNodo.setDerecho(clonAux(nodo.getDerecho()));
        }
        return nuevoNodo;
    }

    public void vaciar() {
        this.raiz = null;
    }

    public String toString() {
        /*
         * Metodo sin parametros para testeo de programa se recomienda comentar
         * cuando no se esta testeando. En caso de ser vacio se retorna un mensaje
         * "ARBOL VACIO" sino se llama a un metodo privado "toStringAux"
         */
        String texto = "";

        if (this.esVacio()) {
            texto = "ARBOL VACIO";
        } else {
            texto = toStringAux(this.raiz, texto);
        }

        return texto;
    }

    private String toStringAux(NodoAVLDicc nodo, String texto) {
        /*
         * Metodo privado que recibe un nodo siendo este la raiz en una primera
         * instancia junto con una cadena vacia que sera la retornada
         */

        if (nodo != null) {// En caso de nodo null se retorna una cadena vacia
            if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
                // En caso de tener ambos hijos se imprimen
                texto += " NODO: " + nodo.getClave().toString()
                        + " HI: " + nodo.getIzquierdo().getClave().toString() + " "
                        + " HD:  " + nodo.getDerecho().getClave().toString() + "\n";
            } else {
                // 3 posibles casos donde no se encuentra a ambos hijos se puede tener un hijo
                // null y el otro no o no tener ninguno
                if (nodo.getIzquierdo() == null && nodo.getDerecho() != null) {
                    texto += " NODO: " + nodo.getClave().toString() + " HI: --" + " HD: "
                            + nodo.getDerecho().getClave().toString() + "\n";
                } else if (nodo.getDerecho() == null && nodo.getIzquierdo() != null) {
                    texto += " NODO: " + nodo.getClave().toString() + " HI: "
                            + nodo.getIzquierdo().getClave().toString() + " " + " HD: --" + "\n";
                } else {
                    texto += " NODO: " + nodo.getClave().toString() + " HI: --" + " HD: --"
                            + "\n";
                }
            }
            // Si existieran nodos visitariamos estos hasta llegar al null
            texto = toStringAux(nodo.getIzquierdo(), texto);
            texto = toStringAux(nodo.getDerecho(), texto);
        }

        return texto;
    }

    public Lista listarRango(Comparable keyMenor, Comparable keyMayor) {
        Lista lis = new Lista();
        listarRangoAux(this.raiz, lis, keyMenor, keyMayor);
        return lis;
    }

    private void listarRangoAux(NodoAVLDicc nodo, Lista lis, Comparable keyMenor, Comparable keyMayor) {

        if (nodo != null) {
            if (nodo.getIzquierdo() != null && nodo.getClave().compareTo(keyMenor) > 0) {
                listarRangoAux(nodo.getIzquierdo(), lis, keyMenor, keyMayor);
            }
            if (nodo.getClave().compareTo(keyMenor) >= 0 && nodo.getClave().compareTo(keyMayor) <= 0) {
                lis.insertar(nodo.getDato(), lis.longitud() + 1);
            }
            if (nodo.getDerecho() != null && nodo.getClave().compareTo(keyMayor) < 0) {
                listarRangoAux(nodo.getDerecho(), lis, keyMenor, keyMayor);
            }
        }
    }
}