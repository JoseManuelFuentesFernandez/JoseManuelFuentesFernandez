namespace CentroMedico.Medico
{
    class Medico
    {
        public Medico()
        {
        }

        public Medico(int idEspecialidad, string? nombre, string? apellidos, string? numColegiado, string? telefono, string? dni, int baja, string? email)
        {
            IdEspecialidad = idEspecialidad;
            Nombre = nombre;
            Apellidos = apellidos;
            NumColegiado = numColegiado;
            Telefono = telefono;
            Dni = dni;
            Baja = baja;
            Email = email;
        }

        public Medico(int id, int idEspecialidad, string? nombre, string? apellidos, string? numColegiado, string? telefono, string? dni, int baja, string? email)
        {
            Id = id;
            IdEspecialidad = idEspecialidad;
            Nombre = nombre;
            Apellidos = apellidos;
            NumColegiado = numColegiado;
            Telefono = telefono;
            Dni = dni;
            Baja = baja;
            Email = email;
        }

        public int Id { get; set; }
        public int IdEspecialidad { get; set; }
        public string? Nombre { get; set; }
        public string? Apellidos { get; set; }
        public string? NumColegiado { get; set; }
        public string? Telefono { get; set; }
        public string? Dni { get; set; }
        public int Baja { get; set; }
        public string? Email { get; set; }
    }
}
