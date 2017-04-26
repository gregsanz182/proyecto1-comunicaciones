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
public class Cliente {

    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    BufferedOutputStream bout;
    String nombre, cadena, nomf;
    int tamf;
    StringTokenizer tokens;
    byte[] in, out;
    int bytes;
    File f;
    static final int tambytes = 1048576; // 512 kb

    public Cliente() {
        socket = null;
        nombre = "Anny Chacon";
        cadena = nomf = "";
        tamf = 0;
        tokens = null;
        try {
            socket = new Socket("localhost", 5000);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //client  ** IP - NOMBRE **
            System.out.println("Cliente >> IP: " + socket.getInetAddress() + " Nombre: " + nombre);
            cadena = socket.getInetAddress() + "?" + nombre + "\n";
            out = new byte[1024];
            out = cadena.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();
            out = null;

            //server  ** NOMBRE - PESO **
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                cadena = new String(in, 0, bytes, StandardCharsets.UTF_8);
                tokens = new StringTokenizer(cadena, "?\n");
                nomf = tokens.nextToken();
                tamf = Integer.parseInt(tokens.nextToken());
            }
            System.out.println("Servidor >> Nombre: " + nomf + " Tamaño: " + tamf);
            f = new File("C:/Users/Anny Chacon/Desktop/proyecto1-comunicaciones/servidor/" + nomf);
            bout = new BufferedOutputStream(new FileOutputStream(f));

            //client  ** RESPUESTA **
            dout.write(in, 0, bytes);
            dout.flush();

            // server ** ARCHIVO **
            in = new byte[tambytes];
            int tam = 0;
            System.out.println("Cliente >> Recibiendo Imagen");
            while (tam < tamf) {
                bytes = din.read(in, 0, in.length);
                if (bytes > 0) {
                    bout.write(in, 0, bytes);
                    tam += bytes;
                    System.out.println("Cliente >> Bytes Recibidos  --> " + tam + " Recibiendo -->>> " + bytes);
                }
                bout.flush();
            }
            System.out.println("Cliente >> Imagen Recibida");
            //client ** FIN CONEXION **
            cadena = "FIN CONEXION";
            out = new byte[1024];
            out = cadena.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();
            System.out.println("Cliente >> " + cadena);

            dout.close();
            din.close();
            bout.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
