import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The nodes defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Nodes__8 implements Serializable
{

    private final static long serialVersionUID = -3329357867961992155L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Nodes__8 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Nodes__8) == false) {
            return false;
        }
        Nodes__8 rhs = ((Nodes__8) other);
        return true;
    }

}
