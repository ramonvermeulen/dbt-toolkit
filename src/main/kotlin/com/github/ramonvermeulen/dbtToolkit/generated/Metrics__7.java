import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The metrics defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Metrics__7 implements Serializable
{

    private final static long serialVersionUID = -6547920852927427627L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Metrics__7 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Metrics__7) == false) {
            return false;
        }
        Metrics__7 rhs = ((Metrics__7) other);
        return true;
    }

}
