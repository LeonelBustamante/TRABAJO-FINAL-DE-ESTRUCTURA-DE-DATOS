package util;

import java.util.Random;

class GenerarTexto {
        /*
         * Clase generadora de texto para la facilidad de generar archivos de prueba
         * para TrenesSA.
         */
        public static void main(String[] args) {
                String estaciones[] = {
                                "NEUQUEN", "CIPOLLETTI", "ROCA", "ALLEN", "CENTENARIO", "SENILLOSA",
                                "VILLA EL CHOCON", "VILLA REGINA", "CHINA MUERTA", "VILLA MASCARDI",
                                "VILLA LA ANGOSTURA", "SAN MARTIN DE LOS ANDES", "VILLA MANZANO", "JUNIN DE LOS ANDES",
                                "ALUMINE", "ZAPALA", "CUTRAL CO", "PLAZA HUINCUL", "PRIMEROS PINOS", "VILLA PEHUENIA",
                                "PICUN LEUFU", "PIEDRA DEL AGUILA", "VILLA TRAFUL" };
                int i;
                for (i = 0; i < estaciones.length; i++) {
                        // ALMACENAR EN TDA Diccionario
                        // Estacion,Nombre,Direccion,CP,Ciudad,cantidadVias,cantidadPlataformas
                        System.out.println("E;" +
                                        estaciones[i] + ";" +
                                        "DIRECCION-" + i + ";" +
                                        i + ";" +
                                        "CP-" + i + ";" +
                                        estaciones[i] + ";" +
                                        new Random().nextInt(5) + ";" +
                                        new Random().nextInt(5) + ";");
                }
                System.out.println("");
                String destinosLinea = "";
                for (i = 0; i < 20; i++) {
                        // Linea,nombre,destinos
                        for (int j = 0; j < 5; j++) {
                                // 5 destinos cada linea
                                destinosLinea += estaciones[new Random().nextInt(estaciones.length)] + ";";
                        }
                        System.out.println("L;" +
                                        "LINEA-" + i + ";"
                                        + destinosLinea);
                        destinosLinea = "";
                }
                System.out.println("");
                for (i = 0; i < 20; i++) {
                        // Rieles,Origen,Destino,kms
                        System.out.println("R;" +
                                        "ESTACION" + i + ";" +
                                        "ESTACION" + i + ";" +
                                        Math.round(new Random().nextFloat(10, 500)) + ";");
                }
                System.out.println("");
                String combustibles[] = { "DIESEL", "ELECTRICIDAD", "NAFTA", "GNC" };
                for (i = 0; i < 40; i++) {
                        // Tren;Interno,Combustible,vagonesPasajeros,vagonesCarga,linea
                        System.out.println("T;"
                                        + combustibles[new Random().nextInt(combustibles.length)]
                                        + ";"
                                        + Math.round(new Random().nextFloat(0, 5)) + ";"
                                        + Math.round(new Random().nextFloat(0, 5)) + ";"
                                        + "NO-ASIGNADO" + ";");
                }

        }

}
