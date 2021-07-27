package app.banco.cliente.cli;

import app.banco.cliente.Cliente;
import app.banco.protocolo.PaqueteLector;

import java.util.List;
import java.util.Scanner;

public class AplicacionCLI {

    public static void main(String[] args) throws Exception {

        Cliente cliente = new Cliente();
        int opcion = 0;

        do {

            System.out.println("=========================================");
            System.out.println(" BancoApp");
            System.out.println(" 1. Abrir cuenta de ahorros.");
            System.out.println(" 2. Crear un bolsillo.");
            System.out.println(" 3. Cancelar un bolsillo.");
            System.out.println(" 4. Cancelar cuenta de ahorros.");
            System.out.println(" 5. Depositar dinero en una cuenta de ahorros.");
            System.out.println(" 6. Retirar dinero de una cuenta de ahorros.");
            System.out.println(" 7. Trasladar dinero de una cuenta de ahorros a su bolsillo.");
            System.out.println(" 8. Consultar saldo de cuenta o bolsillo.");
            System.out.println(" 9. Cargar archivo de transacciones.");
            System.out.println(" 10. Salir de la aplicación.");
            System.out.println("=========================================");

            opcion = leerEntero("");

            switch (opcion) {

                case 1: {
                    String nombre = leerCadena("¿Cuál es el nombre de la cuenta?");
                    PaqueteLector resultado = cliente.abrirCuenta(nombre);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 2: {
                    int codigo = leerEntero("¿# de Cuenta de Ahorros?");
                    PaqueteLector resultado = cliente.crearBolsillo(codigo);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 3: {
                    String cadena = leerCadena("¿# del bosillo?");
                    PaqueteLector resultado = cliente.cancelarBolsillo(cadena);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 4: {
                    int codigo = leerEntero("¿# de Cuenta de Ahorros?");
                    PaqueteLector resultado = cliente.cancelarCuenta(codigo);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 5: {
                    int cuentaAhorros = leerEntero("¿# de Cuenta de Ahorros?");
                    int valor = leerEntero("¿Cuánto dinero quiere depositar?");
                    PaqueteLector resultado = cliente.depositarSaldo(cuentaAhorros, valor);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 6: {
                    int cuentaAhorros = leerEntero("¿# de Cuenta de Ahorros?");
                    int valor = leerEntero("¿Cuánto dinero quiere retirar?");
                    PaqueteLector resultado = cliente.retirarSaldo(cuentaAhorros, valor);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 7: {
                    int cuentaAhorros = leerEntero("¿# de Cuenta de Ahorros?");
                    int valor = leerEntero("¿Cuánto dinero quiere trasladar?");
                    PaqueteLector resultado = cliente.trasladarSaldo(cuentaAhorros, valor);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 8: {
                    String codigo = leerCadena("¿# de Cuenta o Bolsillo?");
                    PaqueteLector resultado = cliente.consultarSaldo(codigo);
                    System.out.println(resultado.leerCadena().replace("OK:", "").replace("ERROR:", ""));
                    break;
                }

                case 9: {
                    String archivoPath = leerCadena("¿Archivo?");
                    PaqueteLector resultado = cliente.cargarArchivo(archivoPath);
                    String mensaje = resultado.leerCadena();
                    if (mensaje.equalsIgnoreCase("OK")) {
                        List<String> log = resultado.leerLista();
                        for (String linea: log) {
                            System.out.println(linea);
                        }
                    } else {
                        System.out.println(mensaje.replace("OK:", "").replace("ERROR:", ""));
                    }
                    break;
                }

                case 10: {
                    System.out.println("¡Gracias por utilizar la aplicación!");
                    break;
                }

                default: {
                    System.out.println("No se encuentra la opción buscada.");
                }

            }

            if (opcion != 10) {
                Thread.sleep(3000L);
                for (int i = 0; i < 30; i++) {
                    System.out.println(" ");
                }
            }

        } while (opcion != 10);

    }

    public static String leerCadena(String titulo) {
        System.out.print(titulo + "> ");
        Scanner entrada = new Scanner(System.in);
        return entrada.nextLine();
    }

    public static int leerEntero(String titulo) {
        System.out.print(titulo + "> ");
        Scanner entrada = new Scanner(System.in);
        try {
            return entrada.nextInt();
        } catch (Throwable ex) {
            return -1;
        }
    }

}
