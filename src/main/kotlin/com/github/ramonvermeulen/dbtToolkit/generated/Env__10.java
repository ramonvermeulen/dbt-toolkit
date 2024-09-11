import java.io.Serializable;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class Env__10 implements Serializable
{

    private final static long serialVersionUID = -4154981785262408692L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Env__10 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Env__10) == false) {
            return false;
        }
        Env__10 rhs = ((Env__10) other);
        return true;
    }

}
