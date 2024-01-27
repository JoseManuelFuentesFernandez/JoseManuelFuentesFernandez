using CentroMedico.Paciente;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;

namespace CentroMedico.Cita
{
    /// <summary>
    /// Lógica de interacción para NuevaCita.xaml
    /// </summary>
    public partial class NuevaCita : Window
    {
        public NuevaCita()
        {
            InitializeComponent();
            CargarDatosEsp();
            dtPck.DisplayDateStart = DateTime.Today;
        }

        static Especialidad.Especialidad esp = null;
        static Medico.Medico med = null;
        static Paciente.Paciente pac = null;

        static string date;
        static string hora;

        static List<string> HorasDisp = new List<string>();

        private List<Especialidad.Especialidad> CargarDatosEsp()
        {
            MySqlDataReader reader = null;
            List<Especialidad.Especialidad> datos = new List<Especialidad.Especialidad>();
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
                    Especialidad.Especialidad a = new Especialidad.Especialidad(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetInt32(3));
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

        private void cmbEsp_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbEsp.SelectedIndex == -1)
            {
                return;
            }
            List<Especialidad.Especialidad> datos = CargarDatosEsp();
            Especialidad.Especialidad[] arrayDatos = datos.ToArray();
            esp = arrayDatos[cmbEsp.SelectedIndex];

            List<Medico.Medico> medicos = CargarDatosMed(esp);

            med = null;
            pac = null;
            txbBusq.IsEnabled = false;
            btnBusq.IsEnabled = false;
        }

        private List<Medico.Medico> CargarDatosMed(Especialidad.Especialidad esp)
        {
            MySqlDataReader reader = null;
            List<Medico.Medico> datos = new List<Medico.Medico>();
            List<string> nombres = new List<string>();
            MySqlConnection conexionBD = Conexion.GetConexion();

            try
            {
                string consulta = "SELECT * FROM Medico WHERE idEspecialidad=?idEspecialidad";
                MySqlCommand comando = new MySqlCommand(consulta);
                comando.Parameters.Add("?idEspecialidad", MySqlDbType.VarChar).Value = esp.Id;
                comando.Connection = conexionBD;
                conexionBD.Open();
                reader = comando.ExecuteReader();

                while (reader.Read())
                {
                    Medico.Medico m = new Medico.Medico();
                    m.Baja = reader.GetInt32(7);
                    if (m.Baja == 1)
                    {
                        continue;
                    }

                    m.Id = reader.GetInt32(0);
                    m.IdEspecialidad = reader.GetInt32(1);
                    m.Nombre = reader.IsDBNull(2) ? null : reader.GetString(2);
                    m.Apellidos = reader.IsDBNull(3) ? null : reader.GetString(3);
                    m.NumColegiado = reader.IsDBNull(4) ? null : reader.GetString(4);
                    m.Telefono = reader.IsDBNull(5) ? null : reader.GetString(5);
                    m.Dni = reader.IsDBNull(6) ? null : reader.GetString(6);
                    m.Email = reader.IsDBNull(8) ? null : reader.GetString(8);

                    nombres.Add(m.Nombre.ToString() + " " + m.Apellidos.ToString());
                    datos.Add(m);
                }
                cmbMed.ItemsSource = nombres;
                cmbMed.IsEnabled = true;
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

        private void cmbMed_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbMed.SelectedIndex == -1)
            {
                return;
            }
            List<Medico.Medico> datos = CargarDatosMed(esp);
            Medico.Medico[] arrayDatos = datos.ToArray();
            med = arrayDatos[cmbMed.SelectedIndex];

            txbBusq.IsEnabled = true;
            btnBusq.IsEnabled = true;
        }

        private void txbBusq_TextChanged(object sender, TextChangedEventArgs e)
        {
            lblTick.Visibility = Visibility.Hidden;
            dtPck.IsEnabled = false;
            cmbHorasDisp.IsEnabled = false;
            pac = null;
        }

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
                pac.nombre = reader.IsDBNull(1) ? null : reader.GetString(1);
                pac.apellidos = reader.IsDBNull(2) ? null : reader.GetString(2);
                pac.direccion = reader.IsDBNull(3) ? null : reader.GetString(3);
                pac.dni = reader.IsDBNull(4) ? null : reader.GetString(4);
                pac.telefono = reader.IsDBNull(5) ? null : reader.GetString(5);
                pac.idCompania = reader.GetInt32(6);
                pac.email = reader.IsDBNull(7) ? null : reader.GetString(7);

                lblTick.Visibility = Visibility.Visible;
                dtPck.IsEnabled = true;
            }
            else
            {
                MessageBoxResult result = MessageBox.Show("Ese paciente no existe\n¿Quieres crear uno?", "Error", MessageBoxButton.YesNo);
                if (result == MessageBoxResult.Yes)
                {
                    NuevoPaciente nuevoPaciente = new NuevoPaciente();
                    nuevoPaciente.ShowDialog();
                }
            }

            reader.Close();
            conexionBD.Close();
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
                    string hora = reader.GetString(1);

                    foreach (string h in posiblesHoras)
                    {
                        if (h.Equals(hora))
                        {
                            horasOcupadas.Add(hora);
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

            HorasDisp = posiblesHoras;
            cmbHorasDisp.ItemsSource = HorasDisp;

            return HorasDisp;
        }

        private void dtPck_SelectedDateChanged(object sender, SelectionChangedEventArgs e)
        {
            if (dtPck.GetValue == null)
            {
                MessageBox.Show("Selecciona una fecha válida", "Error");
            }

            DateTime dateTime = (DateTime)dtPck.SelectedDate;
            date = dateTime.ToShortDateString();

            string dia = date.Substring(0, 2);
            string mes = date.Substring(3, 2);
            string ano = date.Substring(6);

            date = ano + "-" + mes + "-" + dia;

            CargarHorasDisp(date);
            cmbHorasDisp.IsEnabled = true;
            btnCrearCita.IsEnabled = false;
        }

        private void cmbHorasDisp_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbHorasDisp.SelectedIndex == -1)
            {
                MessageBox.Show("Selecciona una hora válida", "Error");
                return;
            }

            List<string> datos = CargarHorasDisp(date);
            string[] arrayDatos = datos.ToArray();
            hora = arrayDatos[cmbHorasDisp.SelectedIndex];

            btnCrearCita.IsEnabled = true;
        }

        private void btnCrearCita_Click(object sender, RoutedEventArgs e)
        {
            MySqlConnection conn = Conexion.GetConexion();
            conn.Open();
            try
            {
                string sql = "INSERT INTO Cita (idEspecialidad, idPaciente, idMedico, fecha, hora, anulada) VALUES (?idEspecialidad, ?idPaciente, ?idMedico, ?fecha, ?hora, 0)";
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.Add("?idEspecialidad", MySqlDbType.Int32).Value = esp.Id;
                cmd.Parameters.Add("?idPaciente", MySqlDbType.Int32).Value = pac.id;
                cmd.Parameters.Add("?idMedico", MySqlDbType.Int32).Value = med.Id;
                cmd.Parameters.Add("?fecha", MySqlDbType.VarChar).Value = date;
                cmd.Parameters.Add("?hora", MySqlDbType.VarChar).Value = hora;

                cmd.ExecuteNonQuery();

                MessageBox.Show("Cita creada con éxito", ":)");

                NuevaCita n = new NuevaCita();
                Close();
                n.ShowDialog();
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
