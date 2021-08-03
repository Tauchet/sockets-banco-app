package app.banco.protocolo;

import app.banco.protocolo.transaccion.Transaccion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;

public class ProtocoloManager {

    public static String resolverComando(SocketAddress clienteIp, String comando, ProtocoloLector lector) throws Exception {

        PaqueteLector paqueteLector = new PaqueteLector(comando);
        PaqueteEscritor escritor = new PaqueteEscritor();

        // Leer el tipo de transacción
        String tipoId = paqueteLector.leerCadena();
        TipoTransaccion tipoTransaccion;
        try {
            tipoTransaccion = TipoTransaccion.valueOf(tipoId.toUpperCase());
        } catch (Throwable ex) {
            return "ERROR:¡La opción no existe!";
        }

        // Crea una instancia de la transacción
        Transaccion transaccion = tipoTransaccion.crear();
        try {
            transaccion.leer(paqueteLector);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return "ERROR:¡Los datos son inconsistentes!";
        } catch (NumberFormatException ex) {
            return "ERROR:¡Los valores enteros son incorrectos!";
        } catch (IOException e) {
            return "ERROR:¡Ha ocurrido un error inesperado!";
        }


        lector.resolver(clienteIp, transaccion, escritor);
        String resultado = escritor.resultado();

        System.out.println("[Cliente] [" + clienteIp + "] ====================================");
        System.out.println("[Cliente] [" + clienteIp + "] Ejecutando:");
        System.out.println("[Cliente] [" + clienteIp + "]   - Transacción: " + transaccion);
        System.out.println("[Cliente] [" + clienteIp + "]   - Resultado: " + resultado);
        System.out.println("[Cliente] [" + clienteIp + "] ====================================");

        return resultado;
    }

    public static void resolver(SocketAddress clienteIp, DataInputStream entrada, DataOutputStream salida, ProtocoloLector lector) throws Exception {
        String comando = entrada.readUTF();
        String resultado = resolverComando(clienteIp, comando, lector);
        salida.writeUTF(resultado);
        salida.flush();
    }

    public static PaqueteLector solicitar(
            Transaccion transaccion,
            DataInputStream entrada,
            DataOutputStream salida) throws Exception {

        PaqueteEscritor escritor = new PaqueteEscritor();

        // Escribir datos de transacción
        escritor.escribirCadena(transaccion.getId().name());
        transaccion.escribir(escritor);

        // Resultado: EVENTO, informacion...
        salida.writeUTF(escritor.resultado());

        // Resultado
        return new PaqueteLector(entrada.readUTF());

    }

}
