package app.banco.cliente.gui;

import app.banco.cliente.Cliente;

import javax.swing.*;

public class AplicacionGUI {

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            Ventana ventana = new Ventana(cliente);
            ventana.setResizable(false);
            ventana.setSize(600, 300);
            ventana.setVisible(true);
        });
    }

}
