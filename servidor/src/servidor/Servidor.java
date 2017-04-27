/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import java.util.logging.*;

/**
 *
 * @author Anny Chacon
 */
public class Servidor {

    ServerSocket server;
    Socket socket;
    DataOutputStream dout;
    DataInputStream din;
    BufferedInputStream bin;
    byte[] out, in;
    File f;
    int bytes;
    String ip, nombre, cadena;
    StringTokenizer tokens;
    static final int tambytes = 1048576; // 1Mb

    public Servidor() {
        server = null;
        socket = null;
        tokens = null;
        cadena = "";
        f = new File("D:/Imagenes/fractals/1.jpg");
        try {
            server = new ServerSocket(5000);
            System.out.println("Servidor Listo... Esperando Cliente...");
        } catch (IOException ex) {
            System.out.println("Problema al iniciar el servidor");
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket = new Socket();
        try {
            socket = server.accept();
            System.out.println("Cliente conectado...");
            dout = new DataOutputStream(socket.getOutputStream());
            din = new DataInputStream(socket.getInputStream());
            bin = new BufferedInputStream(new FileInputStream(f));
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //client  ** IP - NOMBRE **
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                cadena = new String(in, 0, bytes, StandardCharsets.UTF_8);
                tokens = new StringTokenizer(cadena, "?\n");
                ip = tokens.nextToken();
                nombre = tokens.nextToken();
                System.out.println("Cliente >> IP: " + ip + " Nombre: " + nombre);
            }

            //server  ** NOMBRE ARCHIVO - PESO **
            out = new byte[tambytes];
            cadena = f.getName() + "?" + f.length() + "\n";
            out = cadena.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();
            System.out.println("Servidor >> " + cadena);

            //client ** VERIFICACION - NOMBRE - PESO **
            String aux = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                aux = new String(in, 0, bytes, StandardCharsets.UTF_8);
                System.out.println("Cliente >> " + aux);
            }
            if (aux.equals(cadena)) {
                //server  ** IMAGEN **
                System.out.println("Servidor >> Enviando Imagen");
                out = new byte[tambytes];
                bytes = bin.read(out, 0, out.length);
                while (bytes > 0) {
                    dout.write(out, 0, bytes);
                    dout.flush();
                    bytes = bin.read(out, 0, out.length);
                    sleep(50);
                }
                System.out.println("Servidor >> Imagen Enviada");
            }
            //client ** FIN **
            String exit = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                exit = new String(in, 0, bytes, StandardCharsets.UTF_8);
                System.out.println("Cliente >> " + exit);
            }

            dout.close();
            din.close();
            bin.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
