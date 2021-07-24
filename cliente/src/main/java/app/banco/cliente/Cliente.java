package app.banco.cliente;

import app.banco.protocolo.paquete.AbrirBolsilloPaquete;
import app.banco.protocolo.paquete.AbrirCuentaPaquete;
import app.banco.protocolo.paquete.CancelarBolsilloPaquete;
import app.banco.protocolo.paquete.Paquete;
import app.banco.protocolo.ProtocoloManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente {

    private final static int PUERTO = 8090;

    public int abrirCuenta(String nombre) throws Exception {
        return solicitar(new AbrirCuentaPaquete(nombre));
    }

    public int crearBolsillo(int numeroCuenta) throws Exception {
        return solicitar(new AbrirBolsilloPaquete(numeroCuenta));
    }

    public int cancelarBolsillo(String bolsilloId) throws Exception {
        return solicitar(new CancelarBolsilloPaquete(bolsilloId));
    }

    private int solicitar(Paquete paquete) throws Exception {
        Socket conexion = new Socket("localhost", PUERTO);
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        return ProtocoloManager.solicitar(paquete, entrada, salida);
    }

}
