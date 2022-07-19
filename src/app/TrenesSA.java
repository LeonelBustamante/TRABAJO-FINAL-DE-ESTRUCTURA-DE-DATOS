package app;

import tools.*;
import util.TecladoIn;

public class TrenesSA {

    public static void main(String[] args) {
        Archivos.limpiarTXT();
        menuPrincipal();
    }

    private static void menuPrincipal() {
        Grafo mapa = new Grafo();
        DiccionarioAVL estaciones = new DiccionarioAVL();
        DiccionarioHash trenes = new DiccionarioHash(20);
        MapeoAMuchos lineas = new MapeoAMuchos(20);
        int opcion;
        do {
            System.out.println("----------------------------------------------------");
            System.out.println("\t\t\tMENU PRINCIPAL");
            System.out.println("1. Carga inicial de datos");
            System.out.println("2. ABM de Trenes");
            System.out.println("3. ABM de Estaciones");
            System.out.println("4. ABM de Lineas");
            System.out.println("5. ABM de Vias");
            System.out.println("6. Consultas de Trenes");
            System.out.println("7. Consultas de Estaciones");
            System.out.println("8. Consultas de Viajes");
            System.out.println("9. Mostrar todos los datos");
            System.out.println("10. Salir");
            System.out.print("Ingrese una opcion: ");
            opcion = TecladoIn.readLineInt();
            switch (opcion) {
                case 1 -> Archivos.leer(
                        "D:\\Documentos\\Leo\\TRABAJO-FINAL-DE-ESTRUCTURA-DE-DATOS\\cargaDeDatos.txt", mapa, estaciones,
                        trenes, lineas);
                case 2 -> abmTrenes(trenes, lineas);
                case 3 -> abmEstaciones(estaciones, mapa);
                case 4 -> abmLineas(lineas, estaciones);
                case 5 -> abmVias(mapa, estaciones);
                case 6 -> consultasTrenes(trenes, lineas);
                case 7 -> consultasEstaciones(estaciones);
                case 8 -> consultasViajes(mapa, estaciones);
                case 9 -> mostrarDatos(mapa, estaciones, trenes, lineas);
                case 10 -> System.out.println("Saliendo");
                default -> System.out.println("Opcion incorrecta");
            }
        } while (opcion != 10);
    }

    public static int menuABM() {
        System.out.println("----------------------------------------------------");
        System.out.println("1. Agregar");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Volver");
        System.out.print("Ingrese una opcion: ");
        return TecladoIn.readLineInt();
    }

    private static Tren buscarTrenSegunInterno(DiccionarioHash trenes) {
        int interno = 0;
        do {
            System.out.print("Ingrese el interno del tren: ");
            interno = TecladoIn.readLineInt();
            if (!trenes.existeClave(interno)) {
                System.out.println("El tren no existe");
            }
        } while (!trenes.existeClave(interno));
        return (Tren) trenes.obtenerDato(interno);
    }

    private static String pedirLinea(Lista lineasDisponibles) {
        String linea = "";
        boolean seguir;
        do {
            if (!lineasDisponibles.esVacia()) {
                System.out.println(lineasDisponibles);
                System.out.print("Ingrese el nombre de la linea que se asignara o 0 para no asignarle linea: ");
                linea = TecladoIn.readLine();
                linea = linea.toUpperCase();

                if (lineasDisponibles.localizar(linea) == -1) {
                    // Si la linea no existe, se le pide al usuario que la ingrese nuevamente
                    System.out.println("!!!ERROR: No existe la linea " + linea);
                    seguir = true;
                } else if (linea.equals("0")) {
                    // Si el usuario ingresa 0, se asigna la linea "LIBRE" y se sale del ciclo
                    linea = "LIBRE";
                    seguir = false;
                } else {
                    // Si la linea existe, se sale del ciclo
                    seguir = false;
                }
            } else {
                // Si no hay lineas disponibles, se asigna "LIBRE" y se sale del ciclo
                System.out.println("No hay lineas disponibles, se asignara \"LIBRE\"");
                linea = "LIBRE";
                seguir = true;
            }
        } while (!seguir);
        return linea;
    }

    private static int pedirVagonesCarga() {
        int vagonesCarga = 0;
        do {
            System.out.print("Ingrese la cantidad de vagones de carga: ");
            vagonesCarga = TecladoIn.readLineInt();
            if (vagonesCarga < 0) {
                System.out.println("!!!ERROR: La cantidad de vagones de carga debe ser mayor o igual a 0");
            }
        } while (vagonesCarga < 0);
        return vagonesCarga;
    }

    private static int pedirVagonesPasajeros() {
        int vagonesPasajeros = 0;
        do {
            System.out.print("Ingrese la cantidad de vagones de pasajeros: ");
            vagonesPasajeros = TecladoIn.readLineInt();
            if (vagonesPasajeros < 0) {
                System.out.println("!!!ERROR: La cantidad de vagones de pasajeros debe ser mayor o igual a 0");
            }
        } while (vagonesPasajeros < 0);
        return vagonesPasajeros;
    }

    private static String pedirCombustible() {
        String combustible = "";
        boolean seguir = false;
        do {
            System.out.print("Ingrese el tipo de combustible (1-DIESEL, 2-ELECTRICIDAD, 3-NAFTA, 4-GNC): ");
            switch (TecladoIn.readLineInt()) {
                case 1 -> combustible = "DIESEL";
                case 2 -> combustible = "ELECTRICIDAD";
                case 3 -> combustible = "NAFTA";
                case 4 -> combustible = "GNC";
                default -> System.out.println("!!!ERROR: El tipo de combustible ingresado no es valido");
            }
            if (!combustible.equals("")) {
                seguir = true;
            }
        } while (!seguir);
        return combustible;
    }

    private static void abmTrenes(DiccionarioHash trenes, MapeoAMuchos lineas) {
        int opcion;
        do {
            System.out.println("-----ABM TRENES-----");
            opcion = menuABM();
            if (trenes.esVacio() && (opcion == 2 || opcion == 3)) {
                System.out.println("No hay trenes para modificar o eliminar");
                opcion = 0;
            }
            switch (opcion) {
                case 1 -> agregarTren(trenes, lineas);
                case 2 -> modificarTren(buscarTrenSegunInterno(trenes), lineas);
                case 3 -> eliminarTren(trenes);
                case 4 -> System.out.println("Volviendo");
                default -> System.out.println("Opcion incorrecta");
            }
        } while (opcion != 4);
    }

    private static void agregarTren(DiccionarioHash trenes, MapeoAMuchos lineas) {
        Lista lineasDisponibles = lineas.obtenerConjuntoDominio();
        String combustible = pedirCombustible(), linea = pedirLinea(lineasDisponibles);
        int vPasajeros = pedirVagonesPasajeros(), vCarga = pedirVagonesCarga();
        Tren tren = new Tren(combustible, vCarga, vPasajeros, linea);
        trenes.insertar(Tren.ultimoID(), tren);
        Archivos.escribirLog("Se agrego el tren con interno " + tren.getId());
    }

    private static void modificarTren(Tren tren, MapeoAMuchos lineas) {
        int opcion;
        do {
            System.out.println("Tren actualmente:\n " + tren);
            System.out.println("1. Cambiar combustible");
            System.out.println("2. Cambiar cantidad de vagones de pasajeros");
            System.out.println("3. Cambiar cantidad de vagones de carga");
            System.out.println("4. Cambiar linea");
            System.out.println("5. Volver");
            System.out.print("Ingrese una opcion: ");
            opcion = TecladoIn.readLineInt();
            switch (opcion) {
                case 1 -> cambiarCombustible(tren);
                case 2 -> cambiarVagonesPasajeros(tren);
                case 3 -> cambiarVagonesCarga(tren);
                case 4 -> cambiarLinea(tren, lineas);
                case 5 -> System.out.println("Volviendo");
                default -> System.out.println("Opcion incorrecta");
            }
        } while (opcion != 5);
    }

    private static void cambiarCombustible(Tren tren) {
        if (tren != null) {
            String combustible = "";
            boolean seguir = false;
            do {
                System.out.println("Combustible actual: " + tren.getCombustible());
                System.out.print("Ingrese el tipo de combustible (1-DIESEL, 2-ELECTRICIDAD, 3-NAFTA, 4-GNC): ");
                switch (TecladoIn.readLineInt()) {
                    case 1 -> combustible = "DIESEL";
                    case 2 -> combustible = "ELECTRICIDAD";
                    case 3 -> combustible = "NAFTA";
                    case 4 -> combustible = "GNC";
                    default -> System.out.println("!!!ERROR: Ingrese un numero entre 1 y 4");
                }
                if (!combustible.equals("")) {
                    seguir = true;
                }
            } while (!seguir);
            tren.setCombustible(combustible);
            Archivos.escribirLog("Se cambio el combustible del tren con interno " + tren.getId());
        } else {
            Archivos.escribirLog("Tren no encontrado");
        }
    }

    private static void cambiarVagonesPasajeros(Tren tren) {
        if (tren != null) {
            tren.setCantidadVagonesPasajeros(pedirVagonesPasajeros());
            Archivos.escribirLog("Se cambio la cantidad de vagones de pasajeros del tren con interno " + tren.getId());
        } else {
            Archivos.escribirLog("Tren no encontrado");
        }
    }

    private static void cambiarVagonesCarga(Tren tren) {
        if (tren != null) {
            tren.setCantidadVagonesCarga(pedirVagonesCarga());
            Archivos.escribirLog("Se cambio la cantidad de vagones de carga del tren con interno " + tren.getId());
        } else {
            Archivos.escribirLog("Tren no encontrado");
        }
    }

    private static void cambiarLinea(Tren tren, MapeoAMuchos lineas) {
        if (tren != null) {
            Lista listaDeLineasDisponibles = lineas.obtenerConjuntoDominio();
            tren.setLinea(pedirLinea(listaDeLineasDisponibles));
            Archivos.escribirLog("Se cambio la linea del tren con interno " + tren.getId());
        } else {
            Archivos.escribirLog("Tren no encontrado");
        }
    }

    private static void eliminarTren(DiccionarioHash trenes) {
        System.out.print("Ingrese el interno del tren: ");
        int interno = TecladoIn.readLineInt();
        if (trenes.existeClave(interno)) {
            trenes.eliminar(interno);
            Archivos.escribirLog("Se elimino el tren con interno " + interno);
        } else {
            Archivos.escribirLog("Tren no encontrado");
        }
    }

    private static Estacion buscarEstacionPorNombre(DiccionarioAVL estaciones) {
        String nombre = "";
        boolean seguir;
        do {
            System.out.print("Ingrese el nombre de la estacion: ");
            nombre = TecladoIn.readLine();
            seguir = estaciones.existeClave(nombre);
            if (!seguir) {
                System.out.println("!!!ERROR: Estacion no encontrada");
            }
        } while (!seguir);
        return (Estacion) estaciones.obtenerDato(nombre);

    }

    private static int pedirCantidadPlataformas() {
        int cantidadPlataformas = 0;
        boolean seguir = false;
        do {
            System.out.print("Ingrese la cantidad de plataformas: ");
            cantidadPlataformas = TecladoIn.readLineInt();
            if (cantidadPlataformas <= 0) {
                System.out.println("!!!ERROR: Ingrese un numero mayor o igual a 0");
            } else {
                seguir = true;
            }
        } while (!seguir);
        return cantidadPlataformas;
    }

    private static int pedirCantidadVias() {
        int cantidadVias = 0;
        boolean seguir = false;
        do {
            System.out.print("Ingrese la cantidad de vias: ");
            cantidadVias = TecladoIn.readLineInt();
            if (cantidadVias <= 0) {
                System.out.println("!!!ERROR: Ingrese un numero mayor o igual a 0");
            } else {
                seguir = true;
            }
        } while (!seguir);
        return cantidadVias;
    }

    private static String pedirCodigoPostal() {
        String codigoPostal = "";
        boolean seguir = false;
        do {
            System.out.print("Ingrese el codigo postal: ");
            codigoPostal = TecladoIn.readLine();
            if (codigoPostal.length() == 0) {
                System.out.println("!!!ERROR: Ingrese un codigo postal");
            } else {
                seguir = true;
            }
        } while (!seguir);
        return codigoPostal.toUpperCase();
    }

    private static String pedirCiudad() {
        String ciudad = "";
        boolean seguir = false;
        do {
            System.out.print("Ingrese la ciudad: ");
            ciudad = TecladoIn.readLine();
            if (ciudad.length() == 0) {
                System.out.println("!!!ERROR: Ingrese una ciudad");
            } else {
                seguir = true;
            }
        } while (!seguir);
        return ciudad.toUpperCase();
    }

    private static int pedirAltura() {
        int altura = 0;
        boolean seguir = false;
        do {
            System.out.print("Ingrese la altura: ");
            altura = TecladoIn.readLineInt();
            if (altura <= 0) {
                System.out.println("!!!ERROR: Ingrese una altura mayor a 0");
            } else {
                seguir = true;
            }
        } while (!seguir);
        return altura;
    }

    private static String pedirNombreCalle() {
        String calle;
        boolean seguir;
        do {
            System.out.print("Ingrese la calle de la estacion: ");
            calle = TecladoIn.readLine();
            if (!calle.equals("")) {
                seguir = true;
            } else {
                System.out.println("!!!ERROR: No puede ingresar un nombre vacio");
                seguir = false;
            }
        } while (!seguir);
        return calle.toUpperCase();
    }

    private static String pedirNombreEstacion() {
        String nombre;
        boolean seguir;
        do {
            System.out.print("Ingrese el nombre de la estacion: ");
            nombre = TecladoIn.readLine();
            if (!nombre.equals("")) {
                seguir = true;
            } else {
                System.out.println("!!!ERROR: No puede ingresar un nombre vacio");
                seguir = false;
            }
        } while (!seguir);
        return nombre.toUpperCase();
    }

    private static void abmEstaciones(DiccionarioAVL estaciones, Grafo mapa) {
        int opcion;
        do {
            System.out.println("-----ABM ESTACIONES-----");
            opcion = menuABM();
            if (estaciones.esVacio() && (opcion == 2 || opcion == 3)) {
                System.out.println("No se puede agregar o eliminar estaciones, no hay estaciones cargadas");
                opcion = 0;
            }
            switch (opcion) {
                case 1 -> agregarEstacion(estaciones, mapa);
                case 2 -> modificarEstacion(buscarEstacionPorNombre(estaciones));
                case 3 -> eliminarEstacion(estaciones, mapa);
                case 4 -> System.out.println("Volviendo");
                default -> System.out.println("Opcion incorrecta");
            }
        } while (opcion != 4);
    }

    private static void agregarEstacion(DiccionarioAVL estaciones, Grafo mapa) {
        String nombre = pedirNombreEstacion();
        if (!estaciones.existeClave(nombre)) {
            String calle = pedirNombreCalle(), ciudad = pedirCiudad(), codigoPostal = pedirCodigoPostal();
            int altura = pedirAltura(), cantVias = pedirCantidadVias(), cantPlataformas = pedirCantidadPlataformas();
            Estacion estacion = new Estacion(nombre, calle, altura, ciudad, codigoPostal, cantVias, cantPlataformas);
            estaciones.insertar(nombre, estacion);
            mapa.insertarVertice(estacion);
            Archivos.escribirLog("Se agrego la estacion " + nombre);
        } else {
            System.out.println("!!!ERROR: La estacion ya existe");
            Archivos.escribirLog("Estacion ya existe");
        }
    }

    private static void modificarEstacion(Estacion estacion) {
        int opcion;
        do {
            System.out.println("Estación actualmente:\n" + estacion);
            System.out.println("----------Modificar Estacion----------");
            System.out.println("1. Modificar cantidad de vias");
            System.out.println("2. Modificar cantidad de plataformas");
            System.out.println("3. Volver");
            System.out.println("----------------------------");
            System.out.print("Ingrese una opcion: ");
            opcion = TecladoIn.readLineInt();
            switch (opcion) {
                case 1 -> modificarCantidadVias(estacion);
                case 2 -> modificarCantidadPlataformas(estacion);
                case 3 -> System.out.println("Volviendo");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 3);

    }

    private static void modificarCantidadVias(Estacion estacion) {
        if (estacion != null) {
            estacion.setCantidadVias(pedirCantidadVias());
            Archivos.escribirLog("Se modifico la cantidad de vias de la estacion " + estacion.getNOMBRE());
        } else {
            Archivos.escribirLog("No se pudo modificar la cantidad de vias de la estacion");
        }
    }

    private static void modificarCantidadPlataformas(Estacion estacion) {
        if (estacion != null) {
            estacion.setCantidadPlataformas(pedirCantidadPlataformas());
            Archivos.escribirLog("Se modifico la cantidad de vias de la estacion " + estacion.getNOMBRE());
        } else {
            Archivos.escribirLog("No se pudo modificar la cantidad de vias de la estacion");
        }
    }

    private static void eliminarEstacion(DiccionarioAVL estaciones, Grafo mapa) {
        String nombre = pedirNombreEstacion();
        if (estaciones.existeClave(nombre)) {
            estaciones.eliminar(nombre);
            mapa.eliminarVertice(nombre);
            Archivos.escribirLog("Se elimino la estacion " + nombre);
        } else {
            Archivos.escribirLog("Estacion no encontrada");
        }
    }

    /*
     * CONSULTA
     * Tengo el problema con el ABM de Lineas al momento de cargarle mas estaciones,
     * tengo que revisar como localizar una para asignar a esta nuevas estaciones.
     * Me funciona correctamente a la hora de cargarle estaciones, en la creacion de
     * la linea.
     */
    private static void abmLineas(MapeoAMuchos lineas, DiccionarioAVL estaciones) {
        if (!estaciones.esVacio()) {
            int opcion;
            do {
                System.out.println("-----ABM LINEAS-----");
                opcion = menuABM();
                if (lineas.esVacio() && (opcion == 2 || opcion == 3)) {
                    System.out.println("No se puede agregar o eliminar lineas, no hay lineas cargadas");
                    opcion = 0;
                }
                switch (opcion) {
                    case 1 -> agregarLinea(lineas, estaciones);
                    case 2 -> modificarLinea(lineas, estaciones);
                    case 3 -> System.out.println("NO SE PUEDE ELIMINAR LINEAS DEBIDO A LA ESTRUCTURA UTILIZADA");
                    case 4 -> System.out.println("Volviendo");
                    default -> System.out.println("Opcion incorrecta");
                }
            } while (opcion != 4);
        } else {
            System.out.println("No se puede agregar o eliminar lineas, no hay estaciones cargadas");
        }
    }

    private static void agregarLinea(MapeoAMuchos lineas, DiccionarioAVL estaciones) {
        System.out.println("----------Agregar Linea----------");
        String nombre = pedirNombreLinea();
        System.out.println(lineas.obtenerConjuntoDominio().localizar(nombre));
        if (lineas.obtenerConjuntoDominio().localizar(nombre) == -1) {
            Linea linea = new Linea(nombre);
            agregarEstacionesALinea(lineas, linea, estaciones);
        } else {
            System.out.println("!!!ERROR: Linea existente, ingrese a menu modificar");
        }
    }

    private static String pedirNombreLinea() {
        String nombre;
        boolean seguir;
        do {
            System.out.print("Ingrese el nombre de la linea: ");
            nombre = TecladoIn.readLine();
            if (!nombre.equals("")) {
                seguir = true;
            } else {
                System.out.println("!!!ERROR: No puede ingresar un nombre vacio");
                seguir = false;
            }
        } while (!seguir);
        return nombre.toUpperCase();
    }

    private static void agregarEstacionesALinea(MapeoAMuchos lineas, Linea linea, DiccionarioAVL estaciones) {
        Lista estacionesDisponibles = estaciones.listarClaves();
        System.out.println("Estaciones disponibles:\n" + estacionesDisponibles);
        lineas.asociar(linea, buscarEstacionPorNombre(estaciones));
    }

    // Este es el menu para agregar o eliminar estaciones a una linea.
    private static void modificarLinea(MapeoAMuchos lineas, DiccionarioAVL estaciones) {
        int opcion;
        do {
            System.out.println("Lineas actuales:\n" + lineas.obtenerConjuntoDominio());
            System.out.println("----------Modificar Estaciones----------");
            System.out.println("1. Agregar estaciones a la linea");
            System.out.println("2. Eliminar estaciones de la linea");
            System.out.println("3. Volver");
            System.out.println("----------------------------");
            System.out.print("Ingrese una opcion: ");
            opcion = TecladoIn.readLineInt();
            switch (opcion) {
                case 1 -> agregarEstacionALinea(lineas, estaciones);
                case 2 -> eliminarEstacionALinea(lineas, estaciones);
                case 3 -> System.out.println("Volviendo");
                default -> System.out.println("Opcion incorrecta");
            }
        } while (opcion != 3);
    }

    private static void agregarEstacionALinea(MapeoAMuchos lineas, DiccionarioAVL estaciones) {
        String nombre = pedirNombreLinea();
        if (lineas.obtenerConjuntoDominio().localizar(nombre) > 0) {
            System.out.println("entre a agregar estaciones");
            lineas.asociar(nombre, buscarEstacionPorNombre(estaciones));
            Archivos.escribirLog("Se agrego una estacion a la linea " + nombre);
        } else {
            Archivos.escribirLog("!!!ERROR: Linea no existente");
            System.out.println("!!!ERROR: Linea no encontrada");
        }
    }

    private static void eliminarEstacionALinea(MapeoAMuchos lineas, DiccionarioAVL estaciones) {
        System.out.println("----------Eliminar estacion de una Linea----------");
        String nombre = pedirNombreLinea();
        if (lineas.obtenerConjuntoDominio().localizar(nombre) > 0) {
            if (lineas.obtenerValores(nombre).longitud() > 1) {
                Estacion estacion = buscarEstacionPorNombre(estaciones);
                lineas.desasociar(nombre, estacion);
                Archivos.escribirLog("Se elimino la estacion" + estacion.getNOMBRE() + "de linea " + nombre);
            } else {
                System.out.println(
                        "!!!ERROR: No se puede eliminar la estacion, la linea debe tener al menos 1 estacion");
            }
        } else {
            Archivos.escribirLog("Linea no encontrada");
        }
    }

    private static void abmVias(Grafo mapa, DiccionarioAVL estaciones) {
        if (!estaciones.esVacio()) {
            int opcion;
            do {
                System.out.println("-----ABM VIAS-----");
                opcion = menuABM();
                if (estaciones.esVacio() && (opcion == 2 || opcion == 3)) {
                    System.out.println("!!!ERROR: No hay estaciones cargadas");
                    opcion = 0;
                }
                switch (opcion) {
                    case 1 -> agregarVia(mapa, estaciones);
                    case 2 -> System.out.println("NO SE PERMITE MODIFICAR VIAS");
                    case 3 -> eliminarVia(mapa, estaciones);
                    case 4 -> System.out.println("Volviendo");
                    default -> System.out.println("Opcion incorrecta");
                }
            } while (opcion != 4);
        } else {
            System.out.println("!!!ERROR: No hay estaciones cargadas");
        }
    }

    private static void eliminarVia(Grafo mapa, DiccionarioAVL estaciones) {
        if (!estaciones.esVacio()) {
            System.out.println("Estaciones disponibles:\n" + estaciones.listarClaves());
            Estacion estacionA = buscarEstacionPorNombre(estaciones);
            Estacion estacionB = buscarEstacionPorNombre(estaciones);
            if (estacionA.getNOMBRE().equals(estacionB.getNOMBRE())) {
                System.out.println("!!!ERROR: No existen vias entre la misma estacion");
            } else {
                if (mapa.existeArco(estacionA, estacionB)) {
                    if (mapa.eliminarArco(estacionA, estacionB)) {
                        System.out.println("Via eliminada");
                        Archivos.escribirLog(
                                "Se elimino la via entre " + estacionA.getNOMBRE() + " y " + estacionB.getNOMBRE());
                    } else {
                        System.out.println("!!!ERROR: No se pudo eliminar la via");
                    }
                } else {
                    System.out.println("!!!ERROR: No existe via entre las estaciones");
                }
            }
        } else {
            System.out.println("!!!ERROR: No hay estaciones cargadas");
            Archivos.escribirLog("!!!ERROR: No hay estaciones cargadas");
        }
    }

    private static void agregarVia(Grafo mapa, DiccionarioAVL estaciones) {
        if (!estaciones.esVacio()) {
            Estacion estacionA = buscarEstacionPorNombre(estaciones);
            Estacion estacionB = buscarEstacionPorNombre(estaciones);
            int kilometros;
            if (estacionA.getNOMBRE().equals(estacionB.getNOMBRE())) {
                System.out.println("!!!ERROR: No se puede agregar una via a una misma estacion");
            } else {
                boolean seguir;
                do {
                    System.out.print("Ingrese los kilometros: ");
                    kilometros = TecladoIn.readLineInt();
                    if (kilometros < 1) {
                        System.out.println("!!!ERROR: No puede ingresar un kilometro menor a 1");
                        seguir = false;
                    } else {
                        seguir = true;
                    }
                } while (!seguir);
                mapa.insertarArco(estacionA, estacionB, kilometros);
                Archivos.escribirLog(
                        "Se agrego una via entre " + estacionA.getNOMBRE() + " y " + estacionB.getNOMBRE());
            }
        } else {
            System.out.println("!!!ERROR: No hay estaciones cargadas");
            Archivos.escribirLog("!!!ERROR: No hay estaciones cargadas");
        }
    }

    private static void consultasTrenes(DiccionarioHash trenes, MapeoAMuchos lineas) {
        if (!trenes.esVacio()) {
            int opcion;
            do {
                System.out.println("----------Consultas de Trenes----------");
                System.out.println("1. Consultar informacion de un tren");
                System.out.println("2. Consultar linea, y estaciones de un tren");
                System.out.println("3. Volver");
                System.out.println("----------------------------");
                System.out.print("Ingrese una opcion: ");
                opcion = TecladoIn.readLineInt();
                switch (opcion) {
                    case 1 -> System.out.println(buscarTrenSegunInterno(trenes));
                    case 2 -> consutlarRecorridoTren(trenes, lineas);
                    case 3 -> System.out.println("Volviendo");
                    default -> System.out.println("Opcion incorrecta");
                }
            } while (opcion != 3);
        } else {
            System.out.println("!!!ERROR: No hay trenes cargados");
        }
    }

    private static void consutlarRecorridoTren(DiccionarioHash trenes, MapeoAMuchos lineas) {
        Tren tren = buscarTrenSegunInterno(trenes);
        System.out.println("El tren " + tren.getId() + " esta en la linea " + tren.getLinea());
        if (!tren.getLinea().equals("LIBRE")) {
            System.out.println("Estaciones de la linea: " + tren.getLinea());
            System.out.println("El tren visita las estaciones:\n" + lineas.obtenerValores(tren.getLinea()));
        }
    }

    private static void consultasEstaciones(DiccionarioAVL estaciones) {
        if (!estaciones.esVacio()) {
            int opcion;
            do {
                System.out.println("----------Consultas de Estaciones----------");
                System.out.println("1. Consultar informacion de una estacion");
                System.out.println("2. Consultar destinos de una estacion");
                System.out.println("3. Volver");
                System.out.println("----------------------------");
                System.out.print("Ingrese una opcion: ");
                opcion = TecladoIn.readLineInt();
                switch (opcion) {
                    case 1 -> System.out.println(estaciones.obtenerDato(pedirNombreEstacion()));
                    case 2 -> {
                        String estacion = pedirNombreEstacion();
                        System.out.println(estaciones.listarRango(estacion, estacion + "zzz"));
                    }
                    case 3 -> System.out.println("Volviendo");
                    default -> System.out.println("Opcion incorrecta");
                }
            } while (opcion != 3);
        } else {
            System.out.println("!!!ERROR: No hay estaciones cargadas");
        }
    }

    private static void consultasViajes(Grafo mapa, DiccionarioAVL estaciones) {
        if (!estaciones.esVacio()) {
            int opcion;
            do {
                System.out.println("----------Consultas de Viajes----------");
                System.out.println("1. Consultar recorrido que pase por menos estaciones");
                System.out.println("2. Consultar recorrido con menos kilometros");
                System.out.println("3. Volver");
                System.out.println("----------------------------");
                System.out.print("Ingrese una opcion: ");
                opcion = TecladoIn.readLineInt();
                switch (opcion) {
                    case 1 -> recorridoMasCorto(mapa,
                            buscarEstacionPorNombre(estaciones),
                            buscarEstacionPorNombre(estaciones));
                    case 2 -> recorridoMenosKilometros(mapa, buscarEstacionPorNombre(estaciones),
                            buscarEstacionPorNombre(estaciones));
                    case 3 -> System.out.println("Volviendo");
                    default -> System.out.println("Opcion incorrecta");
                }
            } while (opcion != 3);
        } else {
            System.out.println("!!!ERROR: No hay estaciones cargadas");
        }
    }

    private static void recorridoMenosKilometros(Grafo mapa, Estacion estacion, Estacion estacion2) {
        if (!estacion.equals(estacion2)) {
            System.out.println("Recorrido:\n" + mapa.caminoMasCorto(estacion, estacion2));
        } else {
            System.out.println("!!!ERROR: No existe un camino entre las estaciones ingresadas o son iguales");
        }
    }

    private static void recorridoMasCorto(Grafo mapa, Estacion estacionA, Estacion estacionB) {
        if (!estacionA.equals(estacionB)) {
            System.out.println("Recorrido:\n" + mapa.minimoPesoParaPasar(estacionA, estacionB));
        } else {
            System.out.println("!!!ERROR: No existe un camino entre las estaciones ingresadas o son iguales");
        }
    }

    private static void mostrarDatos(Grafo mapa, DiccionarioAVL estaciones, DiccionarioHash trenes,
            MapeoAMuchos lineas) {
        int opcion;
        do {
            System.out.println("----------Mostrar Todos los Datos----------");
            System.out.println("1. Mostrar Mapa");
            System.out.println("2. Mostrar Todos los Trenes");
            System.out.println("3. Mostrar Todas las Estaciones");
            System.out.println("4. Mostrar Todas las Lineas");
            System.out.println("5. Mostrar Todas las Vias");
            System.out.println("6. Volver");
            System.out.println("----------------------------");
            System.out.print("Ingrese una opcion: ");
            opcion = TecladoIn.readLineInt();
            switch (opcion) {
                case 1 -> System.out.println(mapa);
                case 2 -> System.out.println(trenes);
                case 3 -> System.out.println(estaciones);
                case 4 -> System.out.println(lineas);
                case 5 -> System.out.println(mapa);
                case 6 -> System.out.println("Volviendo");
                default -> System.out.println("Opcion incorrecta");
            }
        } while (opcion != 6);
    }
}
