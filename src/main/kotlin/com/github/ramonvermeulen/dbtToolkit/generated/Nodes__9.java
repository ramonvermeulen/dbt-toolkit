import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The nodes defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Nodes__9 implements Serializable
{

    private final static long serialVersionUID = 8310748316439729071L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Nodes__9 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Nodes__9) == false) {
            return false;
        }
        Nodes__9 rhs = ((Nodes__9) other);
        return true;
    }

}
