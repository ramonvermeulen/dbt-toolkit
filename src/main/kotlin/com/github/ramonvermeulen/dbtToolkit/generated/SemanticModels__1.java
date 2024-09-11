import java.io.Serializable;
import javax.annotation.processing.Generated;


/**
 * The semantic models defined in the dbt project
 * 
 */
@Generated("jsonschema2pojo")
public class SemanticModels__1 implements Serializable
{

    private final static long serialVersionUID = -1250494989469385027L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SemanticModels__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof SemanticModels__1) == false) {
            return false;
        }
        SemanticModels__1 rhs = ((SemanticModels__1) other);
        return true;
    }

}
