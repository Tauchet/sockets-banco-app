package app.banco.cliente.cli;

import app.banco.cliente.Cliente;

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
            System.out.println(" 5. Depositar en una cuenta de ahorros.");
            System.out.println(" 8. Consultar saldo de cuenta o bolsillo.");
            System.out.println(" 10. Salir de la aplicación.");
            System.out.println("=========================================");

            opcion = leerEntero("");

            switch (opcion) {

                case 1: {

                    String nombre = leerCadena("¿Cuál es el nombre de la cuenta?");
                    int resultado = cliente.abrirCuenta(nombre);

                    if (resultado == -1) {
                        System.out.println("¡Ya existe una cuenta con este mismo nombre!");
                    }

                    if (resultado >= 0) {
                        System.out.println("¡La cuenta ha sido creada correctamente!");
                        System.out.println("# de Cuenta: " + resultado);
                    }

                    break;
                }

                case 2: {

                    int codigo = leerEntero("¿# de Cuenta de Ahorros?");
                    int resultado = cliente.crearBolsillo(codigo);

                    if (resultado == -1) {
                        System.out.println("¡La cuenta no ha existido!");
                    }

                    if (resultado == -2) {
                        System.out.println("¡Esta cuenta ya tiene un bolsillo!");
                    }

                    if (resultado == 0) {
                        System.out.println("¡Ha sido creado correctamente!");
                    }

                    break;
                }

                case 3: {

                    String cadena = leerCadena("¿# del bosillo?");
                    int resultado = cliente.cancelarBolsillo(cadena);

                    if (resultado == -1) {
                        System.out.println("¡No existe el bolsillo para cancelar!");
                    }

                    if (resultado >= 0) {
                        System.out.println("¡Ha sido eliminado correctamente el bolsillo y se ha transferido " + resultado + " a la cuenta de ahorros!");
                    }

                    break;
                }

                case 4: {

                    int codigo = leerEntero("¿# de Cuenta de Ahorros?");
                    int resultado = cliente.cancelarCuenta(codigo);

                    if (resultado == -1) {
                        System.out.println("¡No existe la cuenta de ahorros!");
                    }

                    if (resultado == -2) {
                        System.out.println("¡Lo sentimos! La cuenta de ahorros buscada tiene vinculada un bolsillo.");
                    }

                    if (resultado == -3) {
                        System.out.println("¡Lo sentimos! La cuenta de ahorros aún tiene saldo.");
                    }

                    if (resultado == 0) {
                        System.out.println("¡La cuenta de ahorros ha sido eliminada!");
                    }

                    break;
                }

                case 5: {

                    int cuentaAhorros = leerEntero("¿# de Cuenta de Ahorros?");
                    int saldo = leerEntero("¿Cuánto dinero quiere depositar?");

                    int resultado = cliente.depositarSaldo(cuentaAhorros, saldo);

                    if (resultado == -1) {
                        System.out.println("¡No existe la cuenta de ahorros!");
                    }

                    if (resultado == -2) {
                        System.out.println("¡No puede depositar valores ni cero ni valores negativos!");
                    }

                    if (resultado == 0) {
                        System.out.println("¡Se han depositado $"+saldo+" pesos a su cuenta de ahorros!");
                    }

                    break;
                }

                case 8: {

                    String codigo = leerCadena("¿# de Cuenta o Bolsillo?");
                    int resultado = cliente.consultarSaldo(codigo);

                    if (resultado == -1) {
                        System.out.println("¡Los datos ingresados son incorrectos!");
                    }

                    if (resultado == -2) {
                        System.out.println("¡Lo sentimos! El bolsillo buscado no existe.");
                    }

                    if (resultado == -3) {
                        System.out.println("¡Lo sentimos! La cuenta de ahorros no se ha encontrado.");
                    }

                    if (resultado >= 0) {
                        System.out.println("Saldo actual: " + resultado);
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
