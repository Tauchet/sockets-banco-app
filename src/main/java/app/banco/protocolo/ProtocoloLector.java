package app.banco.protocolo;

import app.banco.protocolo.transaccion.Transaccion;

import java.net.InetAddress;
import java.net.SocketAddress;

public interface ProtocoloLector {

    void resolver(SocketAddress clienteIp, Transaccion solicitud, PaqueteEscritor escritor);

}
