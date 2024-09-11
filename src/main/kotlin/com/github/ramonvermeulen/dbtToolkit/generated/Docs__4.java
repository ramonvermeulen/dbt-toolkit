import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The docs defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Docs__4 implements Serializable
{

    private final static long serialVersionUID = 2529578892835286870L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Docs__4 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Docs__4) == false) {
            return false;
        }
        Docs__4 rhs = ((Docs__4) other);
        return true;
    }

}
