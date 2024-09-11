import java.io.Serializable;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Env__11 implements Serializable
{

    private final static long serialVersionUID = -9001504475657769337L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Env__11 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Env__11) == false) {
            return false;
        }
        Env__11 rhs = ((Env__11) other);
        return true;
    }

}
