package app.banco.protocolo.transaccion;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.PaqueteLector;
import app.banco.protocolo.TipoTransaccion;

import java.io.IOException;

public class ConsultarTransaccion extends Transaccion {

    private String codigo;

    public ConsultarTransaccion() {
        super(TipoTransaccion.CONSULTAR);
    }

    public ConsultarTransaccion(String codigo) {
        super(TipoTransaccion.CONSULTAR);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public void leer(PaqueteLector entrada) throws IOException {
        this.codigo = entrada.leerCadena();
    }

    @Override
    public void escribir(PaqueteEscritor salida) throws IOException {
        salida.escribirCadena(this.codigo);
    }

    @Override
    public String toString() {
        return "ConsultarTransaccion{" +
                "codigo='" + codigo + '\'' +
                '}';
    }

}
