package tools;

import app.Estacion;

public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object vertice) {
        // INSERTA VERTICE EN EL GRAFO AL PRINCIPIO Y
        // RETORNA TRUE O FALSE DE ACUERDO AL EXITO
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(vertice);
        if (aux == null) {
            this.inicio = new NodoVert(vertice, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object elem) {
        // BUSCA EL VERTICE EN EL GRAFO Y RETORNA EL NODO
        // O RETORNA NULL SI NO EXISTE EL VERTICE
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(elem)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean eliminarVertice(Object vertice) {
        // ELIMINA VERTICE Y TODOS SUS ARCOS

        boolean exito = false;
        if (!this.esVacio()) {
            if (this.inicio.getElem().equals(vertice)) {
                eliminarArcos(this.inicio);
                this.inicio = this.inicio.getSigVertice();
                exito = true;

            } else {
                NodoVert aux = this.ubicarVerticeAnterior(vertice);
                if (aux != null) {
                    eliminarArcos(aux.getSigVertice());
                    aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    exito = true;
                }
            }
        }
        return exito;
    }

    private NodoVert ubicarVerticeAnterior(Object elem) {
        NodoVert aux = this.inicio;
        while (aux.getSigVertice() != null && !aux.getSigVertice().getElem().equals(elem)) {
            aux = aux.getSigVertice();
        }
        if (aux.getSigVertice() == null) {
            aux = null;
        }
        return aux;
    }

    private void eliminarArcos(NodoVert nodo) {
        NodoAdy adyacente = nodo.getPrimerAdy();
        while (adyacente != null) {

            eliminar(adyacente.getVertice(), nodo);
            adyacente = adyacente.getSigAdyacente();
        }
    }

    public boolean existeVertice(Object vertice) {
        // RETORNA TRUE O FALSE DE ACUERDO AL EXITO EN LA BUSQUEDA DEL VERTICE
        return (ubicarVertice(vertice) != null);
    }

    public boolean insertarArco(Object verticeO, Object verticeD, int etiqueta) {
        boolean exito = false;
        NodoVert origen = ubicarVertice(verticeO);
        NodoVert destino = ubicarVertice(verticeD);
        if (origen != null && destino != null) {
            origen.setPrimerAdy(new NodoAdy(destino, origen.getPrimerAdy(), etiqueta));
            destino.setPrimerAdy(new NodoAdy(origen, destino.getPrimerAdy(), etiqueta));
            exito = true;
        }
        return exito;
    }

    public boolean eliminarArco(Object verticeO, Object verticeD) {
        boolean exito = false;
        NodoVert origen = ubicarVertice(verticeO);
        NodoVert destino = ubicarVertice(verticeD);
        if (origen != null && destino != null) {
            exito = eliminar(origen, destino);
            eliminar(destino, origen);
        }
        return exito;
    }

    private NodoAdy ubicarArco(Object origen, Object destino) {
        NodoAdy aux = this.ubicarVertice(origen).getPrimerAdy();
        while (aux != null && !aux.getVertice().getElem().equals(destino)) {
            aux = aux.getSigAdyacente();
        }
        return aux;
    }

    private boolean eliminar(NodoVert nodoO, NodoVert nodoD) {
        boolean exito = false;
        NodoAdy aux = nodoO.getPrimerAdy();
        if (aux != null) {
            if (aux.getVertice().equals(nodoD)) {
                nodoO.setPrimerAdy(nodoO.getPrimerAdy().getSigAdyacente());
            } else {
                while (!exito && aux != null) {
                    if (aux.getSigAdyacente() != null && aux.getSigAdyacente().getVertice().equals(nodoD)) {
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                        exito = true;
                    }
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean existeArco(Object verticeO, Object verticeD) {
        return (ubicarArco(verticeO, verticeD) != null);
    }

    public boolean esVacio() {
        return (this.inicio == null);
    }

    public Grafo clone() {
        Grafo clon = new Grafo();

        if (!this.esVacio()) {
            clon.inicio = new NodoVert(this.inicio.getElem(), null);
            NodoVert aux = this.inicio.getSigVertice();
            NodoVert aux2 = clon.inicio;

            while (aux != null) {
                aux2.setSigVertice(new NodoVert(aux.getElem(), null));
                aux2 = aux2.getSigVertice();
                aux = aux.getSigVertice();
            }

            aux = this.inicio;
            aux2 = clon.inicio;
            while (aux != null) {
                clonarNodosAdyacentes(aux, aux2, inicio);
                aux = aux.getSigVertice();
                aux2 = aux2.getSigVertice();
            }
        }
        return clon;
    }

    private void clonarNodosAdyacentes(NodoVert original, NodoVert copia, NodoVert inicio) {
        NodoAdy aux = original.getPrimerAdy();
        if (aux != null) {
            copia.setPrimerAdy(
                    new NodoAdy(ubicarVertice(aux.getVertice().getElem(), inicio), null, aux.getEtiqueta()));
            NodoAdy aux3 = copia.getPrimerAdy();
            aux = aux.getSigAdyacente();
            while (aux != null) {
                aux3.setSigAdyacente(
                        new NodoAdy(ubicarVertice(aux.getVertice().getElem(), inicio), null, aux.getEtiqueta()));
                aux = aux.getSigAdyacente();
                aux3 = aux3.getSigAdyacente();
            }
        }
    }

    private NodoVert ubicarVertice(Object elem, NodoVert nodo) {
        NodoVert aux = nodo;
        while (aux != null && !aux.getElem().equals(elem)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public String toString() {
        String texto = "SIN INFORMACION";
        if (!this.esVacio()) {
            texto = "";
            NodoVert vertice = this.inicio;
            while (vertice != null) {
                texto += "\nVERTICE " + vertice.getElem() + ": [";
                NodoAdy adyacente = vertice.getPrimerAdy();
                while (adyacente != null) {
                    texto += adyacente.getVertice().getElem() + " " + "(" + adyacente.getEtiqueta() + ")";
                    adyacente = adyacente.getSigAdyacente();
                    if (adyacente != null) {
                        texto += " // ";
                    }
                }
                texto += "]";
                vertice = vertice.getSigVertice();
            }
        }
        return texto;
    }

    public boolean existeCamino(Object verticeO, Object verticeD) {
        boolean exito = false;
        // VERIFICA SI AMBOS VERTICES EXISTEN
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;

        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(verticeO)) {
                auxO = aux;
            }
            if (aux.getElem().equals(verticeD)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxO != null && auxD != null) {
            // SI AMBOS VERTICES EXISTE BUSCA SI EXISTE CAMINO ENTRE AMBOS
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, verticeD, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoVert nodo, Object dest, Lista lis) {
        boolean exito = false;
        if (nodo != null) {
            // SI VERTICE nodo ES EL DESTINO: HAY CAMINO!
            if (nodo.getElem().equals(dest)) {
                exito = true;
            } else {
                // SI NO ES EL DESTINO VERIFICA SI HAY CAMINO ENTRE nodo Y destino
                lis.insertar(nodo.getElem(), lis.longitud() + 1);
                NodoAdy ady = nodo.getPrimerAdy();
                while (!exito && ady != null) {
                    if (lis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, lis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // DEFINE UN VERTICE DONDE COMENZAR A CORRER
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // SI EL VERTICE NO FUE VISITADO AUN, AVANZA EN PROFUNDIDAD
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista lis) {
        if (nodo != null) {
            // MARCA AL VERTICE nodo COMO VISITADO
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null) {
                // VISITA EN PROFUNDIDAD LOS ADYACENTES DE nodo AUN NO VISITADOS
                if (lis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), lis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        // DEFINE UN VERTICE DONDE COMENZAR A CORRER
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // SI EL VERTICE NO FUE VISITADO AUN, AVANZA EN ANCHURA
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVert nodo, Lista visitados) {
        if (nodo != null) {
            Cola q = new Cola();
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            q.poner(nodo);
            while (!q.esVacia()) {
                NodoVert u = (NodoVert) q.obtenerFrente();
                q.sacar();
                NodoAdy aux = u.getPrimerAdy();
                while (aux != null) {
                    if (visitados.localizar(aux.getVertice().getElem()) < 0) {
                        visitados.insertar(aux.getVertice().getElem(), visitados.longitud() + 1);
                        q.poner(aux.getVertice());
                    }
                    aux = aux.getSigAdyacente();
                }
            }
        }
    }

    public Lista caminoMasLargo(Object verticeO, Object verticeD) {
        // CAMINO DONDE PASE POR LA MAYOR CANTIDAD DE NODOS SIN REPETIR

        Lista actual = new Lista();
        Lista res = new Lista();
        Lista visitados = new Lista();
        if (!this.esVacio()) {
            NodoVert origen = this.ubicarVertice(verticeO);
            NodoVert destino = this.ubicarVertice(verticeD);
            if (origen != null && destino != null) {
                if (origen.getElem().equals(destino.getElem())) {
                    res = caminoMasLargoAux(origen, destino.getPrimerAdy().getVertice(), actual, res, visitados);

                    res.insertar(origen.getElem(), res.longitud() + 1);
                } else {
                    res = caminoMasLargoAux(origen, destino, actual, res, visitados);
                }

            }

        }

        return res;
    }

    private Lista caminoMasLargoAux(NodoVert origen, NodoVert destino, Lista actual, Lista res, Lista visitados) {
        if (origen != null) {
            if (origen.getElem().equals(destino.getElem())) {
                actual.insertar(origen.getElem(), actual.longitud() + 1);
                visitados.insertar(origen.getElem(), visitados.longitud() + 1);
                if (res.esVacia()) {
                    res = actual.clone();
                } else {
                    if (actual.longitud() > res.longitud()) {
                        res = actual.clone();
                    }
                }

                actual.eliminar(actual.longitud());

                visitados.eliminar(visitados.longitud());

            } else {
                actual.insertar(origen.getElem(), actual.longitud() + 1);
                visitados.insertar(origen.getElem(), visitados.longitud() + 1);
                NodoAdy ady = origen.getPrimerAdy();

                while (ady != null) {
                    // VISITA EN PROFUNDIDAD LOS ADYACENTES DE nodo AUN NO VISITADOS
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        res = caminoMasLargoAux(ady.getVertice(), destino, actual, res, visitados);
                    }

                    ady = ady.getSigAdyacente();
                }

                actual.eliminar(actual.longitud());

                visitados.eliminar(visitados.longitud());

            }
        }
        return res;
    }

    public Lista nodosAdyacentes(Object vertice) {
        // METODO QUE DEVUELVE UNA LISTA DE LOS NODOS ADYACENTES A UN VERTICE DADO POR
        // PARAMETRO

        Lista lis = new Lista();
        NodoVert vert = this.ubicarVertice(vertice);
        NodoAdy ady = vert.getPrimerAdy();
        while (ady != null) {
            lis.insertar(ady.getEtiqueta(), lis.longitud() + 1);
            lis.insertar(ady.getVertice().getElem(), lis.longitud() + 1);
            ady = ady.getSigAdyacente();
        }
        return lis;
    }

    public boolean sonAdyacentes(Object origen, Object destino) {
        // VERIFICA SI DOS VERTICES SON ADYACENTES

        boolean exito = false;
        NodoVert vert = this.ubicarVertice(origen);
        NodoAdy ady = vert.getPrimerAdy();

        while (!exito && ady != null) {
            if (ady.getVertice().equals(destino)) {
                exito = true;
            }
            ady = ady.getSigAdyacente();
        }
        return exito;
    }

    public boolean puedePasar(Object origen, Object destino, int k) {
        boolean exito = false;
        NodoVert vert = this.ubicarVertice(origen);
        NodoVert dest = this.ubicarVertice(destino);
        NodoAdy ady = vert.getPrimerAdy();

        while (!exito && ady != null) {
            if (ady.getVertice().equals(dest)) {
                if (k >= ady.getEtiqueta()) {
                    exito = true;
                }
            }
            ady = ady.getSigAdyacente();
        }
        return exito;
    }

    public boolean esPosibleLlegar(Object verticeO, Object verticeD, int k) {
        // VERIFICA SI ES POSIBLE LLEGAR DE UN VERTICE A OTRO CON UN MAXIMO VALOR DEL
        // PESO DE LAS ETIQUETAS

        boolean exito = false;

        Lista visitados = new Lista();
        if (!this.esVacio()) {
            NodoVert origen = this.ubicarVertice(verticeO);
            NodoVert destino = this.ubicarVertice(verticeD);
            if (origen != null && destino != null) {
                if (origen.getElem().equals(destino.getElem())) {
                    exito = true;
                } else {
                    exito = esPosibleLlegarAux(origen, destino, k, visitados);
                }

            }

        }

        return exito;
    }

    private boolean esPosibleLlegarAux(NodoVert origen, NodoVert destino, int k, Lista visitados) {
        int suma = 0;
        boolean exito = false;
        if (origen != null) {

            if (origen.getElem().equals(destino.getElem()) && k >= 0) {
                exito = true;

            } else {

                visitados.insertar(origen.getElem(), visitados.longitud() + 1);
                NodoAdy ady = origen.getPrimerAdy();

                while (!exito && ady != null) {
                    // VISITA EN PROFUNDIDAD LOS ADYACENTES DE nodo AUN NO VISITADOS
                    suma = ady.getEtiqueta();
                    if (visitados.localizar(ady.getVertice().getElem()) < 0) {
                        if (k - suma >= 0) {
                            exito = esPosibleLlegarAux(ady.getVertice(), destino, k - suma, visitados);
                        }
                    }

                    ady = ady.getSigAdyacente();

                }

                visitados.eliminar(visitados.longitud());

            }
        }
        return exito;
    }

    public Lista sinPasarPor(Object verticeO, Object verticeD, Object verticeEvitado, int k) {
        // VERIFICA SI ES POSIBLE LLEGAR DE UN VERTICE A OTRO, SIN PASAR POR UN TERCER
        // VERTICE
        // CON UN MAXIMO VALOR DEL PESO DE LAS ETIQUETAS

        boolean exito = false;
        Lista resultante = new Lista();
        Lista visitados = new Lista();
        if (!this.esVacio()) {
            NodoVert origen = this.ubicarVertice(verticeO);
            NodoVert destino = this.ubicarVertice(verticeD);
            NodoVert evitado = this.ubicarVertice(verticeEvitado);
            if (origen != null && destino != null) {
                if (origen != evitado && destino != evitado) {
                    if (origen.getElem().equals(destino.getElem())) {
                        resultante.insertar(origen, resultante.longitud() + 1);
                    } else {

                        resultante = sinPasarPorAux(origen, destino, evitado, k, visitados, resultante);
                    }
                }
            }

        }

        return resultante;
    }

    private Lista sinPasarPorAux(NodoVert origen, NodoVert destino, NodoVert evitado, int k, Lista visitados,
            Lista resultante) {
        int suma = 0;
        boolean exito = false;
        if (origen != null) {
            visitados.insertar(origen.getElem(), visitados.longitud() + 1);
            if (origen.getElem().equals(destino.getElem()) && k >= 0) {

                resultante.insertar(visitados.clone(), resultante.longitud() + 1);

            } else {

                NodoAdy ady = origen.getPrimerAdy();

                while (!exito && ady != null) {
                    // VISITA EN PROFUNDIDAD LOS ADYACENTES DE nodo AUN NO VISITADOS

                    suma = ady.getEtiqueta();
                    if (visitados.localizar(ady.getVertice().getElem()) < 0
                            && !ady.getVertice().getElem().equals(evitado.getElem())) {
                        if (k - suma >= 0) {
                            resultante = sinPasarPorAux(ady.getVertice(), destino, evitado, k - suma, visitados,
                                    resultante);
                        }
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud());
        }
        return resultante;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista resultado = new Lista();
        NodoVert verticeOrigen = ubicarVertice(origen);
        if (verticeOrigen != null) {
            // Si el vertice origen existe se busca el vertice destino
            resultado = encuentraCaminoMasCorto(verticeOrigen, new Lista(), resultado, destino);
        }
        return resultado;
    }

    private Lista encuentraCaminoMasCorto(NodoVert actual, Lista caminoActual, Lista caminoMinimo, Object destino) {
        NodoAdy adyActual;
        if (actual != null) {
            caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);
            if (actual.getElem().equals(destino)) {
                if (caminoMinimo.longitud() == 0 || caminoActual.longitud() < caminoMinimo.longitud()) {
                    caminoMinimo = caminoActual.clone();
                }
            } else {
                adyActual = actual.getPrimerAdy();
                while (adyActual != null) {
                    if (caminoActual.localizar(adyActual.getVertice().getElem()) < 0) {
                        caminoMinimo = encuentraCaminoMasCorto(adyActual.getVertice(), caminoActual, caminoMinimo,
                                destino);
                        caminoActual.eliminar(caminoActual.longitud());
                    }
                    adyActual = adyActual.getSigAdyacente();
                }
            }
        }
        return caminoMinimo;
    }

    public Lista minimoPesoParaPasar(Object origen, Object destino) {
        Lista caminoMenosPeso = new Lista();
        NodoVert auxO = ubicarVertice(origen);
        NodoVert auxD = ubicarVertice(destino);
        if (auxO != null && auxD != null) {
            Lista caminoActual = new Lista();
            caminoMenosPeso = minimoPesoParaPasarAux(auxO, auxD, 0, caminoActual, caminoMenosPeso);
        }
        return caminoMenosPeso;
    }

    private Lista minimoPesoParaPasarAux(NodoVert actual, NodoVert dest, int pesoActual, Lista caminoActual,
            Lista caminoMenosPeso) {
        if (actual != null) {
            caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);

            if (actual.equals(dest)) {
                caminoActual.insertar(pesoActual, caminoActual.longitud() + 1);
                if (caminoMenosPeso.esVacia() ||
                        (int) caminoActual.recuperar(caminoActual.longitud()) < (int) caminoMenosPeso
                                .recuperar(caminoMenosPeso.longitud())) {
                    caminoMenosPeso = caminoActual.clone();
                }
                caminoActual.eliminar(caminoActual.longitud());
            } else {
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null) {
                    if (caminoActual.localizar(ady.getVertice().getElem()) < 0) {
                        pesoActual += ady.getEtiqueta();
                        caminoMenosPeso = minimoPesoParaPasarAux(ady.getVertice(), dest, pesoActual, caminoActual,
                                caminoMenosPeso);
                        caminoActual.eliminar(caminoActual.longitud());
                        pesoActual -= ady.getEtiqueta();
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return caminoMenosPeso;
    }
}
