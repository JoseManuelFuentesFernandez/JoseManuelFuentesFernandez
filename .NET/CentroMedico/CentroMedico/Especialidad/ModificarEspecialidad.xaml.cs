using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;

namespace CentroMedico.Especialidad
{
    /// <summary>
    /// Lógica de interacción para ModificarEspecialidad.xaml
    /// </summary>
    public partial class ModificarEspecialidad : Window
    {
        public ModificarEspecialidad()
        {
            InitializeComponent();
            CargarDatos();
            txbNombre.IsEnabled = false;
            txbDesc.IsEnabled = false;
            chbBaja.IsEnabled = false;
        }

        static bool seleccionado = false;

        private List<Especialidad> CargarDatos()
        {
            MySqlDataReader reader = null;
            List<Especialidad> datos = new List<Especialidad>();
            List<string> nombres = new List<string>();
            MySqlConnection conexionBD = Conexion.GetConexion();

            try
            {
                string consulta = "SELECT * FROM Especialidad";
                MySqlCommand comando = new MySqlCommand(consulta);
                comando.Connection = conexionBD;
                conexionBD.Open();
                reader = comando.ExecuteReader();

                while (reader.Read())
                {
                    Especialidad a = new Especialidad(reader.GetString(1), reader.GetString(2), reader.GetInt32(3));
                    nombres.Add(a.Nombre.ToString());
                    datos.Add(a);
                }

                cmbEsp.ItemsSource = nombres;

            }
            catch (MySqlException ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                conexionBD.Close();
            }
            return datos;
        }

        private void ComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbEsp.SelectedIndex == -1)
            {
                return;
            }
            List<Especialidad> datos = CargarDatos();
            Especialidad[] arrayDatos = datos.ToArray();
            Especialidad seleccion = arrayDatos[cmbEsp.SelectedIndex];

            txbNombre.Text = seleccion.Nombre;
            txbDesc.Text = seleccion.Descripcion;

            if (seleccion.Baja == 0)
            {
                chbBaja.IsChecked = false;
            }
            else
            {
                chbBaja.IsChecked = true;
            }

            txbDesc.IsEnabled = true;
            chbBaja.IsEnabled = true;

            seleccionado = true;
        }

        private void btnMod_Click(object sender, RoutedEventArgs e)
        {
            if (!seleccionado)
            {
                MessageBox.Show("Indica una especialidad", "Error");
                return;
            }

            if (txbDesc.Text.Equals(""))
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
                    int baja = 0;
                    string sql = "UPDATE Especialidad SET descripcion=?descripcion, baja=?baja where nombre=?nombre";
                    MySqlCommand cmd = new MySqlCommand(sql, conn);
                    cmd.Parameters.Add("?nombre", MySqlDbType.VarChar).Value = txbNombre.Text;
                    cmd.Parameters.Add("?descripcion", MySqlDbType.VarChar).Value = txbDesc.Text;

                    if (chbBaja.IsChecked == true) baja = 1;
                    cmd.Parameters.Add("?baja", MySqlDbType.Int32).Value = baja;

                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Especialidad modificada con éxito", ":)");
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

            cmbEsp.SelectedIndex = -1;

            txbNombre.Text = "";
            txbDesc.Text = "";

            txbDesc.IsEnabled = false;
            chbBaja.IsEnabled = false;
            chbBaja.IsChecked = false;

            seleccionado = false;
        }
    }
}