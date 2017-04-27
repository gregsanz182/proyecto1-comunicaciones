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
    byte[] out, in;
    File f;
    int bytes, width, height;
    String ip, nombre, cadena;
    StringTokenizer tokens;
    static final int tambytes = 1048576; // 1Mb
    //--------------------------------------------
    JLabel text1, ipclient, nameclient, contImagen, dirImagen, pesoImagen, msjentrega, fin, peso, direccion;
    JPanel ventana, cabecera, panelImagen;
    ImageIcon imagen;
    Icon icon;
    JButton enviar;

    public Servidor() {
        server = null;
        socket = null;
        tokens = null;
        cadena = "";
        width = 700;
        height = 500;
        f = new File("D:/Imagenes/fractals/5.jpg");
        try {
            server = new ServerSocket(5000);
            System.out.println("Servidor Listo... Esperando Cliente...");
            inicializaComponentes();
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
            text1.setText("Cliente conectado...");
            enviar.setEnabled(true);
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
                System.out.println("Cliente  >> IP: " + ip + " Nombre: " + nombre);
                ipclient.setText(ipclient.getText() + " " + ip);
                nameclient.setText(nameclient.getText() + " " + nombre);
                ipclient.setVisible(true);
                nameclient.setVisible(true);
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
        //----------------------PRINCIPAL-------------------------------
        ventana = new JPanel();
        ventana.setLayout(null);
        ventana.setPreferredSize(new Dimension(width, height));
        ventana.setBackground(Color.decode("#E4F7CA"));
        //----------------------CABECERA--------------------------------
        cabecera = new JPanel();
        cabecera.setLayout(null);
        cabecera.setBounds(20, 20, width - 40, 120);
        cabecera.setBackground(Color.decode("#E4F7CA"));
        cabecera.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        //--------------------------------------------------------------
        int w = (cabecera.getWidth() / 2) - 40, x = 20;
        text1 = new JLabel("Esperando conexion del cliente...");
        text1.setBounds(20, 20, w, 20);
        text1.setVisible(true);
        //--------------------------------------------------------------
        ipclient = new JLabel("IP cliente: ");
        ipclient.setBounds(x, 50, w, 20);
        ipclient.setVisible(false);
        //--------------------------------------------------------------
        nameclient = new JLabel("Nombre cliente: ");
        nameclient.setBounds(x, 80, w, 20);
        nameclient.setVisible(false);
        //--------------------------------------------------------------
        x = (cabecera.getWidth() / 2) + 20;
        msjentrega = new JLabel("Imagen Entregada...");
        msjentrega.setBounds(x, 20, w, 20);
        msjentrega.setVisible(false);
        //--------------------------------------------------------------
        fin = new JLabel("Fin de la transmicion...");
        fin.setBounds(x, 50, w, 20);
        fin.setVisible(false);
        //------------------------IMAGEN--------------------------------
        int y = (cabecera.getY() * 2) + cabecera.getHeight();
        panelImagen = new JPanel();
        panelImagen.setLayout(null);
        panelImagen.setBounds(20, y, width - 40, height - y - 20);
        panelImagen.setBackground(Color.decode("#E4F7CA"));
        panelImagen.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        //--------------------------------------------------------------
        imagen = new ImageIcon(f.getAbsolutePath(), "Imagen a enviar");
        //--------------------------------------------------------------
        contImagen = new JLabel();
        contImagen.setVisible(true);
        //--------------------------------------------------------------
        int scaleW = (int) (0.5 * (panelImagen.getWidth() - 40)), scaleH = (panelImagen.getHeight() - 40);
        System.out.println("ancho: " + scaleW + " alto: " + scaleH);
        y = x = 20;
        if (imagen.getIconHeight() > imagen.getIconWidth()) {
            scaleW = (imagen.getIconWidth() * scaleH) / imagen.getIconHeight();
            x = (panelImagen.getWidth() / 4) - (scaleW / 2);
        } else {
            scaleH = (imagen.getIconHeight() * scaleW) / imagen.getIconWidth();
            y = (panelImagen.getHeight() / 2) - (scaleH / 2);
        }
        contImagen.setBounds(x, y, scaleW, scaleH);
        System.out.println(contImagen.getBounds().toString());
        icon = new ImageIcon(imagen.getImage().getScaledInstance(contImagen.getWidth(), contImagen.getHeight(), 1));
        contImagen.setIcon(icon);
        x = (panelImagen.getWidth() / 2) + 20;
        w = (panelImagen.getWidth() - x - 20);
        //--------------------------------------------------------------
        dirImagen = new JLabel("Direccion de la Imagen: ");
        dirImagen.setBounds(x, 20, w, 20);
        dirImagen.setVisible(true);
        //--------------------------------------------------------------
        direccion = new JLabel(f.getAbsolutePath());
        direccion.setBounds(x, 60, w, 20);
        direccion.setBackground(Color.WHITE);
        direccion.setForeground(Color.red);
        direccion.setVisible(true);
        //--------------------------------------------------------------
        pesoImagen = new JLabel("Peso de la imagen: ");
        pesoImagen.setBounds(x, 100, w, 20);
        pesoImagen.setVisible(true);
        //--------------------------------------------------------------
        peso = new JLabel(f.length() / 1024 + " Kb");
        peso.setBounds(x, 140, w, 20);
        peso.setVisible(true);
        //--------------------------------------------------------------
        enviar = new JButton("Enviar");
        enviar.setBounds((panelImagen.getWidth() / 2) - 50, panelImagen.getHeight() - 65, 100, 35);
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
        panelImagen.add(direccion);
        panelImagen.add(pesoImagen);
        panelImagen.add(peso);
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
        try {
            enviar.setEnabled(false);
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
                System.out.println("Cliente  >> " + aux);
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
                msjentrega.setVisible(true);
            }
            //client ** FIN **
            String exit = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length);
            if (bytes > 0) {
                exit = new String(in, 0, bytes, StandardCharsets.UTF_8);
                System.out.println("Cliente  >> " + exit);
            }
            fin.setVisible(true);
            dout.close();
            din.close();
            bin.close();
            socket.close();
            text1.setText("Cliente Desconectado...");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
