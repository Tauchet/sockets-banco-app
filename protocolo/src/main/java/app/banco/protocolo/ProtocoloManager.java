package app.banco.protocolo;

import app.banco.protocolo.paquete.Paquete;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ProtocoloManager {

    public static void responder(DataInputStream entrada, DataOutputStream salida, ProtocoloLector lector) throws Exception {

        String tipoId = entrada.readUTF();
        TipoPaquete tipoPaquete = TipoPaquete.valueOf(tipoId.toUpperCase());

        Paquete peticion = tipoPaquete.crear();
        peticion.leer(entrada);

        // Tiene un resultado
        salida.writeInt(lector.resolver(peticion));

    }

    public static int solicitar(Paquete peticion, DataInputStream entrada, DataOutputStream salida) throws Exception {

        salida.writeUTF(peticion.getId().name());
        peticion.escribir(salida);

        // Resultado
        return entrada.readInt();

    }

}
