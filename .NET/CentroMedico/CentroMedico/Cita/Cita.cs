namespace CentroMedico.Cita
{
    class Cita
    {
        public Cita() { }

        public Cita(int idEspecialidad, int idPaciente, int idMedico, string? descripcion, string? fecha, string? hora, int anulada)
        {
            this.idEspecialidad = idEspecialidad;
            this.idPaciente = idPaciente;
            this.idMedico = idMedico;
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.hora = hora;
            this.anulada = anulada;
        }

        public int id { get; set; }
        public int idEspecialidad { get; set; } = 0;
        public int idPaciente { get; set; } = 0;
        public int idMedico { get; set; } = 0;
        public string? descripcion { get; set; } = null;
        public string? fecha { get; set; } = null;
        public string? hora { get; set; } = null;
        public int anulada { get; set; } = 0;

    }
}
