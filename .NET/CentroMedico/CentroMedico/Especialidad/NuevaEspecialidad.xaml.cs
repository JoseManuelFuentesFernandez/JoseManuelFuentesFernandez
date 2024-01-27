using MySql.Data.MySqlClient;
using System;
using System.Windows;

namespace CentroMedico.Especialidad
{
    /// <summary>
    /// Lógica de interacción para NuevaEspecialidad.xaml
    /// </summary>
    public partial class NuevaEspecialidad : Window
    {
        public NuevaEspecialidad()
        {
            InitializeComponent();
        }

        private void btnAnadir_Click(object sender, RoutedEventArgs e)
        {
            if (txbNombre.Text.Equals("") ||
                txbDesc.Text.Equals(""))
            {
                MessageBox.Show("Rellena los campos", "Error");
                return;
            }

            MySqlConnection conn = Conexion.GetConexion();
            conn.Open();
            try
            {
                string sql = "INSERT INTO Especialidad (Nombre, Descripcion, baja) VALUES (?nombre, ?descripcion, 0)";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.Add("?nombre", MySqlDbType.VarChar).Value = txbNombre.Text;
                cmd.Parameters.Add("?descripcion", MySqlDbType.VarChar).Value = txbDesc.Text;

                cmd.ExecuteNonQuery();

                MessageBox.Show("Especialidad añadida con éxito", ":)");

                txbNombre.Text = "";
                txbDesc.Text = "";
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }
}
