import java.util.Base64;

public interface Encode {
    static String codificar(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    static String descodificar(String s) {
        return new String(Base64.getDecoder().decode(s));
    }
}
