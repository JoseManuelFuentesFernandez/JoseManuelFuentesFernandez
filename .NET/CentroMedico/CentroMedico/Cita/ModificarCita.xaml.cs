using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Reflection.PortableExecutable;
using System.Windows;
using System.Windows.Controls;

namespace CentroMedico.Cita
{
    /// <summary>
    /// Lógica de interacción para ModificarCita.xaml
    /// </summary>
    public partial class ModificarCita : Window
    {
        public ModificarCita()
        {
            InitializeComponent();
            dtgCitas.ItemsSource = new List<Cita>();
        }

        static Paciente.Paciente pac;
        static Cita cita;

        static string date;
        static string hora;

        static List<string> HorasDisp = new List<string>();

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

            if (citas.Count == 0)
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
            dtgCitas.ItemsSource = new List<Cita>();
            dtgCitas.IsEnabled = false;
            btnModificarCita.IsEnabled = false;
        }

        private void dtgCitas_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (sender == null)
            {
                btnModificarCita.IsEnabled = false;
                return;
            }

            cita = (Cita)dtgCitas.SelectedItem;

            btnModificarCita.IsEnabled = true;
            dtPck.IsEnabled = true;
            cmbHorasDisp.IsEnabled = true;
            chbAn.IsEnabled = true;

            dtPck.SelectedDate = DateTime.Parse(cita.fecha);
            DateTime dateTime = (DateTime)dtPck.SelectedDate;
            date = dateTime.ToShortDateString();

            string dia = date.Substring(0, 2);
            string mes = date.Substring(3, 2);
            string ano = date.Substring(6);

            date = ano + "-" + mes + "-" + dia;

            cmbHorasDisp.ItemsSource = CargarHorasDisp(date);
            if (cita.anulada==1) chbAn.IsChecked = true;
        }

        private void dtPck_SelectedDateChanged(object sender, EventArgs e)
        {
            dtPck.SelectedDate= DateTime.Parse(cita.fecha);
            DateTime dateTime = (DateTime)dtPck.SelectedDate;
            date = dateTime.ToShortDateString();

            string dia = date.Substring(0, 2);
            string mes = date.Substring(3, 2);
            string ano = date.Substring(6);

            date = ano + "-" + mes + "-" + dia;

            cmbHorasDisp.ItemsSource = CargarHorasDisp(date);
        }

        private List<string> CargarHorasDisp(string date)
        {
            List<string> posiblesHoras = new List<string>();

            //Inicializar horas
            for (int i = 9; i < 21; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    string hora = i.ToString() + ":" + (j * 15);
                    if (j == 0) hora = hora + "0";
                    posiblesHoras.Add(hora);
                }
            }

            //Quitar horas no disponibles
            MySqlDataReader reader = null;
            MySqlConnection conexionBD = Conexion.GetConexion();

            string consulta = "SELECT fecha,hora,anulada FROM Cita where fecha=?fecha";
            MySqlCommand comando = new MySqlCommand(consulta);
            comando.Parameters.Add("?fecha", MySqlDbType.VarChar).Value = date;

            comando.Connection = conexionBD;
            conexionBD.Open();
            reader = comando.ExecuteReader();

            List<string> horasOcupadas = new List<string>();
            int index=-1;

            while (reader.Read())
            {
                string fecha = reader.GetString(0);
                fecha = fecha.Substring(0, 10);

                string dia = fecha.Substring(0, 2);
                string mes = fecha.Substring(3, 2);
                string ano = fecha.Substring(6);

                fecha = ano + "-" + mes + "-" + dia;

                int anulada = reader.GetInt32(2);
                if (fecha.Equals(date) && anulada == 0)
                {
                    string horaTemp = reader.GetString(1);

                    foreach (string h in posiblesHoras)
                    {
                        if (h.Equals(horaTemp))
                        {
                            horasOcupadas.Add(horaTemp);
                        }
                    }
                }
            }

            reader.Close();
            conexionBD.Close();

            foreach (string h in horasOcupadas)
            {
                posiblesHoras.Remove(h);
            }

            foreach (string h in posiblesHoras)
            {
                index++;
                if (h.Equals(hora))
                {
                    break;
                }
            }

            HorasDisp = posiblesHoras;
            cmbHorasDisp.ItemsSource = HorasDisp;
            cmbHorasDisp.SelectedItem = hora;

            return HorasDisp;
        }

        private void btnModificarCita_Click(object sender, RoutedEventArgs e)
        {
            if (dtPck.SelectedDate == null || DateTime.Compare((DateTime)dtPck.SelectedDate, DateTime.Today) < 0)
            {
                MessageBox.Show("Indica una fecha válida", "ERROR");
                return;
            }
            
            if(cmbHorasDisp.SelectedItem == null) 
            {
                MessageBox.Show("Indica una hora válida", "ERROR");
                return;
            }

            MySqlConnection conn = Conexion.GetConexion();
            conn.Open();
            try
            {
                string sql = "UPDATE Cita SET anulada=?anulada,hora=?hora,fecha=?fecha where id=?id";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                
                int anulada = 0;
                if(chbAn.IsChecked == true)
                {
                    anulada = 1;
                }

                cmd.Parameters.Add("?id", MySqlDbType.Int32).Value = cita.id;
                cmd.Parameters.Add("?anulada", MySqlDbType.Int32).Value = anulada;
                cmd.Parameters.Add("?hora", MySqlDbType.String).Value = hora;

                DateTime dateTime = (DateTime)dtPck.SelectedDate;
                date = dateTime.ToShortDateString();

                string dia = date.Substring(0, 2);
                string mes = date.Substring(3, 2);
                string ano = date.Substring(6);

                date = ano + "-" + mes + "-" + dia;

                cmd.Parameters.Add("?fecha", MySqlDbType.String).Value = date;

                cmd.ExecuteNonQuery();

                MessageBox.Show("Cita modificada con éxito", ":)");

                btnModificarCita.IsEnabled = false;
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
