import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The selectors defined in selectors.yml
 * 
 */
@Generated("jsonschema2pojo")
public class Selectors__11 implements Serializable
{

    private final static long serialVersionUID = 4376694436598829028L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Selectors__11 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Selectors__11) == false) {
            return false;
        }
        Selectors__11 rhs = ((Selectors__11) other);
        return true;
    }

}
