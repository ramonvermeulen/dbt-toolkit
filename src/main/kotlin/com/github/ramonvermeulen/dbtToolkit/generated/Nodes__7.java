import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The nodes defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Nodes__7 implements Serializable
{

    private final static long serialVersionUID = -539771579266086521L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Nodes__7 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Nodes__7) == false) {
            return false;
        }
        Nodes__7 rhs = ((Nodes__7) other);
        return true;
    }

}
