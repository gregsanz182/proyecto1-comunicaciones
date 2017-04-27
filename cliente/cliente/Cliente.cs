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
        byte[] bufferIn;
        byte[] bufferOut;
        long tamano;
        String nomFichero;
        String nombre;
        String dirServer;
        String cad;

        public Cliente()
        {
            sCliente = null;
            sStream = null;
            fs = null;
            bw = null;
            bufferIn = new byte[1024];

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
                sCliente.Connect("localhost", 5000);
            }
            catch (SocketException e)
            {
                Console.WriteLine(e.Message);
                return;
            }

            Console.WriteLine(" Conectado al servidor.");
            sStream = sCliente.GetStream();

            enviarInfo();
            recibirInfo();
            aceptarTransmision();
            abrirFichero();
            recibir();
            cerrarConexion();
        }

        void enviarInfo()
        {
            try
            {
                bufferOut = Encoding.UTF8.GetBytes("192.168.0.1?Gregory");
                sStream.Flush();
                sStream.Write(bufferOut, 0, bufferOut.Length);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        void recibirInfo()
        {
            try
            {
                String[] cadena;
                int bytes;
                bytes = sStream.Read(bufferIn, 0, bufferIn.Length);
                cad = Encoding.UTF8.GetString(bufferIn, 0, bytes);
                cadena = cad.Split('?');
                nomFichero = cadena[0];
                tamano = Int64.Parse(cadena[1]);
            }
            catch (IOException e)
            {
                Console.WriteLine(e.ToString());
            }
            Console.WriteLine(nomFichero + " " + tamano);
        }

        void aceptarTransmision()
        {
            try
            {
                sStream.Flush();
                bufferOut = Encoding.UTF8.GetBytes(cad);
                sStream.Write(bufferOut, 0, bufferOut.Length);
                sStream.Flush();
            }
            catch (IOException e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        void abrirFichero()
        {
            fs = new FileStream(nomFichero, FileMode.Create);
            bw = new BinaryWriter(fs);
        }

        void recibir()
        {
            long total = 0;
            int bytes;
            try
            {
                while (total < tamano)
                {
                    bytes = sStream.Read(bufferIn, 0, bufferIn.Length);
                    Console.WriteLine("aqui");
                    if (bytes > 0)
                    {
                        bw.Write(bufferIn, 0, bytes);
                        total += bytes;
                    }
                }
                sStream.Flush();
            }
            catch (IOException e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        void cerrarConexion()
        {
            bw.Close();
            fs.Close();
            sStream.Close();
            sCliente.Close();
        }
    }
}
