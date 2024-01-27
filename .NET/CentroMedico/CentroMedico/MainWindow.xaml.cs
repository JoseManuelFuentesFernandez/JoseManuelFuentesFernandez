using CentroMedico.Cita;
using CentroMedico.Especialidad;
using CentroMedico.Paciente;
using System.Windows;

namespace CentroMedico
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        //CITAS
        private void btnNuevaCita_Click(object sender, RoutedEventArgs e)
        {
            NuevaCita nuevaCita = new NuevaCita();
            nuevaCita.ShowDialog();
        }

        private void btnModificarCita_Click(object sender, RoutedEventArgs e)
        {
            ModificarCita modificarCita = new ModificarCita();
            modificarCita.ShowDialog();
        }

        private void btnCancelarCita_Click(object sender, RoutedEventArgs e)
        {
            CancelarCita cancelarCita = new CancelarCita();
            cancelarCita.ShowDialog();
        }

        //PACIENTES
        private void btnNuevoPac_Click(object sender, RoutedEventArgs e)
        {
            NuevoPaciente nuevoPaciente = new NuevoPaciente();
            nuevoPaciente.ShowDialog();
        }

        private void btnModificarPac_Click(object sender, RoutedEventArgs e)
        {
            ModificarPaciente modificarPaciente = new ModificarPaciente();
            modificarPaciente.ShowDialog();
        }



        //ESPECIALIDADES
        private void btnNuevaEsp_Click(object sender, RoutedEventArgs e)
        {
            NuevaEspecialidad nuevaEspecialidad = new NuevaEspecialidad();
            nuevaEspecialidad.ShowDialog();
        }

        private void btnModificarEsp_Click(object sender, RoutedEventArgs e)
        {
            ModificarEspecialidad modificarEspecialidad = new ModificarEspecialidad();
            modificarEspecialidad.ShowDialog();
        }
    }
}
