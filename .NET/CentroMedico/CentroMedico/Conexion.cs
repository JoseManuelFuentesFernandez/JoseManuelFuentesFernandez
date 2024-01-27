using MySql.Data.MySqlClient;

namespace CentroMedico
{
    internal static class Conexion
    {
        static string servidor = "localhost";
        static string bd = "CentroMedico";
        static string usuario = "root";
        static string password = "root";

        public static MySqlConnection GetConexion()
        {
            string cadenaConexion = "Database=" + bd + "; Data Source=" + servidor + "; User Id=" + usuario + "; Password=" + password + "";

            MySqlConnection conexionBD = new MySqlConnection(cadenaConexion);
            return conexionBD;
        }
    }
}
