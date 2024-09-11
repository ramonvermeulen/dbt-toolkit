import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The docs defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Docs__5 implements Serializable
{

    private final static long serialVersionUID = 1695756575276703822L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Docs__5 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Docs__5) == false) {
            return false;
        }
        Docs__5 rhs = ((Docs__5) other);
        return true;
    }

}
