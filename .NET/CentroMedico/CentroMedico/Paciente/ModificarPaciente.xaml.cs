using MySql.Data.MySqlClient;
using System;
using System.Windows;

namespace CentroMedico.Paciente
{
    /// <summary>
    /// Lógica de interacción para ModificarPaciente.xaml
    /// </summary>
    public partial class ModificarPaciente : Window
    {
        public ModificarPaciente()
        {
            InitializeComponent();
            txbNombre.IsEnabled = false;
            txbApe.IsEnabled = true;
            txbDir.IsEnabled = false;
            txbDni.IsEnabled = true;
            txbEmail.IsEnabled = false;
            txbTel.IsEnabled = false;
            txbIdCom.IsEnabled = false;
        }

        static bool seleccionado = false;

        private void btnBuscar_Click(object sender, RoutedEventArgs e)
        {
            if (txbApe.Text.Equals("") && txbDni.Text.Equals(""))
            {
                MessageBox.Show("Rellena algún campo", "Error");
                return;
            }

            MySqlDataReader reader = null;
            MySqlConnection conexionBD = Conexion.GetConexion();

            string consulta = "SELECT * FROM PACIENTE where dni=?dni or apellidos=?apellidos";
            MySqlCommand comando = new MySqlCommand(consulta);
            comando.Parameters.Add("?apellidos", MySqlDbType.VarChar).Value = txbApe.Text;
            comando.Parameters.Add("?dni", MySqlDbType.VarChar).Value = txbDni.Text;

            comando.Connection = conexionBD;
            conexionBD.Open();
            reader = comando.ExecuteReader();

            if (reader.Read())
            {
                txbNombre.Text = reader.IsDBNull(1) ? null : reader.GetString(1);
                txbApe.Text = reader.IsDBNull(2) ? null : reader.GetString(2);
                txbDir.Text = reader.IsDBNull(3) ? null : reader.GetString(3);
                txbDni.Text = reader.IsDBNull(4) ? null : reader.GetString(4);
                txbTel.Text = reader.IsDBNull(5) ? null : reader.GetString(5);
                txbIdCom.Text = reader.GetInt32(6).ToString();
                txbEmail.Text = reader.IsDBNull(7) ? null : reader.GetString(7);

                txbNombre.IsEnabled = false;
                txbApe.IsEnabled = false;
                txbDir.IsEnabled = true;
                txbDni.IsEnabled = false;
                txbEmail.IsEnabled = false;
                txbTel.IsEnabled = true;
                txbIdCom.IsEnabled = true;

                seleccionado = true;
            }
            else
            {
                MessageBox.Show("Ese paciente no existe", "Error");
            }

            reader.Close();
            conexionBD.Close();
        }

        private void btnModificar_Click(object sender, RoutedEventArgs e)
        {
            if (!seleccionado)
            {
                MessageBox.Show("Indica un paciente", "Error");
                return;
            }

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

            MessageBoxResult result = MessageBox.Show("¿Estás seguro?", "Confirmar", MessageBoxButton.YesNo);
            if (result == MessageBoxResult.Yes)
            {
                MySqlConnection conn = Conexion.GetConexion();
                conn.Open();
                try
                {
                    string sql = "UPDATE Paciente SET direccion=?direccion, telefono=?telefono, idCompañia=?idCompañia where dni=?dni AND apellidos=?apellidos";
                    MySqlCommand cmd = new MySqlCommand(sql, conn);
                    cmd.Parameters.Add("?apellidos", MySqlDbType.VarChar).Value = txbApe.Text;
                    cmd.Parameters.Add("?direccion", MySqlDbType.VarChar).Value = txbDir.Text;
                    cmd.Parameters.Add("?dni", MySqlDbType.VarChar).Value = txbDni.Text;
                    cmd.Parameters.Add("?telefono", MySqlDbType.VarChar).Value = txbTel.Text;
                    cmd.Parameters.Add("?idCompañia", MySqlDbType.Int32).Value = Int32.Parse(txbIdCom.Text.ToString());

                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Paciente modificado con éxito", ":)");
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

            txbNombre.Text = "";
            txbApe.Text = "";
            txbDni.Text = "";
            txbDir.Text = "";
            txbEmail.Text = "";
            txbIdCom.Text = "";
            txbTel.Text = "";

            txbNombre.IsEnabled = false;
            txbApe.IsEnabled = true;
            txbDir.IsEnabled = false;
            txbDni.IsEnabled = true;
            txbEmail.IsEnabled = false;
            txbTel.IsEnabled = false;
            txbIdCom.IsEnabled = false;

            seleccionado = false;
        }
    }
}
