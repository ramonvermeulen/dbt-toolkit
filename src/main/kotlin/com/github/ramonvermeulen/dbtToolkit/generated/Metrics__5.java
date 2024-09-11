import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The metrics defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Metrics__5 implements Serializable
{

    private final static long serialVersionUID = 7604010205686511685L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Metrics__5 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Metrics__5) == false) {
            return false;
        }
        Metrics__5 rhs = ((Metrics__5) other);
        return true;
    }

}
