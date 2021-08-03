package app.banco.servidor;

import app.banco.protocolo.PaqueteEscritor;
import app.banco.protocolo.ProtocoloLector;
import app.banco.protocolo.ProtocoloManager;
import app.banco.protocolo.transaccion.*;
import app.banco.servidor.cuenta.Bolsillo;
import app.banco.servidor.cuenta.CuentaDeAhorros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private boolean esTodoLetras(String valor) {
        int letras = 0;
        for (int i = 0; i < valor.length(); i++) {
            char caracter = valor.charAt(i);
            if (Character.isLetter(caracter) || caracter == ' ') {
                letras++;
            }
        }
        return letras == valor.length();
    }

    public String eliminarEspacio(String valor) {
        while (valor.contains("  ")) {
            valor = valor.replace("  ", " ");
        }
        return valor;
    }

    @Override
    public void resolver(SocketAddress clienteIp, Transaccion solicitud, PaqueteEscritor escritor) {

        if (solicitud instanceof AbrirCuentaTransaccion) {

            AbrirCuentaTransaccion peticion = (AbrirCuentaTransaccion) solicitud;
            String nombre = eliminarEspacio(peticion.getNombreCompleto());

            // Validar que sea Nombre Apellido
            if (!esTodoLetras(nombre)) {
                escritor.escribirCadena("ERROR:¡El nombre solo puede contener letras!");
                return;
            }

            String[] entradas = nombre.split(" ");
            if (entradas.length < 2) {
                escritor.escribirCadena("ERROR:¡El nombre debe ser al menos Nombre Apellido! Ejemplo: Juan Gutierrez");
                return;
            }

            nombre = formatoNombre(nombre);

            if (buscarCuentaPorNombre(nombre) != null) {
                escritor.escribirCadena("ERROR:¡Ya existe una cuenta con este nombre!");
                return;
            }

            CuentaDeAhorros cuenta = abrirCuenta(nombre);

            // >= 0: Número de la app.banco.servidor.cuenta.
            escritor.escribirCadena("OK:¡La cuenta ha sido creada con exito con el número " + cuenta.getId() + "!");
            return;

        }

        if (solicitud instanceof AbrirBolsilloTransaccion) {

            AbrirBolsilloTransaccion peticion = (AbrirBolsilloTransaccion) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                escritor.escribirCadena("ERROR:¡La cuenta de ahorros no existe!");
                return;
            }

            Bolsillo bolsillo = buscarBolsilloPorId(peticion.getCuentaAhorros() + "b");

            if (bolsillo != null) {
                escritor.escribirCadena("ERROR:¡Esta cuenta ya tiene un bolsillo creado!");
                return;
            }

            crearBolsillo(cuenta);

            escritor.escribirCadena("OK:¡El bolsillo ha sido creado con exito, con el número " + cuenta.getId() + "b!");
            return;

        }

        if (solicitud instanceof CancelarBolsilloTransaccion) {

            CancelarBolsilloTransaccion peticion = (CancelarBolsilloTransaccion) solicitud;
            Bolsillo bolsillo = this.bolsillos.remove(peticion.getBolsilloId());

            if (bolsillo == null) {
                escritor.escribirCadena("ERROR:¡No existe el bolsillo que desea cancelar!");
                return;
            }

            // Regresar el dinero del bolsillo a la cuenta.
            cancelarBolsillo(bolsillo);

            // 0: Exitoso.
            escritor.escribirCadena("OK:¡Se ha cancelado completamente el bolsillo! Se han transferido $" + bolsillo.getSaldo() + " pesos a tu cuenta de ahorros.");
            return;
        }

        if (solicitud instanceof CancelarCuentaTransaccion) {

            CancelarCuentaTransaccion peticion = (CancelarCuentaTransaccion) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                escritor.escribirCadena("ERROR:¡No existe la cuenta a la que desea cancelar!");
                return;
            }

            if (cuenta.getBolsillo() != null) {
                // -2: Tiene un bolsillo anclado.
                escritor.escribirCadena("ERROR:¡Esta cuenta tiene un bolsillo vinculado!");
                return;
            }

            if (cuenta.getSaldo() > 0) {
                // -2: Esta cuenta aún tiene saldo.
                escritor.escribirCadena("ERROR:¡Esta cuenta aún tiene saldo!");
                return;
            }

            cancelarCuenta(cuenta);

            // 0: Exitoso.
            escritor.escribirCadena("OK:¡Se ha cancelado correctamente la cuenta de ahorros!");
            return;

        }

        if (solicitud instanceof ConsultarTransaccion) {

            ConsultarTransaccion peticion = (ConsultarTransaccion) solicitud;
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
                escritor.escribirCadena("ERROR:¡Los valores enteros son incorrectos!");
                return;
            }

            if (esBolsillo) {

                Bolsillo bolsillo = buscarBolsilloPorId(codigoId + "b");

                if (bolsillo == null) {
                    // -2: ¡El bolsillo no existe!
                    escritor.escribirCadena("ERROR:¡No existe el bolsillo que desea cancelar!");
                    return;
                }

                escritor.escribirCadena("OK:Saldo actual de bolsillo: " + bolsillo.getSaldo());
                return;
            }

            CuentaDeAhorros cuenta = buscarCuentaPorId(codigoId);

            if (cuenta == null) {
                // -3: ¡La cuenta no existe!
                escritor.escribirCadena("ERROR:¡No existe la cuenta que deseas consultar!");
                return;
            }

            // >=0: Exitoso.
            escritor.escribirCadena("OK:Saldo actual de la Cuenta de Ahorros: " + cuenta.getSaldo());
            return;

        }

        if (solicitud instanceof DepositarDineroTransaccion) {

            DepositarDineroTransaccion peticion = (DepositarDineroTransaccion) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());
            int valor = peticion.getValor();

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                escritor.escribirCadena("ERROR:¡No existe la cuenta a la que desea depositar!");
                return;
            }

            if (valor <= 0) {
                // -2: ¡No puede depositar valores ni cero ni valores negativos!
                escritor.escribirCadena("ERROR:¡El monto minímo de deposito debe ser mayor a 0!");
                return;
            }

            cuenta.setSaldo(cuenta.getSaldo()+valor);

            // 0: Exitoso.
            escritor.escribirCadena("OK:Se han depositado $" + valor + " pesos a tu cuenta de ahorros, ahora su saldo actual será: $" + cuenta.getSaldo());
            return;

        }

        if (solicitud instanceof RetirarDineroTransaccion) {

            RetirarDineroTransaccion peticion = (RetirarDineroTransaccion) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());
            int valor = peticion.getValor();

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                escritor.escribirCadena("ERROR:¡No existe la cuenta que desea para retirar!");
                return;
            }

            if (valor <= 0){
                // -2: ¡No puede depositar valores ni cero ni valores negativos!
                escritor.escribirCadena("ERROR:¡El monto minímo de retiro debe ser mayor a 0!");
                return;
            }

            if (cuenta.getSaldo() < valor){
                // -3: ¡Saldo insuficiente!
                escritor.escribirCadena("ERROR:¡Su saldo es insuficiente!");
                return;
            }

            cuenta.setSaldo(cuenta.getSaldo()-valor);

            // 0: Exitoso.
            escritor.escribirCadena("OK:Se han retirado $" + valor + " pesos de tu cuenta de ahorros, ahora su saldo actual será: $" + cuenta.getSaldo());
            return;

        }

        if (solicitud instanceof TrasladarDineroTransaccion) {

            TrasladarDineroTransaccion peticion = (TrasladarDineroTransaccion) solicitud;
            CuentaDeAhorros cuenta = buscarCuentaPorId(peticion.getCuentaAhorros());
            int valor = peticion.getValor();

            if (cuenta == null) {
                // -1: ¡No existe la cuenta que se pide!
                escritor.escribirCadena("ERROR:¡No existe la cuenta que desea utilizar!");
                return;
            }

            if (valor <= 0){
                // -2: ¡No puede depositar valores ni cero ni valores negativos!
                escritor.escribirCadena("ERROR:¡El monto minímo de retiro debe ser mayor a 0!");
                return;
            }

            if (cuenta.getSaldo() < valor){
                // -3: ¡Saldo insuficiente!
                escritor.escribirCadena("ERROR:¡Su saldo es insuficiente!");
                return;
            }

            if (cuenta.getBolsillo() == null) {
                escritor.escribirCadena("ERROR:¡No existe el bolsillo que desea cancelar!");
                return;
            }

            cuenta.setSaldo(cuenta.getSaldo()-valor);
            cuenta.getBolsillo().setSaldo(cuenta.getBolsillo().getSaldo()+valor);

            // 0: Exitoso.
            escritor.escribirCadena("OK:Se han trasladado $" + valor + " pesos de tu cuenta de ahorros a tu bolsillo, tu saldo de cuenta será: $" + cuenta.getSaldo() + " y tu saldo de bolsillo: $" + cuenta.getBolsillo().getSaldo());
            return;

        }

        if (solicitud instanceof CargarTransaccion) {

            CargarTransaccion peticion = (CargarTransaccion) solicitud;
            String archivoPath = peticion.getArchivo();
            File archivo = new File(archivoPath);

            if (!archivo.exists()) {
                escritor.escribirCadena("ERROR:¡No existe el archivo que desea cargar!");
                return;
            }

            List<String> lineas = leerArchivo(archivo);

            if (lineas.isEmpty()) {
                escritor.escribirCadena("ERROR:¡El archivo se encuentra vacío!");
                return;
            }

            List<String> resultado = new ArrayList<>();
            escritor.escribirCadena("OK");
            for (int i = 0; i < lineas.size(); i++) {
                int numeroLinea = i + 1;
                try {
                    String linea = lineas.get(i);
                    resultado.add("[" + numeroLinea + "]: Ejecutar -> " + linea);
                    resultado.add("[" + numeroLinea + "]: Resultado -> " + ProtocoloManager.resolverComando(clienteIp, linea, this));
                } catch (Throwable ex) {
                    resultado.add("[" + numeroLinea + "]: Ha ocurrido un error.");
                    ex.printStackTrace();
                }
            }

            escritor.escribirLista(resultado);

            return;

        }

        escritor.escribirCadena("¡No se ha encontrado esta opción!");

    }

    private String formatoNombre(String nombre) {
        String[] espacios = nombre.split(" ");
        for (int i = 0; i < espacios.length; i++) {
            String argumento = espacios[i];
            espacios[i] = String.valueOf(argumento.charAt(0)).toUpperCase() + argumento.substring(1, argumento.length() - 1).toLowerCase();
        }
        return String.join(" ", espacios);
    }

    private static List<String> leerArchivo(File archivo) {
        List<String> lineas = new ArrayList<>();
        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(lector);
            String linea;
            while ((linea = buffer.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (Throwable ignored) {}
        return lineas;
    }

}
