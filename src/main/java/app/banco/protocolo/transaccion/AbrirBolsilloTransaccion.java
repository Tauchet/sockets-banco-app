package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class AbrirBolsilloTransaccion extends Transaccion {

    private int cuentaAhorros;

    public AbrirBolsilloTransaccion() {
        super(TipoTransaccion.ABRIR_BOLSILLO);
    }

    public AbrirBolsilloTransaccion(int cuentaAhorros) {
        super(TipoTransaccion.ABRIR_BOLSILLO);
        this.cuentaAhorros = cuentaAhorros;
    }

    public int getCuentaAhorros() {
        return cuentaAhorros;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.cuentaAhorros = entrada.leerEntero();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirEntero(this.cuentaAhorros);
    }


}
