import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The metrics defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Metrics__3 implements Serializable
{

    private final static long serialVersionUID = -74833621220702770L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Metrics__3 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Metrics__3) == false) {
            return false;
        }
        Metrics__3 rhs = ((Metrics__3) other);
        return true;
    }

}
