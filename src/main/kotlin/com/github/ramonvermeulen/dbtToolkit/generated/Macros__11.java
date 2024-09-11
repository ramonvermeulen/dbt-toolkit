import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The macros defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Macros__11 implements Serializable
{

    private final static long serialVersionUID = 756835933902911449L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Macros__11 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Macros__11) == false) {
            return false;
        }
        Macros__11 rhs = ((Macros__11) other);
        return true;
    }

}
