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
    int puerto, puertoResp;

    byte[] out, in;
    File f;
    int bytes, width, height, x, y, w, h;
    String ip, nombre, cadena, mensajeUdp, mensajeUdpRespuesta;
    StringTokenizer tokens;
    static final int tambytes = 1048576; // 1Mb
    //--------------------------------------------
    JLabel text1, ipclient, nameclient, contImagen, dirImagen, pesoImagen, msjentrega, fin;
    JPanel ventana, cabecera, panelImagen, sombra;
    ImageIcon imagen;
    Icon icon;
    JButton enviar;

    public Servidor(File file) throws InterruptedException, UnknownHostException {
        server = null;
        socket = null;
        tokens = null;
        cadena = "";
        width = 700;
        height = 500;
        f = file;
        mensajeUdp = "mabel mabel mabel ma ma ma mabel mabel";
        mensajeUdpRespuesta = "dipper";
        puerto = 3000;
        puertoResp = 5555;
        buffer = new byte[1000];

        try {
            socketUDP = new DatagramSocket(null); //se instancia el Socket para la comunicación UDP
            socketUDP.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), puerto)); // se vincula el Socket con la direccion y el puerto de escucha
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        inicializaComponentes(); // Parte Gráfica
        paquete = new DatagramPacket(buffer, buffer.length); // se instancia el Datagrama que se enviará
        try {
            do {
                cadena = "";
                socketUDP.receive(paquete); // el Socket se queda escuchando hasta que reciba algún mensaje
                tokens = new StringTokenizer(new String(paquete.getData(), StandardCharsets.UTF_8));
                cadena = tokens.nextToken("?");
                sleep(250);
                System.out.println(paquete.getAddress().toString()); // dirección de origen
                System.out.println(cadena); // mensaje del paquete
            } while (!cadena.equals(mensajeUdp));
        } catch (IOException ex) {
            System.out.println("Socket: " + ex.getMessage());
        }
        System.out.println("Cliente que pide conexion: " + paquete.getAddress().toString().replaceAll("/", ""));
        System.out.println("Con este mensaje: " + cadena);
        ip = paquete.getAddress().toString().replaceAll("/", "");
        buffer = mensajeUdpRespuesta.getBytes(StandardCharsets.UTF_8);
        paquete = new DatagramPacket(buffer, buffer.length, paquete.getAddress(), puertoResp); // se instancia el datagrama con el mensaje (buffer), el tamaño del mensaje, la ip del cliente (destino) y el puerto de respuesta
        sleep(2000);
        try {
            socketUDP.send(paquete); // el socket envia el datagrama que se creó previamente
        } catch (IOException ex) {
            System.out.println("Socket: " + ex.getMessage());
        }
        socketUDP.close(); // se cierra el socket UDP, para liberar el puerto
        try {
            server = new ServerSocket(puerto); // se instancia el ServerSocket TCP
        } catch (IOException ex) {
            System.out.println("Server: " + ex.getMessage());
        }
        socket = new Socket(); // se instancia el Socket TCP
        try {
            socket = server.accept(); // el Socket se queda esperando que el cliente se conecte
            dout = new DataOutputStream(socket.getOutputStream()); // se crean las salidas y entradas para la transferencia de datos
            din = new DataInputStream(socket.getInputStream());
            bin = new BufferedInputStream(new FileInputStream(f));
            text1.setText("Cliente conectado...");
        } catch (IOException ex) {
            System.out.println("Server: " + ex.getMessage());
        }
        try {
            //client  ** IP - NOMBRE **
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length); // se recibe la información que el cliente envía
            if (bytes > 0) { // se codifica
                nombre = new String(in, 0, bytes, StandardCharsets.UTF_8);
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
            bytes = bin.read(out, 0, out.length); // se lee el archivo (Imagen) y se guarda en la variable tipo byte
            while (bytes > 0) {
                dout.write(out, 0, bytes); // se escribe o envía la imagen (que se guardo en out) a medida que se lee del archivo
                dout.flush();
                bytes = bin.read(out, 0, out.length); // se sigue leyendo del vector de bytes para asegurar que se envíe completo
                sleep(50);
            }
            msjentrega.setVisible(true);
            //client ** FIN **
            String exit = "";
            in = new byte[tambytes];
            bytes = din.read(in, 0, in.length); // se lee lo que el cliente envía
            if (bytes > 0) {
                exit = new String(in, 0, bytes, StandardCharsets.UTF_8); // se codifica el mensaje entrante
            }
            if (exit.equals("fin")) { // se compara con el mensaje de finalización
                fin.setVisible(true);
                dout.close(); // se cierran las salidas y entradas de datos y el Socket
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
