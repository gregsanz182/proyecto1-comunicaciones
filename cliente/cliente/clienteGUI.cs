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
        private Thread hilo;
        TcpClient sCliente;
        NetworkStream sStream;
        byte[] bufferIn;
        byte[] bufferOut;

        private const int PREPARANDO = 0;
        private const int LISTO = 1;
        private const int CONECTANDO = 2;
        private const int ERROR = 3;
        private const int DESCARGANDO = 4;
        private const int FINAL = 5;
        private const int ESPERANDO = 6;

        public clienteGUI()
        {
            InitializeComponent();
            this.botonConectar.Click += botonConectar_Click;
            hilo = new Thread(inicializarInfoCliente);
            hilo.Start();
            sCliente = null;
            sStream = null;
            bufferIn = new byte[1048576];
        }

        private void inicializarInfoCliente()
        {
            clienteIP = hallarDireccionIP();
            nombrePC = Environment.MachineName;
            Thread.Sleep(1000);
            this.Invoke(new MethodInvoker(delegate { preparar(); }));
        }

        void preparar()
        {
            this.labelTextLocalIP.Text = clienteIP;
            this.labelTextMaquina.Text = nombrePC;
            this.textIP.Enabled = true;
            this.botonConectar.Enabled = true;
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
            serverIP = this.textIP.Text;
            this.label1.Text = "Conectando...";
            mensajeLog("Buscando servidor en " + serverIP);
            this.botonConectar.Enabled = false;
            sCliente = new TcpClient();
            try
            {
                sCliente.Connect(serverIP, 5000);
            }
            catch (SocketException e)
            {
                estadoFoot(ERROR);
                mensajeLog(e.Message);
                MessageBox.Show("Ocurrio un error al realizar la conexión.\nVerifique la dirección del servidor y vuelva a intentar", e.Message, MessageBoxButtons.OK, MessageBoxIcon.Error);
                estadoFoot(LISTO);
                this.botonConectar.Enabled = true;
                return;
            }

            mensajeLog("Conectado a "+serverIP);
            sStream = sCliente.GetStream();

            try
            {
                bufferOut = Encoding.UTF8.GetBytes(clienteIP + "?" + nombrePC);
                mensajeLog("Enviando identificación del cliente");
                mensajeLog("Nombre: "+nombrePC+"\r\n              Direccion Local: "+clienteIP);
                sStream.Flush();
                sStream.Write(bufferOut, 0, bufferOut.Length);
                mensajeLog("Identificación enviada");
            }
            catch (IOException e)
            {
                estadoFoot(ERROR);
                mensajeLog(e.Message);
                MessageBox.Show(e.ToString(), e.Message, MessageBoxButtons.OK, MessageBoxIcon.Error);
                estadoFoot(LISTO);
                this.botonConectar.Enabled = true;
                return;
            }

            

        }

        void cerrarConexion()
        {
            
        }

        void estadoFoot(int tipo)
        {
            switch (tipo)
            {
                case PREPARANDO:
                    this.label1.Text = "Preparando...";
                    this.panel1.BackColor = Color.FromArgb(202, 81, 0);
                    break;
                case LISTO:
                    this.label1.Text = "Listo";
                    this.panel1.BackColor = Color.FromArgb(0, 122, 204);
                    break;
                case ERROR:
                    this.label1.Text = "Error";
                    this.panel1.BackColor = Color.Red;
                    break;
                case CONECTANDO:
                    this.label1.Text = "Conectando...";
                    this.panel1.BackColor = Color.FromArgb(104, 33, 122);
                    break;
                case DESCARGANDO:
                    this.label1.Text = "Recibiendo...";
                    this.panel1.BackColor = Color.FromArgb(21, 194, 60);
                    break;
                case ESPERANDO:
                    this.label1.Text = "Esperando transmisión...";
                    this.panel1.BackColor = Color.FromArgb(21, 194, 60);
                    break;
            }
        }

        void mensajeLog(String cadena)
        {
            this.textBox2.Text += DateTime.Now.ToString("t", CultureInfo.CreateSpecificCulture("hr-HR")) + " >> " + cadena + "\r\n\r\n";
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
    }
}
