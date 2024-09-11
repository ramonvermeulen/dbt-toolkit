import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The exposures defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Exposures__2 implements Serializable
{

    private final static long serialVersionUID = 6323783839393141973L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Exposures__2 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Exposures__2) == false) {
            return false;
        }
        Exposures__2 rhs = ((Exposures__2) other);
        return true;
    }

}
