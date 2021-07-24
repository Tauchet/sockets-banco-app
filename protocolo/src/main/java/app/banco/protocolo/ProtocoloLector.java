package app.banco.protocolo;

import app.banco.protocolo.transaccion.Transaccion;

public interface ProtocoloLector {

    int resolver(Transaccion solicitud);

}
