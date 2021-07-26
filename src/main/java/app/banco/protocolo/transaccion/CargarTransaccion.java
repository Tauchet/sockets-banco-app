package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class CargarTransaccion extends Transaccion {

    private String archivo;

    public CargarTransaccion() {
        super(TipoTransaccion.CARGAR);
    }

    public CargarTransaccion(String archivo) {
        super(TipoTransaccion.CARGAR);
        this.archivo = archivo;
    }

    public String getArchivo() {
        return archivo;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.archivo = entrada.leerCadena();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirCadena(this.archivo);
    }

}
