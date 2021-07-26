package app.banco.protocolo;

import app.banco.protocolo.transaccion.Transaccion;

public interface ProtocoloLector {

    void resolver(Transaccion solicitud, PaqueteEscritor escritor);

}
