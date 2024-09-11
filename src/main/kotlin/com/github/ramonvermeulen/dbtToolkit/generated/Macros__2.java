import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The macros defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Macros__2 implements Serializable
{

    private final static long serialVersionUID = -4451130924766954380L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Macros__2 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Macros__2) == false) {
            return false;
        }
        Macros__2 rhs = ((Macros__2) other);
        return true;
    }

}
