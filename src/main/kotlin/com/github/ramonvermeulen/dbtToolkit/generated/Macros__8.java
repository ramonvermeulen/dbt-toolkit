import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The macros defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Macros__8 implements Serializable
{

    private final static long serialVersionUID = 5193997629791953612L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Macros__8 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Macros__8) == false) {
            return false;
        }
        Macros__8 rhs = ((Macros__8) other);
        return true;
    }

}
