package app.banco.cliente.cli;

import app.banco.cliente.Cliente;

public class AplicacionCLI {

    public static void main(String[] args) throws Exception {

        Cliente cliente = new Cliente();
        System.out.println(cliente.abrirCuenta("Cristian"));
        System.out.println(cliente.abrirCuenta("Cristian"));
        System.out.println(cliente.abrirCuenta("Henry"));

    }

}
