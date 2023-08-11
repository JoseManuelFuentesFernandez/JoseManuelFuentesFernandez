import java.io.*;
import java.util.*;

public class Main implements Encode{
    static final String ARCHIVO_USUARIOS = "usuarios.ser";
    static final String INICIO = "---Bienvenido a Courtain---";
    static final String ELEGIR_USUARIO = "Identifícate: ";
    static final String COMPROBAR_IDENTIDAD = "Contraseña: ";
    static final String USUARIO_NO_EXISTE = "Ese usuario no se encuentra en el sistema\n¿Desea registrarlo? (S/N)";
    static final String ACCESO_CONCEDIDO = "Has iniciado sesión correctamente";
    static final String USUARIO_NO_VALIDO = "Ese usuario no está permitido";
    static final String USUARIO_NUEVO = "Se ha registrado el nuevo usuario correctamente";
    static final String CONTRASENA_NO_VALIDA = "Contraseña incorrecta (%d/3)\n";
    static final String LEYENDO_DATOS = "Leyendo datos...";
    static final String GUARDANDO_DATOS = "Guardando datos...";
    static final String ARCHIVO_NO_EXISTE = "No existe el archivo\nCreando archivo...\nArchivo creado";
    static final String ARCHIVO_VACIO = "El archivo no contiene datos";
    static final String ERROR_LECTURA = "ERROR AL LEER";
    static final String ERROR_ESCRITURA = "ERROR AL GUARDAR";
    static final String LECTURA_COMPLETADA = "Lectura completada";
    static final String ESCRITURA_COMPLETADA = "Datos guardados correctamente";
    static final String MENU = "\n========================\nSelecciona una opción:\n  0) Guardar y salir\n  1) Ver contraseñas\n  2) Añadir contraseña\n  3) Eliminar contraseña\n  4) Generar contraseña aleatoria\n> ";
    static final String GESTOR_VACIO = "No hay contraseñas guardadas";
    static final String PEDIR_ID = "Selecciona el ID de la contraseña a eliminar: ";
    static final String ID_NO_VALIDO = "Ese ID no es válido";
    static final String PEDIR_SITIO = "Introduce el sitio web: ";
    static final String PEDIR_USUARIO = "Introduce el usuario: ";
    static final String PEDIR_CONTRASENA = "Introduce la contraseña (introduce 'random' para generar una contraseña aleatoria): ";
    static final String CONTRASENA_ANADIDA = "Contraseña añadida exitosamente";
    static final String CONTRASENA_ELIMINADA = "Contraseña eliminada exitosamente";
    static final String CONTRASENA_GENERADA = "La contraseña generada es '%s'\n";

    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();
    static Map<String,String> usuarios;
    static ArrayList<Dato> gestor;
    static String archivo;


    public static void main(String[] args) {
        inicializar();
        elegirUsuario();
        deserializarDatos();
        menu();
        serializarDatos();
    }

    private static void inicializar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_USUARIOS))) {
            usuarios = (Map<String,String>) ois.readObject();
        } catch (FileNotFoundException e) {
            //No pasa nada, no existe el archivo y se crea
            usuarios = new HashMap<>();
        } catch (IOException e) {
            usuarios = new HashMap<>();
        } catch (ClassNotFoundException e) {

        }

        if(!usuarios.containsKey("admin")) usuarios.put("admin",Encode.codificar("admin"));

        System.out.println(INICIO);
    }

    private static void elegirUsuario() {
        boolean acceso = false;
        int intentos = 0;
        String clave,usuario,opcion;

        do{
            System.out.println(ELEGIR_USUARIO);
            usuario = sc.nextLine();

            if(usuario.equals("usuarios")||usuario.isBlank()){
                System.out.println(USUARIO_NO_VALIDO);
            }else{
                if(usuarios.containsKey(usuario)){
                    do{
                        System.out.println(COMPROBAR_IDENTIDAD);
                        clave = sc.nextLine();

                        if(Encode.descodificar(usuarios.get(usuario)).equals(clave)){
                            acceso=true;
                            archivo=usuario+".ser";
                            System.out.println(ACCESO_CONCEDIDO);
                            break;
                        }else{
                            System.out.printf(CONTRASENA_NO_VALIDA,++intentos);
                        }
                    }while (intentos<=3);
                }else{
                    System.out.println(USUARIO_NO_EXISTE);
                    opcion = sc.nextLine();

                    if(opcion.equals("S")||opcion.equals("s")){
                        System.out.printf(PEDIR_USUARIO);
                        usuario = sc.nextLine();

                        System.out.printf(COMPROBAR_IDENTIDAD);
                        clave = sc.nextLine();

                        usuarios.put(usuario,Encode.codificar(clave));

                        System.out.println(USUARIO_NUEVO);
                    }
                }
            }
        }while (!acceso);
    }

    private static void menu() {
        int opcion;
        do {
            System.out.printf(MENU);
            do {
                opcion = sc.nextInt();
                sc.nextLine();
            }while (opcion < 0 || opcion > 4);

            switch (opcion){
                case 0:{
                    break;
                }
                case 1:{
                    verContrasenas();
                    break;
                }
                case 2:{
                    anadirContrasena();
                    break;
                }
                case 3:{
                    eliminarContrasena();
                    break;
                }
                case 4:{
                    generarContrasenaAleatoria();
                }
            }
            Collections.sort(gestor);
        }while (opcion!=0);
    }

    private static boolean verContrasenas() {
        System.out.println();
        if(gestor.isEmpty()){
            System.out.println(GESTOR_VACIO);
            return false;
        }else{
            for (Dato d : gestor){
                System.out.println(d);
            }
            return true;
        }
    }

    private static void anadirContrasena() {
        System.out.println(PEDIR_SITIO);
        String sitio = sc.nextLine();

        System.out.println(PEDIR_USUARIO);
        String usuario = sc.nextLine();

        String contrasena;
        do{
            System.out.println(PEDIR_CONTRASENA);
            contrasena = sc.nextLine();
            if(contrasena.equals("random")){
                contrasena = generarContrasenaAleatoria();
            }
        }while (contrasena.length()<8);

        int id = gestor.size()+1;
        if(gestor.size()>0){
            for(int i = 1 ; i <= gestor.size() ; i++){
                if(gestor.get(i-1).getId()!=i){
                    id=i;
                    break;
                }
            }
        }
        gestor.add(new Dato(id,sitio,usuario,contrasena));
        System.out.println(CONTRASENA_ANADIDA);
    }

    private static void eliminarContrasena() {
        if(verContrasenas()){
            System.out.printf(PEDIR_ID);
            while(!sc.hasNextInt()) sc.next();
            int seleccion = sc.nextInt();

            for(Dato d : gestor){
                if(d.getId()==seleccion){
                    gestor.remove(d);
                    System.out.println(CONTRASENA_ELIMINADA);
                    return;
                }
            }
            System.out.println(ID_NO_VALIDO);
        }
    }

    private static String generarContrasenaAleatoria() {
        StringBuilder sb = new StringBuilder();

        String caracteres = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZ!@#$%&/()=+?[]~–";
        int longitud = r.nextInt(6)+12;//Longitud de contraseña: 12 a 18 caracteres

        for(int i = 0 ; i < longitud ; i++){
            sb.append(caracteres.charAt(r.nextInt(caracteres.length())));
        }

        System.out.printf(CONTRASENA_GENERADA,sb);
        return sb.toString();
    }

    private static void deserializarDatos() {
        System.out.println(LEYENDO_DATOS);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            gestor = (ArrayList<Dato>) ois.readObject();
            System.out.println(LECTURA_COMPLETADA);
        } catch (FileNotFoundException e) {
            //No pasa nada, no existe el archivo y se crea
            System.out.println(ARCHIVO_NO_EXISTE);
            gestor = new ArrayList<>();
        } catch (IOException e) {
            System.out.println(ARCHIVO_VACIO);
            gestor = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println(ERROR_LECTURA);
        }
    }

    private static void serializarDatos() {
        System.out.println(GUARDANDO_DATOS);
        //Gestor de contraseñas del usuario
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(gestor);
            System.out.println(ESCRITURA_COMPLETADA);
        } catch (FileNotFoundException e) {
            //No pasa nada, el archivo se ha creado previamente
        } catch (IOException e) {
            System.out.println(ERROR_ESCRITURA);
        }

        //Archivo con la lista de usuarios
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (FileNotFoundException e) {
            //No pasa nada, el archivo se ha creado previamente
        } catch (IOException e) {
            System.out.println(ERROR_ESCRITURA);
        }
    }
}