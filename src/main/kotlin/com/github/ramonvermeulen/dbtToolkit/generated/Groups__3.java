import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The groups defined in the dbt project
 * 
 */
@Generated("jsonschema2pojo")
public class Groups__3 implements Serializable
{

    private final static long serialVersionUID = 8139901198745299023L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Groups__3 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Groups__3) == false) {
            return false;
        }
        Groups__3 rhs = ((Groups__3) other);
        return true;
    }

}
