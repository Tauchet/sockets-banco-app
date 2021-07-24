package app.banco.servidor;

import app.banco.protocolo.ProtocoloLector;
import app.banco.protocolo.paquete.*;
import app.banco.servidor.cuenta.Bolsillo;
import app.banco.servidor.cuenta.CuentaDeAhorros;

import java.util.HashMap;
import java.util.Map;

public class Banco implements ProtocoloLector {

    private final Map<Integer, CuentaDeAhorros> cuentasDeAhorros = new HashMap<>();
    private int idCuentasDeAhorros = 0;

    private final Map<String, Bolsillo> bolsillos = new HashMap<>();

    public CuentaDeAhorros buscarCuentaPorNombre(String nombre) {
        for (CuentaDeAhorros cuenta: this.cuentasDeAhorros.values()) {
            if (cuenta.getNombre().equalsIgnoreCase(nombre)) {
                return cuenta;
            }
        }
        return null;
    }

    public CuentaDeAhorros buscarCuentaPorId(int id) {
        return this.cuentasDeAhorros.getOrDefault(id, null);
    }

    public CuentaDeAhorros abrirCuenta(String nombre) {
        CuentaDeAhorros usuario = new CuentaDeAhorros(this.idCuentasDeAhorros, nombre);
        this.cuentasDeAhorros.put(this.idCuentasDeAhorros, usuario);
        this.idCuentasDeAhorros++;
        return usuario;
    }

    private void cancelarCuenta(CuentaDeAhorros cuenta) {
        this.cuentasDeAhorros.remove(cuenta.getId());
    }

    public Bolsillo buscarBolsilloPorId(String id) {
        return this.bolsillos.getOrDefault(id, null);
    }

    public Bolsillo crearBolsillo(CuentaDeAhorros cuenta) {
        Bolsillo bolsillo = new Bolsillo(cuenta);
        cuenta.setBolsillo(bolsillo);
        this.bolsillos.put(cuenta.getId() + "b", bolsillo);
        return bolsillo;
    }

    private void cancelarBolsillo(Bolsillo bolsillo) {
        bolsillo.getCuenta().setSaldo(bolsillo.getSaldo() + bolsillo.getCuenta().getSaldo());
        bolsillo.getCuenta().setBolsillo(null);
    }

    @Override
    public int resolver(Paquete solicitud) {

        if (solicitud instanceof AbrirCuentaPaquete) {

            AbrirCuentaPaquete peticion = (AbrirCuentaPaquete) solicitud;
            if (buscarCuentaPorNombre(peticion.getNombreUsuario()) != null) {
                // -1: ¡Ya existe una Cuenta con este nombre!
                return -1;
            }

            CuentaDeAhorros cuenta = abrirCuenta(peticion.getNombreUsuario());

            // >= 0: Número de la app.banco.servidor.cuenta.
            return cuenta.getId();

        }

        if (solicitud instanceof AbrirBolsilloPaquete) {

            AbrirBolsilloPaquete peticion = (AbrirBolsilloPaquete) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                return -1;
            }

            Bolsillo bolsillo = buscarBolsilloPorId(peticion.getCuentaAhorros() + "b");

            if (bolsillo != null) {
                // -2: ¡Esta cuenta ya tiene un bolsillo creado!
                return -2;
            }

            crearBolsillo(cuenta);

            // 0: Exitoso.
            return 0;
        }

        if (solicitud instanceof CancelarBolsilloPaquete) {

            CancelarBolsilloPaquete peticion = (CancelarBolsilloPaquete) solicitud;
            Bolsillo bolsillo = this.bolsillos.remove(peticion.getBolsilloId());

            if (bolsillo == null) {
                // -1: ¡No existe el bolsillo por cancelar!
                return -1;
            }

            // Regresar el dinero del bolsillo a la cuenta.
            cancelarBolsillo(bolsillo);

            // 0: Exitoso.
            return 0;
        }

        if (solicitud instanceof CancelarCuentaPaquete) {

            CancelarCuentaPaquete peticion = (CancelarCuentaPaquete) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                return -1;
            }

            if (cuenta.getBolsillo() != null) {
                // -2: Tiene un bolsillo anclado.
                return -2;
            }

            if (cuenta.getSaldo() > 0) {
                // -2: Esta cuenta aún tiene saldo.
                return -3;
            }

            cancelarCuenta(cuenta);

            // 0: Exitoso.
            return 0;
        }

        if (solicitud instanceof ConsultarPaquete) {

            ConsultarPaquete peticion = (ConsultarPaquete) solicitud;
            String codigo = peticion.getCodigo();

            // Posible bolsillo
            boolean esBolsillo = false;
            if (codigo.endsWith("b")) {
                esBolsillo = true;
                codigo = codigo.substring(0, codigo.length() - 1);
            }

            int codigoId = 0;
            try {
                codigoId = Integer.parseInt(codigo);
            } catch (Throwable ex) {
                // -1: Datos mal ingresados.
                return -1;
            }

            if (esBolsillo) {

                Bolsillo bolsillo = buscarBolsilloPorId(codigoId + "b");

                if (bolsillo == null) {
                    // -2: ¡El bolsillo no existe!
                    return -2;
                }

                return bolsillo.getSaldo();
            }

            CuentaDeAhorros cuenta = buscarCuentaPorId(codigoId);

            if (cuenta == null) {
                // -3: ¡La cuenta no existe!
                return -3;
            }

            // >=0: Exitoso.
            return cuenta.getSaldo();
        }

        return -1;
    }




}
