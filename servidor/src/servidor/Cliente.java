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
    BufferedInputStream bin;
    BufferedOutputStream bout;
    String nombre, cadena, nomf;
    int tamf;
    StringTokenizer tokens;
    byte[] in, out;
    int bytes;
    File f;

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
            bin = new BufferedInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //client
            System.out.println("Cliente >> IP: " + socket.getInetAddress() + " Nombre: " + nombre);
            cadena = socket.getInetAddress() + "?" + nombre + "\n";
            out = new byte[1024];
            out = cadena.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();
            out = null;
            //server
            in = new byte[1024];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                cadena = new String(in, 0, bytes, StandardCharsets.UTF_8);
                tokens = new StringTokenizer(cadena, "?\n");
                nomf = tokens.nextToken();
                tamf = Integer.parseInt(tokens.nextToken());
            }
            System.out.println("Servidor >> Nombre: " + nomf + " Tamaño: " + tamf);
            System.out.println("Cadena: " + cadena);
            f = new File("C:/Users/Anny Chacon/Desktop/proyecto1-comunicaciones/servidor/" + nomf);
            bout = new BufferedOutputStream(new FileOutputStream(f));
            //cliente

            //server
            in = new byte[tamf];
            bytes = bin.read(in, 0, tamf);
            System.out.println("bytes  -->>> " + bytes + " Tamaño -->>> " + tamf);
            if (bytes > 0) {
                bout.write(in, 0, bytes);
                bout.flush();
            }
            //client
            String bye = "Adios";
            out = new byte[1024];
            out = bye.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();

            dout.close();
            din.close();
            bin.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
