import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The saved queries defined in the dbt project
 * 
 */
@Generated("jsonschema2pojo")
public class SavedQueries implements Serializable
{

    private final static long serialVersionUID = 6203248888514558628L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SavedQueries.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof SavedQueries) == false) {
            return false;
        }
        SavedQueries rhs = ((SavedQueries) other);
        return true;
    }

}
