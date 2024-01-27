using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;

namespace CentroMedico.Cita
{
    /// <summary>
    /// Lógica de interacción para CancelarCita.xaml
    /// </summary>
    public partial class CancelarCita : Window
    {
        public CancelarCita()
        {
            InitializeComponent();
            dtgCitas.ItemsSource = new List<Cita>();
        }

        static Paciente.Paciente pac;
        static Cita cita;

        private void btnBusq_Click(object sender, RoutedEventArgs e)
        {
            if (txbBusq.Text.Equals(""))
            {
                MessageBox.Show("Introduce DNI o Apellidos", "Error");
                return;
            }

            MySqlDataReader reader = null;
            MySqlConnection conexionBD = Conexion.GetConexion();

            string consulta = "SELECT * FROM PACIENTE where dni=?dni or apellidos=?apellidos";
            MySqlCommand comando = new MySqlCommand(consulta);
            comando.Parameters.Add("?apellidos", MySqlDbType.VarChar).Value = txbBusq.Text;
            comando.Parameters.Add("?dni", MySqlDbType.VarChar).Value = txbBusq.Text;

            comando.Connection = conexionBD;
            conexionBD.Open();
            reader = comando.ExecuteReader();


            if (reader.Read())
            {
                pac = new Paciente.Paciente();
                pac.id = reader.GetInt32(0);

                lblTick.Visibility = Visibility.Visible;
                BuscarCitas(pac);
            }
            else
            {
                MessageBox.Show("Ese paciente no existe", "Error");
            }

            reader.Close();
            conexionBD.Close();
        }

        private void BuscarCitas(Paciente.Paciente pac)
        {
            MySqlDataReader reader = null;
            MySqlConnection conexionBD = Conexion.GetConexion();

            string consulta = "SELECT * FROM Cita where idPaciente=?idPaciente and anulada=0";
            MySqlCommand comando = new MySqlCommand(consulta);
            comando.Parameters.Add("?idPaciente", MySqlDbType.Int32).Value = pac.id;

            comando.Connection = conexionBD;
            conexionBD.Open();
            reader = comando.ExecuteReader();

            List<Cita> citas = new List<Cita>();

            while (reader.Read())
            {
                Cita c = new Cita();
                c.id = reader.GetInt32(0);
                c.idEspecialidad = reader.GetInt32(1);
                c.idPaciente = reader.GetInt32(2);
                c.idMedico = reader.GetInt32(3);
                c.fecha = reader.IsDBNull(4) ? "null" : reader.GetString(4);
                c.hora = reader.IsDBNull(5) ? "null" : reader.GetString(5);
                
                citas.Add(c);
            }

            reader.Close();
            conexionBD.Close();

            if(citas.Count == 0)
            {
                MessageBox.Show("No existen citas asociadas");
                return;
            }

            dtgCitas.ItemsSource = citas;
            dtgCitas.IsEnabled = true;
        }


        private void txbBusq_TextChanged(object sender, TextChangedEventArgs e)
        {
            lblTick.Visibility = Visibility.Hidden;
            dtgCitas.ItemsSource=new List<Cita>();
            dtgCitas.IsEnabled = false;
            btnCancelarCita.IsEnabled = false;
        }

        private void dtgCitas_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (sender == null)
            {
                btnCancelarCita.IsEnabled=false;
                return;
            }

            cita = (Cita)dtgCitas.SelectedItem;
            btnCancelarCita.IsEnabled = true;

        }

        private void btnCancelarCita_Click(object sender, RoutedEventArgs e)
        {
            MySqlConnection conn = Conexion.GetConexion();
            conn.Open();
            try
            {
                string sql = "UPDATE Cita SET anulada=1 where id=?id";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.Add("?id", MySqlDbType.Int32).Value = cita.id;

                cmd.ExecuteNonQuery();

                MessageBox.Show("Cita cancelada con éxito", ":)");

                btnCancelarCita.IsEnabled = false;
                lblTick.Visibility = Visibility.Hidden;
                txbBusq.Text = "";
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
