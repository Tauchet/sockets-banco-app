package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class CancelarBolsilloTransaccion extends Transaccion {

    private String bolsilloId;

    public CancelarBolsilloTransaccion() {
        super(TipoTransaccion.CANCELAR_BOLSILLO);
    }

    public CancelarBolsilloTransaccion(String bolsilloId) {
        super(TipoTransaccion.CANCELAR_BOLSILLO);
        this.bolsilloId = bolsilloId;
    }

    public String getBolsilloId() {
        return bolsilloId;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.bolsilloId = entrada.leerCadena();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirCadena(this.bolsilloId);
    }

}
