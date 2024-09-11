import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The saved queries defined in the dbt project
 * 
 */
@Generated("jsonschema2pojo")
public class SavedQueries__1 implements Serializable
{

    private final static long serialVersionUID = -2459137790409184614L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SavedQueries__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof SavedQueries__1) == false) {
            return false;
        }
        SavedQueries__1 rhs = ((SavedQueries__1) other);
        return true;
    }

}
