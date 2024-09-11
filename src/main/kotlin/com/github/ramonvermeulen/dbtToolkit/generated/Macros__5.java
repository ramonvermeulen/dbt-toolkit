import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The macros defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Macros__5 implements Serializable
{

    private final static long serialVersionUID = -3724756975417302784L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Macros__5 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Macros__5) == false) {
            return false;
        }
        Macros__5 rhs = ((Macros__5) other);
        return true;
    }

}
