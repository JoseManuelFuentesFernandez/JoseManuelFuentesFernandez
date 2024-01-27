package iestr.jmff.gestorfacturas.modelo;

import iestr.jmff.gestorfacturas.modelo.Cliente;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-01-17T18:30:05", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, Date> fecha;
    public static volatile SingularAttribute<Factura, Cliente> cliente;
    public static volatile SingularAttribute<Factura, String> tipo;
    public static volatile SingularAttribute<Factura, String> asunto;
    public static volatile SingularAttribute<Factura, Integer> baja;
    public static volatile SingularAttribute<Factura, Integer> id;
    public static volatile SingularAttribute<Factura, Double> cantidad;

}