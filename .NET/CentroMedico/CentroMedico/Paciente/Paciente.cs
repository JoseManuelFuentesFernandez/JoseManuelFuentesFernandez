namespace CentroMedico.Paciente
{
    class Paciente
    {
        public Paciente() { }

        public Paciente(int id, string nombre, string apellidos, string direccion, string dni, string telefono, int idCompania, string email)
        {
            this.id = id;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.direccion = direccion;
            this.dni = dni;
            this.telefono = telefono;
            this.idCompania = idCompania;
            this.email = email;
        }

        public Paciente(string nombre, string apellidos, string direccion, string dni, string telefono, int idCompania, string email)
        {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.direccion = direccion;
            this.dni = dni;
            this.telefono = telefono;
            this.idCompania = idCompania;
            this.email = email;
        }

        public int? id { get; set; }
        public string? nombre { get; set; }
        public string? apellidos { get; set; }
        public string? direccion { get; set; }
        public string? dni { get; set; }
        public string? telefono { get; set; }
        public int? idCompania { get; set; } = 0;
        public string? email { get; set; }
    }
}