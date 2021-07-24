package app.banco.protocolo;

import app.banco.protocolo.transaccion.Transaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ProtocoloManager {

    public static void responder(DataInputStream entrada, DataOutputStream salida, ProtocoloLector lector) throws Exception {

        long tiempo = entrada.readLong();
        String tipoId = entrada.readUTF();
        TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipoId.toUpperCase());

        Transaccion peticion = tipoTransaccion.crear();
        peticion.setTiempo(tiempo);
        peticion.leer(entrada);

        // Tiene un resultado
        salida.writeInt(lector.resolver(peticion));

    }

    public static int solicitar(Transaccion peticion, DataInputStream entrada, DataOutputStream salida) throws Exception {

        salida.writeLong(peticion.getTiempo());
        salida.writeUTF(peticion.getId().name());
        peticion.escribir(salida);

        // Resultado
        return entrada.readInt();

    }

}
