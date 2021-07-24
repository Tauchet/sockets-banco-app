package app.banco.protocolo.transaccion;

import app.banco.protocolo.TipoTransaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    public void leer(DataInputStream entrada) throws IOException {
        this.codigo = entrada.readUTF();
    }

    @Override
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeUTF(this.codigo);
    }



}
