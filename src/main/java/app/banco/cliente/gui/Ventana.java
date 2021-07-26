package app.banco.cliente.gui;

import app.banco.cliente.Cliente;
import app.banco.protocolo.PaqueteLector;

import javax.swing.*;
import java.util.List;

public class Ventana extends JFrame {

    private JPanel panel;

    private JTextField abrirCuentaEntrada;
    private JButton abrirCuentaButton;

    private JTextField crearBolsilloEntrada;
    private JButton crearBolsilloButton;

    private JTextField cancelarBolsilloEntrada;
    private JButton cancelarBolsilloButton;

    private JTextField cancelarCuentaEntrada;
    private JButton cancelarCuentaButton;

    private JTextField depositarDineroEntrada;
    private JTextField depositarDineroValorEntrada;
    private JButton depositarDineroButton;

    private JTextField retirarDineroEntrada;
    private JTextField retirarDineroValorEntrada;
    private JButton retirarDineroButton;

    private JTextField trasladarDineroValorEntrada;
    private JTextField trasladarDineroEntrada;
    private JButton trasladarDineroButton;

    private JTextField consultarEntrada;
    private JButton consultarButton;

    private JTextField cargarArchivoEntrada;
    private JButton cargarArchivoButton;

    public Ventana(Cliente cliente) {

        super("BancoApp");
        setContentPane(panel);

        abrirCuentaButton.addActionListener(e -> {
            try {
                PaqueteLector resultado = cliente.abrirCuenta(abrirCuentaEntrada.getText());
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        crearBolsilloButton.addActionListener(e -> {
            try {

                int numeroCuenta = -1;
                try {
                    numeroCuenta = Integer.parseInt(crearBolsilloEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado un número correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PaqueteLector resultado = cliente.crearBolsillo(numeroCuenta);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelarBolsilloButton.addActionListener(e -> {
            try {

                String bolsilloId = cancelarBolsilloEntrada.getText();
                PaqueteLector resultado = cliente.cancelarBolsillo(bolsilloId);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        cancelarCuentaButton.addActionListener(e -> {
            try {

                int numeroCuenta = -1;
                try {
                    numeroCuenta = Integer.parseInt(cancelarCuentaEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado un número correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PaqueteLector resultado = cliente.cancelarCuenta(numeroCuenta);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        depositarDineroButton.addActionListener(e -> {
            try {

                int numeroCuenta = -1;
                try {
                    numeroCuenta = Integer.parseInt(depositarDineroEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado el número de la cuenta correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int valor = -1;
                try {
                    valor = Integer.parseInt(depositarDineroValorEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado el número del valor correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PaqueteLector resultado = cliente.depositarSaldo(numeroCuenta, valor);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        retirarDineroButton.addActionListener(e -> {
            try {

                int numeroCuenta = -1;
                try {
                    numeroCuenta = Integer.parseInt(retirarDineroEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado el número de la cuenta correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int valor = -1;
                try {
                    valor = Integer.parseInt(retirarDineroValorEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado el número del valor correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PaqueteLector resultado = cliente.retirarSaldo(numeroCuenta, valor);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        trasladarDineroButton.addActionListener(e -> {
            try {

                int numeroCuenta = -1;
                try {
                    numeroCuenta = Integer.parseInt(trasladarDineroEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado el número de la cuenta correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int valor = -1;
                try {
                    valor = Integer.parseInt(trasladarDineroValorEntrada.getText());
                } catch (Throwable ignored) {
                    JOptionPane.showMessageDialog(this, "No se ha ingresado el número del valor correctamente.", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PaqueteLector resultado = cliente.depositarSaldo(numeroCuenta, valor);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        consultarButton.addActionListener(e -> {
            try {

                String texto = consultarEntrada.getText();
                PaqueteLector resultado = cliente.consultarSaldo(texto);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""), "¡Se ha completado la solicitud!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        cargarArchivoButton.addActionListener(e -> {
            try {

                String texto = cargarArchivoEntrada.getText();
                PaqueteLector resultado = cliente.cargarArchivo(texto);
                String mensaje = resultado.leerCadena();
                if (mensaje.startsWith("ERROR:")) {
                    JOptionPane.showMessageDialog(this, mensaje.replace("ERROR:", ""), "¡Error en la solicitud!", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<String> log = resultado.leerLista();
                    JOptionPane.showMessageDialog(this, mensaje.replace("OK:", ""),
                            "¡Se ha completado la solicitud!\n\n" + String.join("\n\n", log),
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "¡Ha ocurrido un error inesperado!", "¡Error Interno!", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
