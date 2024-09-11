import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The sources defined in the dbt project and its dependencies
 * 
 */
@Generated("jsonschema2pojo")
public class Sources__11 implements Serializable
{

    private final static long serialVersionUID = -6922952295037124037L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Sources__11 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Sources__11) == false) {
            return false;
        }
        Sources__11 rhs = ((Sources__11) other);
        return true;
    }

}
