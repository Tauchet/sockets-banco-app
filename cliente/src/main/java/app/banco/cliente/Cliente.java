package app.banco.cliente;

import app.banco.protocolo.transaccion.*;
import app.banco.protocolo.ProtocoloManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente {

    private final static int PUERTO = 8090;

    public int abrirCuenta(String nombre) throws Exception {
        return solicitar(new AbrirCuentaTransaccion(nombre));
    }

    public int cancelarCuenta(int codigo) throws Exception {
        return solicitar(new CancelarCuentaTransaccion(codigo));
    }

    public int crearBolsillo(int numeroCuenta) throws Exception {
        return solicitar(new AbrirBolsilloTransaccion(numeroCuenta));
    }

    public int cancelarBolsillo(String bolsilloId) throws Exception {
        return solicitar(new CancelarBolsilloTransaccion(bolsilloId));
    }

    public int consultarSaldo(String codigo) throws Exception {
        return solicitar(new ConsultarTransaccion(codigo));
    }

    public int depositarSaldo(int numeroCuenta, int valor) throws Exception {
        return solicitar(new DepositarDineroTransaccion(numeroCuenta, valor));
    }

    public int retirarSaldo(int numeroCuenta, int valor) throws Exception {
        return solicitar(new RetirarDineroTransaccion(numeroCuenta, valor));
    }

    public int trasladarSaldo(int numeroCuenta, int valor) throws Exception {
        return solicitar(new TrasladarDineroTransaccion(numeroCuenta, valor));
    }

    private int solicitar(Transaccion transaccion) throws Exception {
        Socket conexion = new Socket("localhost", PUERTO);
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        return ProtocoloManager.solicitar(transaccion, entrada, salida);
    }


}
