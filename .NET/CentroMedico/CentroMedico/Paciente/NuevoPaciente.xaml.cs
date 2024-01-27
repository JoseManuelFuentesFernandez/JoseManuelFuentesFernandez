using MySql.Data.MySqlClient;
using System;
using System.Windows;

namespace CentroMedico.Paciente
{
    /// <summary>
    /// Lógica de interacción para NuevoPaciente.xaml
    /// </summary>
    public partial class NuevoPaciente : Window
    {
        public NuevoPaciente()
        {
            InitializeComponent();
        }

        private void btnAnadir_Click(object sender, RoutedEventArgs e)
        {
            if (txbNombre.Text.Equals("") ||
                txbApe.Text.Equals("") ||
                txbDir.Text.Equals("") ||
                txbDni.Text.Equals("") ||
                txbEmail.Text.Equals("") ||
                txbTel.Text.Equals("") ||
                txbIdCom.Text.Equals(""))
            {
                MessageBox.Show("Rellena los campos", "Error");
                return;
            }

            MySqlConnection conn = Conexion.GetConexion();
            conn.Open();
            try
            {
                string sql = "INSERT INTO Paciente (Nombre, Apellidos, Direccion, dni, Telefono, idCompañia, email) VALUES (?nombre, ?apellidos, ?direccion, ?dni, ?telefono, ?idCompañia, ?email)";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.Add("?nombre", MySqlDbType.VarChar).Value = txbNombre.Text;
                cmd.Parameters.Add("?apellidos", MySqlDbType.VarChar).Value = txbApe.Text;
                cmd.Parameters.Add("?direccion", MySqlDbType.VarChar).Value = txbDir.Text;
                cmd.Parameters.Add("?dni", MySqlDbType.VarChar).Value = txbDni.Text;
                cmd.Parameters.Add("?telefono", MySqlDbType.VarChar).Value = txbTel.Text;
                cmd.Parameters.Add("?idCompañia", MySqlDbType.Int32).Value = Int32.Parse(txbIdCom.Text.ToString());
                cmd.Parameters.Add("?email", MySqlDbType.VarChar).Value = txbEmail.Text;

                cmd.ExecuteNonQuery();

                MessageBox.Show("Paciente añadido con éxito", ":)");

                txbNombre.Text = "";
                txbApe.Text = "";
                txbDni.Text = "";
                txbDir.Text = "";
                txbEmail.Text = "";
                txbIdCom.Text = "";
                txbTel.Text = "";
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