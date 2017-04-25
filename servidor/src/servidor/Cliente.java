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
import java.util.logging.*;

/**
 *
 * @author Anny Chacon
 */
public class Cliente {

    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    BufferedOutputStream bufout;
    BufferedInputStream bufin;
    String nombre;
    byte[] in, out;
    int bytes;
    File f;

    public Cliente() {
        socket = null;
        nombre = "Anny Chacon";
        f = new File("C:/Users/Anny Chacon/Documents/NetBeansProjects/Servidor/imagen.jpg");
        try {
            socket = new Socket("localhost", 5000);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            bufout = new BufferedOutputStream(new FileOutputStream(f));
            bufin = new BufferedInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //client
            System.out.println("Cliente: " + socket.getInetAddress() + " - " + nombre);
            String ip = socket.getInetAddress() + " - " + nombre;
            out = new byte[1024];
            out = ip.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();
            out = null;
            //server
            in = new byte[512 * 1024];
            bytes = bufin.read(in, 0, in.length);
            if (bytes > 0) {
                bufout.write(in, 0, bytes);
            }
            //client
            String bye = "Adios";
            out = new byte[1024];
            out = bye.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();
            sleep(5);

            dout.close();
            din.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
