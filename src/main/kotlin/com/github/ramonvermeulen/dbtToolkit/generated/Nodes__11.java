import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The nodes defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Nodes__11 implements Serializable
{

    private final static long serialVersionUID = -71882612210995332L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Nodes__11 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Nodes__11) == false) {
            return false;
        }
        Nodes__11 rhs = ((Nodes__11) other);
        return true;
    }

}
