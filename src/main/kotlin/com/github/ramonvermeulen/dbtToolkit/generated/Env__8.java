import java.io.Serializable;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Env__8 implements Serializable
{

    private final static long serialVersionUID = -1490284541851989437L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Env__8 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Env__8) == false) {
            return false;
        }
        Env__8 rhs = ((Env__8) other);
        return true;
    }

}
