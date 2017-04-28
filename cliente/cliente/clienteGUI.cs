using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace cliente
{
    public partial class clienteGUI : Form
    {
        String clienteIP;
        String nombrePC;
        String serverIP;
        String nomFichero;
        long tamano;
        private Thread hilo;
        TcpClient sCliente;
        NetworkStream sStream;
        FileStream fs;
        BinaryWriter bw;
        byte[] bufferIn;
        byte[] bufferOut;

        private const int PREPARANDO = 0;
        private const int LISTO = 1;
        private const int CONECTANDO = 2;
        private const int ERROR = 3;
        private const int DESCARGANDO = 4;
        private const int FINAL = 5;
        private const int ESPERANDO = 6;
        private const int IDENTIFICANDO = 7;
        private const int DESCARGADO = 8;

        String[] formatosImagen;

        public clienteGUI()
        {
            InitializeComponent();
            inicializarFormatos();
            this.botonConectar.Click += botonConectar_Click;
            hilo = new Thread(inicializarInfoCliente);
            hilo.Start();
            sCliente = null;
            sStream = null;
            fs = null;
            bw = null;
            bufferIn = new byte[1048576];
        }

        private void inicializarFormatos()
        {
            formatosImagen = new String[5];
            formatosImagen[0] = ".jpg";
            formatosImagen[1] = ".jpeg";
            formatosImagen[2] = ".gif";
            formatosImagen[3] = ".png";
            formatosImagen[4] = ".bmp";
        }

        private void inicializarInfoCliente()
        {
            clienteIP = hallarDireccionIP();
            nombrePC = Environment.MachineName;
            this.Invoke(new MethodInvoker(delegate { mensajeLog("Preparando cliente"); }));
            Thread.Sleep(1000);
            this.Invoke(new MethodInvoker(delegate { preparar(); }));
        }

        void preparar()
        {
            this.labelTextLocalIP.Text = clienteIP;
            this.labelTextMaquina.Text = nombrePC;
            this.textIP.Enabled = true;
            this.botonConectar.Enabled = true;
            mensajeLog("Listo");
            estadoFoot(LISTO);
        }

        String hallarDireccionIP()
        {
            var host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (var ip in host.AddressList)
            {
                if (ip.AddressFamily == AddressFamily.InterNetwork)
                {
                    return ip.ToString();
                }
            }
            return "0.0.0.0";
        }

        void conectar()
        {
            if (imagen.Image != null)
            {
                imagen.Image.Dispose();
                imagen.Image = null;
            }
            String cad;
            bloquearIn();
            estadoFoot(CONECTANDO);
            serverIP = this.textIP.Text;
            mensajeLog("Buscando servidor en " + serverIP);
            sCliente = new TcpClient();
            try
            {
                sCliente.Connect(serverIP, 5000);
            }
            catch (SocketException e)
            {
                estadoFoot(ERROR);
                mensajeLog(e.Message);
                sCliente.Close();
                MessageBox.Show(e.Message, "Ocurrio un error al realizar la conexión.", MessageBoxButtons.OK, MessageBoxIcon.Error);
                estadoFoot(LISTO);
                habilitarIn();
                return;
            }

            mensajeLog("Conectado a "+serverIP);
            sStream = sCliente.GetStream();
            estadoFoot(IDENTIFICANDO);

            try
            {
                //enviar informacion del cliente
                mensajeLog("Enviando identificación del cliente");
                mensajeLog("Nombre: " + nombrePC + "\r\n              Direccion Local: " + clienteIP);
                enviarInfoCliente();
                mensajeLog("Identificación enviada");

                //recibir informacion del archivo (imagen)
                cad = obtenerInfoFichero();
                mensajeLog("Nombre: " + nomFichero + "\r\n              Tamaño: " + tamano);

                //aceptar transmision
                aceptarTransmision(cad);
                mensajeLog("Transmision aceptada");
                estadoFoot(ESPERANDO);
                mensajeLog("Esperando a que el servidor comience la transmisión");

                //recibir archivo
                recibir();
                cerrarFichero();

                //cerrando conexion
                mensajeLog("Cerrando conexión");
                cerrarConexion();
                mensajeLog("Conexión finalizada correctamente");
                mostrarImagen();

                mensajeLog("Listo");
                estadoFoot(LISTO);
                habilitarIn();
            }
            catch (IOException e)
            {
                estadoFoot(ERROR);
                mensajeLog(e.Message);
                MessageBox.Show(e.ToString(), e.Message, MessageBoxButtons.OK, MessageBoxIcon.Error);
                sCliente.Close();
                sStream.Close();
                if (fs != null)
                {
                    fs.Close();
                }
                if (bw != null)
                {
                    bw.Close();
                }
                estadoFoot(LISTO);
                habilitarIn();
                return;
            }

            

        }

        void enviarInfoCliente()
        {
            bufferOut = Encoding.UTF8.GetBytes(clienteIP + "?" + nombrePC);
            sStream.Flush();
            sStream.Write(bufferOut, 0, bufferOut.Length);
        }

        String obtenerInfoFichero()
        {
            String[] cadena;
            String cad;
            int bytes;
            bytes = sStream.Read(bufferIn, 0, bufferIn.Length);
            cad = Encoding.UTF8.GetString(bufferIn, 0, bytes);
            cadena = cad.Split('?');
            nomFichero = cadena[0];
            tamano = Int64.Parse(cadena[1]);
            return cad;
        }

        void aceptarTransmision(String cad)
        {
            sStream.Flush();
            bufferOut = Encoding.UTF8.GetBytes(cad);
            sStream.Write(bufferOut, 0, bufferOut.Length);
            sStream.Flush();
        }

        void recibir()
        {
            long total = 0;
            int bytes;
            bool first = true;
            while (total < tamano)
            {
                bytes = sStream.Read(bufferIn, 0, bufferIn.Length);
                if (bytes > 0)
                {
                    if (first)
                    {
                        mensajeLog("Comienza la transmisión");
                        abrirFichero();
                        estadoFoot(DESCARGANDO);
                        first = false;
                    }
                    bw.Write(bufferIn, 0, bytes);
                    total += bytes;
                }
            }
            mensajeLog("Imagen recibida satisfactoriamente");
            estadoFoot(DESCARGADO);
            MessageBox.Show("Imagen recibida satisfactoriamente", "Exito!!!", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
            sStream.Flush();
        }

        void cerrarConexion()
        {
            sStream.Flush();
            bufferOut = Encoding.UTF8.GetBytes("fin");
            sStream.Write(bufferOut, 0, bufferOut.Length);
            sStream.Flush();
            sStream.Close();
            sCliente.Close();
        }

        void estadoFoot(int tipo)
        {
            switch (tipo)
            {
                case PREPARANDO:
                    this.panel1.BackColor = Color.FromArgb(202, 81, 0);
                    this.label1.Text = "Preparando...";
                    break;
                case LISTO:
                    this.panel1.BackColor = Color.FromArgb(0, 122, 204);
                    this.label1.Text = "Listo";
                    break;
                case ERROR:
                    this.panel1.BackColor = Color.Red;
                    this.label1.Text = "Error";
                    break;
                case CONECTANDO:
                    this.panel1.BackColor = Color.FromArgb(104, 33, 122);
                    this.label1.Text = "Conectando...";
                    break;
                case DESCARGANDO:
                    this.panel1.BackColor = Color.FromArgb(54, 134, 50);
                    this.label1.Text = "Recibiendo imagen...";
                    break;
                case ESPERANDO:
                    this.panel1.BackColor = Color.FromArgb(202, 81, 0);
                    this.label1.Text = "Esperando transmisión...";
                    break;
                case IDENTIFICANDO:
                    this.panel1.BackColor = Color.FromArgb(104, 33, 122);
                    this.label1.Text = "Identificandose...";
                    break;
                case DESCARGADO:
                    this.panel1.BackColor = Color.FromArgb(54, 134, 50);
                    this.label1.Text = "Descarga satisfactoria";
                    break;
            }
            this.Refresh();
        }

        void bloquearIn()
        {
            this.textIP.Enabled = false;
            this.botonConectar.Enabled = false;
        }

        void habilitarIn()
        {
            this.textIP.Enabled = true;
            this.botonConectar.Enabled = true;
        }

        void abrirFichero()
        {
            fs = new FileStream(nomFichero, FileMode.Create);
            bw = new BinaryWriter(fs);
        }

        void cerrarFichero()
        {
            bw.Close();
            fs.Close();
        }

        void mensajeLog(String cadena)
        {
            this.textBox2.AppendText(DateTime.Now.ToString("HH:mm:ss") + " >> " + cadena + "\r\n\r\n");
        }

        void mostrarImagen()
        {
            bool flag = false;
            foreach (String format in formatosImagen){
                if(nomFichero.ToLower().EndsWith(format)){
                    flag = true;
                }
            }
            if (flag)
            {
                Image img = Image.FromFile(nomFichero);
                imagen.Image = img;
                if (img.Width > imagen.Width || img.Height > imagen.Height)
                    imagen.SizeMode = PictureBoxSizeMode.Zoom;
                else
                    imagen.SizeMode = PictureBoxSizeMode.CenterImage;
                mensajeLog("Imagen renderizada correctamente");
            }
            else
            {
                mensajeLog("No se puede abrir imagen. Formato Inválido");
                MessageBox.Show("El archivo recibido no es una imagen válida", "Formato inválido", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        void ventanaCerrada(object sender, System.Windows.Forms.FormClosedEventArgs e)
        {
            hilo.Abort();
        }

        void botonConectar_Click(object sender, EventArgs e)
        {
            conectar();
        }

        private void textIP_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click_1(object sender, EventArgs e)
        {

        }

        private void label1_Click_2(object sender, EventArgs e)
        {

        }

        private void labelTextLocalIP_Click(object sender, EventArgs e)
        {

        }

        private void richTextBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void richTextBox1_TextChanged_1(object sender, EventArgs e)
        {

        }

        private void maskedTextBox1_MaskInputRejected(object sender, MaskInputRejectedEventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged_1(object sender, EventArgs e)
        {

        }
    }
}
