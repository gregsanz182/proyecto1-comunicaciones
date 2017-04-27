using System.Drawing;
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
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.labelTextMaquina = new System.Windows.Forms.Label();
            this.labelLocalIP = new System.Windows.Forms.Label();
            this.labelMaquina = new System.Windows.Forms.Label();
            this.labelTextLocalIP = new System.Windows.Forms.Label();
            this.botonConectar = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelServerIP
            // 
            this.labelServerIP.AutoSize = true;
            this.labelServerIP.Location = new System.Drawing.Point(444, 10);
            this.labelServerIP.Name = "labelServerIP";
            this.labelServerIP.Size = new System.Drawing.Size(125, 13);
            this.labelServerIP.TabIndex = 0;
            this.labelServerIP.Text = "Dirección IP del servidor:";
            this.labelServerIP.Click += new System.EventHandler(this.label1_Click);
            // 
            // textBox1
            // 
            this.textBox1.Enabled = false;
            this.textBox1.Location = new System.Drawing.Point(447, 26);
            this.textBox1.MaxLength = 15;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(204, 20);
            this.textBox1.TabIndex = 1;
            this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.labelTextMaquina);
            this.groupBox1.Controls.Add(this.labelLocalIP);
            this.groupBox1.Controls.Add(this.labelMaquina);
            this.groupBox1.Controls.Add(this.labelTextLocalIP);
            this.groupBox1.Location = new System.Drawing.Point(29, 10);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(377, 86);
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
            this.botonConectar.Location = new System.Drawing.Point(447, 52);
            this.botonConectar.Name = "botonConectar";
            this.botonConectar.Size = new System.Drawing.Size(204, 43);
            this.botonConectar.TabIndex = 3;
            this.botonConectar.Text = "Conectar";
            this.botonConectar.UseVisualStyleBackColor = true;
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(202)))), ((int)(((byte)(81)))), ((int)(((byte)(0)))));
            this.panel1.Controls.Add(this.label1);
            this.panel1.Location = new System.Drawing.Point(0, 384);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(684, 27);
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
            // clienteGUI
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(684, 411);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.botonConectar);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.labelServerIP);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "clienteGUI";
            this.Text = "clienteGUI";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelServerIP;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label labelLocalIP;
        private System.Windows.Forms.Label labelTextLocalIP;
        private System.Windows.Forms.Label labelMaquina;
        private System.Windows.Forms.Label labelTextMaquina;
        private System.Windows.Forms.Button botonConectar;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label1;
    }
}