package app.banco.servidor.cuenta;

public class Bolsillo {

    private final CuentaDeAhorros cuenta;
    private int saldo;

    public Bolsillo(CuentaDeAhorros cuenta) {
        this.cuenta = cuenta;
    }

    public CuentaDeAhorros getCuenta() {
        return cuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

}
