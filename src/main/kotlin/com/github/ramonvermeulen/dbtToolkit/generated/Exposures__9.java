import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The exposures defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Exposures__9 implements Serializable
{

    private final static long serialVersionUID = 5246658370720421011L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Exposures__9 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Exposures__9) == false) {
            return false;
        }
        Exposures__9 rhs = ((Exposures__9) other);
        return true;
    }

}
