import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The docs defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Docs__8 implements Serializable
{

    private final static long serialVersionUID = -3899415335262907305L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Docs__8 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Docs__8) == false) {
            return false;
        }
        Docs__8 rhs = ((Docs__8) other);
        return true;
    }

}
