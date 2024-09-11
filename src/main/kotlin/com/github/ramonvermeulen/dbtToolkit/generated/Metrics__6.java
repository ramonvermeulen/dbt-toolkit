import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The metrics defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Metrics__6 implements Serializable
{

    private final static long serialVersionUID = -6677531781718455992L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Metrics__6 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Metrics__6) == false) {
            return false;
        }
        Metrics__6 rhs = ((Metrics__6) other);
        return true;
    }

}
