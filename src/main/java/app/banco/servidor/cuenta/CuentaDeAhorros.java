package app.banco.servidor.cuenta;

public class CuentaDeAhorros {

    private final int id;
    private String nombre;
    private int saldo;

    private Bolsillo bolsillo;

    public CuentaDeAhorros(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = 0;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Bolsillo getBolsillo() {
        return bolsillo;
    }

    public void setBolsillo(Bolsillo bolsillo) {
        this.bolsillo = bolsillo;
    }

}
