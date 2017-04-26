using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace cliente
{
    class Cliente
    {
        TcpClient sCliente;
        NetworkStream sStream;
        FileStream fs;
        BinaryWriter bw;
        byte[] buffer = new Byte[1024];
        long tamano;
        String nomFichero;
        String nombre;
        String dirServer;

        public Cliente()
        {
            sCliente = null;
            sStream = null;
            fs = null;
            bw = null;

            //solicitarDatos();
            realizarConexion();
            Console.ReadLine();
        }

        void solicitarDatos()
        {
            Console.Write("Ingrese nombre: \n>> ");
            nombre = Console.ReadLine();
            Console.Write("Ingrese direccion IP del servidor: \n>> ");
            dirServer = Console.ReadLine();
        }

        void realizarConexion(){
            sCliente = new TcpClient();
            try
            {
                sCliente.Connect("localhost", 8888);
            }
            catch (SocketException e)
            {
                Console.WriteLine(e.Message);
                return;
            }

            Console.WriteLine(" Conectado al servidor.");
            sStream = sCliente.GetStream();

            recibirInfo();
        }

        void recibirInfo()
        {
            try
            {
                String[] cadena;
                int bytes;
                bytes = sStream.Read(buffer, 0, buffer.Length);
                sStream.Flush();
                cadena = Encoding.UTF8.GetString(buffer, 0, bytes).Split('?');
                nomFichero = cadena[0];
                tamano = Int64.Parse(cadena[1]);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.ToString());
            }
            Console.WriteLine(nomFichero + " " + tamano);
        }
    }
}
