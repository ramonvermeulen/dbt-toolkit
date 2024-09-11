import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The sources defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Sources__3 implements Serializable
{

    private final static long serialVersionUID = 7184232792300052891L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Sources__3 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Sources__3) == false) {
            return false;
        }
        Sources__3 rhs = ((Sources__3) other);
        return true;
    }

}
