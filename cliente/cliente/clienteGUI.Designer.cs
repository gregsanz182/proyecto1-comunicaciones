using System;
using System.Drawing;
using System.Windows.Forms;
namespace cliente
{
    partial class clienteGUI
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.labelServerIP = new System.Windows.Forms.Label();
            this.textIP = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.labelTextMaquina = new System.Windows.Forms.Label();
            this.labelLocalIP = new System.Windows.Forms.Label();
            this.labelMaquina = new System.Windows.Forms.Label();
            this.labelTextLocalIP = new System.Windows.Forms.Label();
            this.botonConectar = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.vistPrevPan = new System.Windows.Forms.GroupBox();
            this.imagen = new System.Windows.Forms.PictureBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.groupBox1.SuspendLayout();
            this.panel1.SuspendLayout();
            this.vistPrevPan.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.imagen)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelServerIP
            // 
            this.labelServerIP.AutoSize = true;
            this.labelServerIP.Location = new System.Drawing.Point(5, 21);
            this.labelServerIP.Name = "labelServerIP";
            this.labelServerIP.Size = new System.Drawing.Size(125, 13);
            this.labelServerIP.TabIndex = 0;
            this.labelServerIP.Text = "Dirección IP del servidor:";
            this.labelServerIP.Click += new System.EventHandler(this.textIP_Click);
            // 
            // textIP
            // 
            this.textIP.Enabled = false;
            this.textIP.Location = new System.Drawing.Point(8, 37);
            this.textIP.MaxLength = 15;
            this.textIP.Name = "textIP";
            this.textIP.Size = new System.Drawing.Size(294, 20);
            this.textIP.TabIndex = 1;
            this.textIP.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.labelTextMaquina);
            this.groupBox1.Controls.Add(this.labelLocalIP);
            this.groupBox1.Controls.Add(this.labelMaquina);
            this.groupBox1.Controls.Add(this.labelTextLocalIP);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(308, 86);
            this.groupBox1.TabIndex = 2;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Detalles del Cliente";
            // 
            // labelTextMaquina
            // 
            this.labelTextMaquina.AutoSize = true;
            this.labelTextMaquina.Location = new System.Drawing.Point(142, 51);
            this.labelTextMaquina.Name = "labelTextMaquina";
            this.labelTextMaquina.Size = new System.Drawing.Size(55, 13);
            this.labelTextMaquina.TabIndex = 3;
            this.labelTextMaquina.Text = "NAME-PC";
            // 
            // labelLocalIP
            // 
            this.labelLocalIP.AutoSize = true;
            this.labelLocalIP.Location = new System.Drawing.Point(26, 28);
            this.labelLocalIP.Name = "labelLocalIP";
            this.labelLocalIP.Size = new System.Drawing.Size(93, 13);
            this.labelLocalIP.TabIndex = 0;
            this.labelLocalIP.Text = "Direccion IP local:";
            this.labelLocalIP.Click += new System.EventHandler(this.label2_Click);
            // 
            // labelMaquina
            // 
            this.labelMaquina.AutoSize = true;
            this.labelMaquina.Location = new System.Drawing.Point(26, 51);
            this.labelMaquina.Name = "labelMaquina";
            this.labelMaquina.Size = new System.Drawing.Size(116, 13);
            this.labelMaquina.TabIndex = 2;
            this.labelMaquina.Text = "Nombre de la maquina:";
            // 
            // labelTextLocalIP
            // 
            this.labelTextLocalIP.AutoSize = true;
            this.labelTextLocalIP.Location = new System.Drawing.Point(122, 28);
            this.labelTextLocalIP.Name = "labelTextLocalIP";
            this.labelTextLocalIP.Size = new System.Drawing.Size(40, 13);
            this.labelTextLocalIP.TabIndex = 1;
            this.labelTextLocalIP.Text = "0.0.0.0";
            this.labelTextLocalIP.Click += new System.EventHandler(this.labelTextLocalIP_Click);
            // 
            // botonConectar
            // 
            this.botonConectar.Enabled = false;
            this.botonConectar.Location = new System.Drawing.Point(8, 62);
            this.botonConectar.Name = "botonConectar";
            this.botonConectar.Size = new System.Drawing.Size(294, 56);
            this.botonConectar.TabIndex = 3;
            this.botonConectar.Text = "Conectar";
            this.botonConectar.UseVisualStyleBackColor = true;
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(81)))), ((int)(((byte)(0)))));
            this.panel1.Controls.Add(this.label1);
            this.panel1.Location = new System.Drawing.Point(0, 525);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(886, 27);
            this.panel1.TabIndex = 5;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.ForeColor = System.Drawing.Color.White;
            this.label1.Location = new System.Drawing.Point(8, 7);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Preparando...";
            this.label1.Click += new System.EventHandler(this.label1_Click_2);
            // 
            // textBox2
            // 
            this.textBox2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(249)))), ((int)(((byte)(249)))), ((int)(((byte)(249)))));
            this.textBox2.Location = new System.Drawing.Point(6, 19);
            this.textBox2.Multiline = true;
            this.textBox2.Name = "textBox2";
            this.textBox2.ReadOnly = true;
            this.textBox2.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.textBox2.Size = new System.Drawing.Size(294, 263);
            this.textBox2.TabIndex = 7;
            this.textBox2.TextChanged += new System.EventHandler(this.textBox2_TextChanged);
            // 
            // vistPrevPan
            // 
            this.vistPrevPan.Controls.Add(this.imagen);
            this.vistPrevPan.Location = new System.Drawing.Point(327, 13);
            this.vistPrevPan.Name = "vistPrevPan";
            this.vistPrevPan.Size = new System.Drawing.Size(545, 506);
            this.vistPrevPan.TabIndex = 8;
            this.vistPrevPan.TabStop = false;
            this.vistPrevPan.Text = "Vista Previa";
            // 
            // imagen
            // 
            this.imagen.Location = new System.Drawing.Point(6, 19);
            this.imagen.Name = "imagen";
            this.imagen.Size = new System.Drawing.Size(533, 481);
            this.imagen.TabIndex = 0;
            this.imagen.TabStop = false;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.textBox2);
            this.groupBox2.Location = new System.Drawing.Point(13, 231);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(306, 288);
            this.groupBox2.TabIndex = 9;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Log";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.botonConectar);
            this.groupBox3.Controls.Add(this.labelServerIP);
            this.groupBox3.Controls.Add(this.textIP);
            this.groupBox3.Location = new System.Drawing.Point(13, 104);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(308, 124);
            this.groupBox3.TabIndex = 10;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Detalles del Servidor";
            // 
            // clienteGUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(884, 552);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.vistPrevPan);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.groupBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "clienteGUI";
            this.Text = "clienteGUI";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.vistPrevPan.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.imagen)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.ResumeLayout(false);

        }


        #endregion

        private System.Windows.Forms.Label labelServerIP;
        private System.Windows.Forms.TextBox textIP;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label labelLocalIP;
        private System.Windows.Forms.Label labelTextLocalIP;
        private System.Windows.Forms.Label labelMaquina;
        private System.Windows.Forms.Label labelTextMaquina;
        private System.Windows.Forms.Button botonConectar;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label1;
        private TextBox textBox2;
        private GroupBox vistPrevPan;
        private GroupBox groupBox2;
        private GroupBox groupBox3;
        private PictureBox imagen;
    }
}