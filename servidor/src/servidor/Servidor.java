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
    BufferedOutputStream bout;
    BufferedInputStream bin;
    byte[] out, in, outf;
    File f;
    String ip, nombre, cadena;
    StringTokenizer tokens;
    static final int tambytes = 524288; // 512 kb

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
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket = new Socket();
        try {
            socket = server.accept();
            System.out.println("Cliente conectado...");
            dout = new DataOutputStream(socket.getOutputStream());
            din = new DataInputStream(socket.getInputStream());
            bout = new BufferedOutputStream(socket.getOutputStream());
            bin = new BufferedInputStream(new FileInputStream(f));
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //client  ** IP - NOMBRE **
            in = new byte[tambytes];
            int bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                cadena = new String(in, 0, bytes, StandardCharsets.UTF_8);
                tokens = new StringTokenizer(cadena, "?\n");
                ip = tokens.nextToken();
                nombre = tokens.nextToken();
            }
            System.out.println("Cliente >> IP: " + ip + " Nombre: " + nombre);
            in = null;

            //server  ** NOMBRE ARCHIVO - TAMAÑO **
            out = new byte[tambytes];
            cadena = f.getName() + "?" + f.length() + "\n";
            out = cadena.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            System.out.println("Servidor >> " + cadena);

            //server  ** IMAGEN **
            out = new byte[tambytes];
            bytes = bin.read(out, 0, out.length);
            System.out.println("bytes >>>> " + bytes + " outf -->>>> " + out.length);
            while (bytes > 0) {
                dout.write(out, 0, bytes);
                dout.flush();
                bytes = bin.read(out, 0, out.length);
                sleep(50);
            }
            //client
            String exit = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                exit = new String(in, 0, bytes, StandardCharsets.UTF_8);
                System.out.println("Cliente: " + exit);
            }

            dout.close();
            din.close();
            bout.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
