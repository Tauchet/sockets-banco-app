package app.banco.protocolo;

import app.banco.protocolo.transaccion.*;

public enum TipoTransaccion {

    ABRIR_CUENTA (AbrirCuentaTransaccion.class),
    ABRIR_BOLSILLO (AbrirBolsilloTransaccion.class),
    CANCELAR_BOLSILLO (CancelarBolsilloTransaccion.class),
    CANCELAR_CUENTA (CancelarCuentaTransaccion.class),
    CONSULTAR (ConsultarTransaccion.class),
    DEPOSITAR (DepositarDineroTransaccion.class),
    RETIRAR (RetirarDineroTransaccion.class),
    TRASLADAR (TrasladarDineroTransaccion.class);

    private final Class<? extends Transaccion> transaccion;

    TipoTransaccion(Class<? extends Transaccion> transaccion) {
        this.transaccion = transaccion;
    }

    public Transaccion crear() throws Exception {
        return this.transaccion.getDeclaredConstructor().newInstance();
    }


}
