package app.banco.protocolo;

import app.banco.protocolo.paquete.*;

public enum TipoPaquete {

    ABRIR_CUENTA (AbrirCuentaPaquete.class),
    ABRIR_BOLSILLO (AbrirBolsilloPaquete.class),
    CANCELAR_BOLSILLO (CancelarBolsilloPaquete.class),
    CANCELAR_CUENTA (CancelarCuentaPaquete.class),
    CONSULTAR (ConsultarPaquete.class);

    private final Class<? extends Paquete> paquete;

    TipoPaquete(Class<? extends Paquete> paquete) {
        this.paquete = paquete;
    }

    public Paquete crear() throws Exception {
        return this.paquete.getDeclaredConstructor().newInstance();
    }


}
