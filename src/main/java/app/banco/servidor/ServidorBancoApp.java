package app.banco.servidor;

import app.banco.protocolo.ProtocoloManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorBancoApp {

    public static void main(String[] args) throws Exception {

        Banco banco = new Banco();
        ServerSocket servidor = new ServerSocket(8090);
        System.out.println("Se ha ejecutado el servidor.");

        while (true) {
            Socket socket = servidor.accept();
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            ProtocoloManager.resolver(socket.getRemoteSocketAddress(), entrada, salida, banco);
            socket.close();
        }

    }



}
