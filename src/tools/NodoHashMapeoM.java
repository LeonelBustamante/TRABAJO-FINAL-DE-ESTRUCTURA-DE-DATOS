package tools;

class NodoHashMapeoM {
    private Object dominio;
    private Lista rango;
    private NodoHashMapeoM enlace;

    public NodoHashMapeoM(Object dom, Object elem) {
        this.dominio = dom;
        this.rango = new Lista();
        agregarElemento(elem);
        this.enlace = null;
    }

    public void agregarElemento(Object elem) {
        this.rango.insertar(elem, this.rango.longitud() + 1);
    }

    public Object getDominio() {
        return dominio;
    }

    public void setDominio(Object dominio) {
        this.dominio = dominio;
    }

    public Lista getRango() {
        return rango;
    }

    public void setRango(Lista rango) {
        this.rango = rango;
    }

    public NodoHashMapeoM getEnlace() {
        return enlace;
    }

    public void setEnlace(NodoHashMapeoM enlace) {
        this.enlace = enlace;
    }

}
