namespace CentroMedico.Especialidad
{
    class Especialidad
    {
        public int Id { get; }
        public string? Nombre { get; set; } = null;
        public string? Descripcion { get; set; } = null;
        public int Baja { get; set; } = 0;

        public Especialidad(int id, string? nombre, string? descripcion, int baja)
        {
            Id = id;
            Nombre = nombre;
            Descripcion = descripcion;
            Baja = baja;
        }

        public Especialidad(string? nombre, string? descripcion, int baja)
        {
            Nombre = nombre;
            Descripcion = descripcion;
            Baja = baja;
        }

        public Especialidad(string? nombre, string? descripcion)
        {
            Nombre = nombre;
            Descripcion = descripcion;
        }


    }
}