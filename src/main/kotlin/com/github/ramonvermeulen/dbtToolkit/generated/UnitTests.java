import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The unit tests defined in the project
 * 
 */
@Generated("jsonschema2pojo")
public class UnitTests implements Serializable
{

    private final static long serialVersionUID = 7924628048165696222L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UnitTests.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof UnitTests) == false) {
            return false;
        }
        UnitTests rhs = ((UnitTests) other);
        return true;
    }

}
