import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The semantic models defined in the dbt project
 * 
 */
@Generated("jsonschema2pojo")
public class SemanticModels__2 implements Serializable
{

    private final static long serialVersionUID = 4280381479864738450L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SemanticModels__2 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof SemanticModels__2) == false) {
            return false;
        }
        SemanticModels__2 rhs = ((SemanticModels__2) other);
        return true;
    }

}
