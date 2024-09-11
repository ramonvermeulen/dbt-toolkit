import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The groups defined in the dbt project
 * 
 */
@Generated("jsonschema2pojo")
public class Groups__1 implements Serializable
{

    private final static long serialVersionUID = 5406963343720512910L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Groups__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Groups__1) == false) {
            return false;
        }
        Groups__1 rhs = ((Groups__1) other);
        return true;
    }

}
