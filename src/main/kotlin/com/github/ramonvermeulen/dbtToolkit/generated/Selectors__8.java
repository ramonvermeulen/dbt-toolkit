import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The selectors defined in selectors.yml
 * 
 */
@Generated("jsonschema2pojo")
public class Selectors__8 implements Serializable
{

    private final static long serialVersionUID = 2479911432953958605L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Selectors__8 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Selectors__8) == false) {
            return false;
        }
        Selectors__8 rhs = ((Selectors__8) other);
        return true;
    }

}
