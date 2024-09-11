import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The selectors defined in selectors.yml
 * 
 */
@Generated("jsonschema2pojo")
public class Selectors__5 implements Serializable
{

    private final static long serialVersionUID = -6248079573483301390L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Selectors__5 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Selectors__5) == false) {
            return false;
        }
        Selectors__5 rhs = ((Selectors__5) other);
        return true;
    }

}
