package app.banco.cliente;

import app.banco.Configuracion;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.ProtocoloManager;
import app.banco.protocolo.transaccion.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente {

    public PaqueteLector abrirCuenta(String nombre) throws Exception {
        return solicitar(new AbrirCuentaTransaccion(nombre));
    }

    public PaqueteLector cancelarCuenta(int codigo) throws Exception {
        return solicitar(new CancelarCuentaTransaccion(codigo));
    }

    public PaqueteLector crearBolsillo(int numeroCuenta) throws Exception {
        return solicitar(new AbrirBolsilloTransaccion(numeroCuenta));
    }

    public PaqueteLector cancelarBolsillo(String bolsilloId) throws Exception {
        return solicitar(new CancelarBolsilloTransaccion(bolsilloId));
    }

    public PaqueteLector consultarSaldo(String codigo) throws Exception {
        return solicitar(new ConsultarTransaccion(codigo));
    }

    public PaqueteLector depositarSaldo(int numeroCuenta, int valor) throws Exception {
        return solicitar(new DepositarDineroTransaccion(numeroCuenta, valor));
    }

    public PaqueteLector retirarSaldo(int numeroCuenta, int valor) throws Exception {
        return solicitar(new RetirarDineroTransaccion(numeroCuenta, valor));
    }

    public PaqueteLector trasladarSaldo(int numeroCuenta, int valor) throws Exception {
        return solicitar(new TrasladarDineroTransaccion(numeroCuenta, valor));
    }

    public PaqueteLector cargarArchivo(String archivo) throws Exception {
        return solicitar(new CargarTransaccion(archivo));
    }

    private PaqueteLector solicitar(Transaccion transaccion) throws Exception {
        Socket conexion = new Socket(Configuracion.SERVIDOR, Configuracion.PUERTO);
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        return ProtocoloManager.solicitar(transaccion, entrada, salida);
    }


}
