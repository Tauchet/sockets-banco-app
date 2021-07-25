package app.banco.protocolo;

import app.banco.protocolo.transaccion.Transaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ProtocoloManager {

    public static String resolverComando(String comando, ProtocoloLector lector) throws Exception {

        PaqueteLector paqueteLector = new PaqueteLector(comando);
        PaqueteEscritor escritor = new PaqueteEscritor();

        String tipoId = paqueteLector.leerCadena();
        TipoTransaccion tipoTransaccion = null;
        try {
            tipoTransaccion = TipoTransaccion.valueOf(tipoId.toUpperCase());
        } catch (Throwable ex) {
            return "¡La opción no existe!";
        }

        Transaccion peticion = tipoTransaccion.crear();
        try {
            peticion.leer(paqueteLector);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return "¡Los datos son inconsistentes!";
        } catch (NumberFormatException ex) {
            return "¡Los valores enteros son incorrectos!";
        } catch (IOException e) {
            return "¡Ha ocurrido un error inesperado!";
        }

        lector.resolver(peticion, escritor);
        return escritor.resultado();
    }

    public static void resolver(DataInputStream entrada, DataOutputStream salida, ProtocoloLector lector) throws Exception {
        String comando = entrada.readUTF();
        String resultado = resolverComando(comando, lector);
        salida.writeUTF(resultado);
        salida.flush();
    }

    public static PaqueteLector solicitar(
            Transaccion transaccion,
            DataInputStream entrada,
            DataOutputStream salida) throws Exception {

        PaqueteEscritor escritor = new PaqueteEscritor();

        escritor.escribirCadena(transaccion.getId().name());
        transaccion.escribir(escritor);

        salida.writeUTF(escritor.resultado());

        // Resultado
        return new PaqueteLector(entrada.readUTF());

    }

}
