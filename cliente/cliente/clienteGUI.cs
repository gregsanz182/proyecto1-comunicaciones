using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
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
        private Color[] colores;

        public clienteGUI()
        {
            InitializeComponent();
            iniThemeColors();
            ejecutar();
        }

        public void ejecutar()
        {
            inicializarInfoCliente();
        }

        void iniThemeColors(){
            colores = new Color[3];
            colores[0] = Color.FromArgb(202, 81, 0);
            colores[1] = Color.FromArgb(0, 122, 204);
        }

        private void inicializarInfoCliente()
        {
            clienteIP = hallarDireccionIP();
            this.labelTextLocalIP.Text = clienteIP;
            nombrePC = Environment.MachineName;
            this.labelTextMaquina.Text = nombrePC;
            this.textBox1.Enabled = true;
            this.botonConectar.Enabled = true;
            this.panel1.BackColor = colores[1];
            this.label1.Text = "Listo";
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

        private void label1_Click(object sender, EventArgs e)
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
    }
}
