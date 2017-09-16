/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author Anny Chacon
 */
public class Servidor extends JFrame {

    ServerSocket server;
    Socket socket;
    DataOutputStream dout;
    DataInputStream din;
    BufferedInputStream bin;

    DatagramSocket socketUDP;
    DatagramPacket paquete;
    byte[] buffer;
    int puerto;
    int puertoResp;

    byte[] out, in;
    File f;
    int bytes, width, height, x, y, w, h;
    String ip, nombre, cadena;
    String mensajeUdp;
    String mensajeUdpRespuesta;
    StringTokenizer tokens;
    static final int tambytes = 1048576; // 1Mb
    //--------------------------------------------
    JLabel text1, ipclient, nameclient, contImagen, dirImagen, pesoImagen, msjentrega, fin;
    JPanel ventana, cabecera, panelImagen, sombra;
    ImageIcon imagen;
    Icon icon;
    JButton enviar;

    public Servidor(File a) throws InterruptedException {
        server = null;
        socket = null;
        tokens = null;
        cadena = "";
        width = 700;
        height = 500;
        f = a;
        mensajeUdp = "mabel mabel mabel ma ma ma mabel mabel";
        mensajeUdpRespuesta = "dipper";

        socketUDP = null;
        puerto = 5000;
        puertoResp = 5001;
        buffer = new byte[1000];

        try {
            socketUDP = new DatagramSocket(puerto);
        } catch (SocketException ex) {
            System.out.println("socket: " + ex.getMessage());
        }
        inicializaComponentes();
        paquete = new DatagramPacket(buffer, buffer.length);
        String mensaje = "";
        try {
            do{
                socketUDP.receive(paquete);
                mensaje = new String(paquete.getData(), StandardCharsets.UTF_8);
                sleep(250);
            }while(mensaje.equals(mensajeUdp));
        } catch (IOException ex) {
            System.out.println("Socket: " + ex.getMessage());
        }
        System.out.println("Cliente que pide conexion: " + paquete.getAddress().toString().replaceAll("/", ""));
        System.out.println("Con este mensaje: " + mensaje);
        ip = paquete.getAddress().toString().replaceAll("/", "");
        buffer = mensajeUdpRespuesta.getBytes(StandardCharsets.UTF_8);
        paquete = new DatagramPacket(buffer, buffer.length, paquete.getAddress(), puertoResp);
        sleep(3000);
        try {
            socketUDP.send(paquete);
        } catch (IOException ex) {
            System.out.println("Socket: " + ex.getMessage());
        }
        socketUDP.close();
        try {
            server = new ServerSocket(puerto);
        } catch (IOException ex) {
            System.out.println("Server: " + ex.getMessage());
        }
        socket = new Socket();
        try {
            socket = server.accept();
            dout = new DataOutputStream(socket.getOutputStream());
            din = new DataInputStream(socket.getInputStream());
            bin = new BufferedInputStream(new FileInputStream(f));
            text1.setText("Cliente conectado...");
        } catch (IOException ex) {
            System.out.println("Server: " + ex.getMessage());
        }
        try {
            //client  ** IP - NOMBRE **
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                cadena = new String(in, 0, bytes, StandardCharsets.UTF_8);
                tokens = new StringTokenizer(cadena, "?\n");
                nombre = tokens.nextToken();
                ipclient.setText(ipclient.getText() + " " + ip);
                nameclient.setText(nameclient.getText() + " " + nombre);
                ipclient.setVisible(true);
                nameclient.setVisible(true);
            }
            //server  ** NOMBRE ARCHIVO - PESO **
            out = new byte[tambytes];
            cadena = f.getName() + "?" + f.length() + "\n";
            out = cadena.getBytes(StandardCharsets.UTF_8);
            dout.write(out, 0, out.length);
            dout.flush();

            //client ** VERIFICACION - NOMBRE - PESO **
            String aux = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                aux = new String(in, 0, bytes, StandardCharsets.UTF_8);
            }
            if (aux.equals(cadena)) {
                enviar.setEnabled(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaComponentes() {
        setTitle("Servidor");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //--------------------------------------------------------------
        imagen = new ImageIcon(f.getAbsolutePath(), "Imagen a enviar");
        int scaleW = 300, scaleH = 300;
        y = x = h = 20;
        if (imagen.getIconWidth() <= 300 && imagen.getIconHeight() <= 300) {
            scaleW = imagen.getIconWidth();
            scaleH = imagen.getIconHeight();
            width = 460 + scaleW;
            height = 220 + scaleH;
            if (scaleH < 180) {
                height += (180 - scaleH);
            }
        } else {
            if (imagen.getIconHeight() > imagen.getIconWidth()) {
                scaleW = (imagen.getIconWidth() * scaleH) / imagen.getIconHeight();
            } else {
                scaleH = (imagen.getIconHeight() * scaleW) / imagen.getIconWidth();
            }
            if (imagen.getIconWidth() > imagen.getIconHeight()) {
                width = 760;
                height = 220 + scaleH;
            } else {
                height = 520;
                width = 460 + scaleW;
            }
        }
        //----------------------PRINCIPAL-------------------------------
        ventana = new JPanel();
        ventana.setLayout(null);
        ventana.setPreferredSize(new Dimension(width, height));
        ventana.setBackground(Color.decode("#FFFFFF"));
        //----------------------CABECERA--------------------------------
        cabecera = new JPanel();
        cabecera.setLayout(null);
        cabecera.setBounds(x, y, width - 40, 120);
        cabecera.setBackground(Color.decode("#FFFFFF"));
        cabecera.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //--------------------------------------------------------------
        w = (cabecera.getWidth() / 2) - 40;
        text1 = new JLabel("Esperando conexion del cliente...");
        text1.setBounds(x, y, w, h);
        text1.setVisible(true);
        //--------------------------------------------------------------
        ipclient = new JLabel("IP cliente: ");
        ipclient.setBounds(x, y + 30, w, h);
        ipclient.setVisible(false);
        //--------------------------------------------------------------
        nameclient = new JLabel("Nombre cliente: ");
        nameclient.setBounds(x, y + 60, w, h);
        nameclient.setVisible(false);
        //--------------------------------------------------------------
        x = (cabecera.getWidth() / 2) + 20;
        msjentrega = new JLabel("Imagen Entregada...");
        msjentrega.setBounds(x, y, w, h);
        msjentrega.setVisible(false);
        //--------------------------------------------------------------
        fin = new JLabel("Fin de la transmicion...");
        fin.setBounds(x, y + 30, w, h);
        fin.setVisible(false);
        //------------------------IMAGEN--------------------------------
        x = y = 20;
        panelImagen = new JPanel();
        panelImagen.setLayout(null);
        panelImagen.setBounds(x, 160, width - 40, height - 180);
        panelImagen.setBackground(Color.decode("#FFFFFF"));
        panelImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //--------------------------------------------------------------
        contImagen = new JLabel();
        contImagen.setVisible(true);
        contImagen.setBounds(x, y, scaleW, scaleH);
        //--------------------------------------------------------------
        icon = new ImageIcon(imagen.getImage().getScaledInstance(contImagen.getWidth(), contImagen.getHeight(), 1));
        contImagen.setIcon(icon);
        //--------------------------------------------------------------
        x = contImagen.getWidth() + 40;
        w = (panelImagen.getWidth() - x - 20);
        y = 40;
        dirImagen = new JLabel("Direccion de la Imagen: " + f.getAbsolutePath());
        dirImagen.setBounds(x, y, w, h);
        dirImagen.setVisible(true);
        //--------------------------------------------------------------
        pesoImagen = new JLabel("Peso de la imagen: " + f.length() / 1024 + " Kb");
        pesoImagen.setBounds(x, y + 30, w, 20);
        pesoImagen.setVisible(true);
        //--------------------------------------------------------------
        x = 40 + contImagen.getWidth() + 150;
        enviar = new JButton("Enviar");
        enviar.setBounds((contImagen.getWidth() + 170), panelImagen.getHeight() - 100, 100, 40);
        enviar.setEnabled(false);
        enviar.setVisible(true);
        enviar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enviarArchivo();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        cabecera.add(text1);
        cabecera.add(ipclient);
        cabecera.add(nameclient);
        cabecera.add(msjentrega);
        cabecera.add(fin);

        panelImagen.add(contImagen);
        panelImagen.add(dirImagen);
        panelImagen.add(pesoImagen);
        panelImagen.add(enviar);

        ventana.add(cabecera);
        ventana.add(panelImagen);

        getContentPane().add(ventana);
        pack();
        setLocationRelativeTo(null);
        validate();
        repaint();
    }

    private void enviarArchivo() {
        enviar.setEnabled(false);
        try {
            //server  ** IMAGEN **
            out = new byte[tambytes];
            bytes = bin.read(out, 0, out.length);
            while (bytes > 0) {
                dout.write(out, 0, bytes);
                dout.flush();
                bytes = bin.read(out, 0, out.length);
                sleep(50);
            }
            msjentrega.setVisible(true);
            //client ** FIN **
            String exit = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                exit = new String(in, 0, bytes, StandardCharsets.UTF_8);
            }
            if (exit.equals("fin")) {
                fin.setVisible(true);
                dout.close();
                din.close();
                bin.close();
                socket.close();
                text1.setText("Cliente Desconectado...");
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
