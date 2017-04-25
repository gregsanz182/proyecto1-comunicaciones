/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
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
    BufferedOutputStream bufout;
    BufferedInputStream bufin;
    byte[] out, in;
    File f;

    public Servidor() {
        server = null;
        socket = null;
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
            bufin = new BufferedInputStream(new FileInputStream(f));
            bufout = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //client
            in = new byte[1024];
            int bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                System.out.println("Cliente: " + new String(in, 0, bytes, StandardCharsets.UTF_8));
            }
            in = null;
            //server
            out = new byte[524288]; // 512 kb
            bytes = bufin.read(out, 0, out.length);
            if (bytes > 0) {
                bufout.write(out, 0, bytes);
                bufout.flush();
            }
            //client
            String exit="";
            in = new byte[1024];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                exit = new String(in, 0, bytes, StandardCharsets.UTF_8);
                System.out.println("Cliente: " + exit);
            }
            
            dout.close();
            din.close();
            bufin.close();
            bufout.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
