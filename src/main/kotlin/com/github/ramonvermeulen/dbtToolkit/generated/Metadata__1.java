import java.io.Serializable;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * ManifestMetadata
 * <p>
 * Metadata about the manifest
 * 
 */
@Generated("jsonschema2pojo")
public class Metadata__1 implements Serializable
{

    @SerializedName("dbt_schema_version")
    @Expose
    private String dbtSchemaVersion;
    @SerializedName("dbt_version")
    @Expose
    private String dbtVersion = "1.9.0a1";
    @SerializedName("generated_at")
    @Expose
    private String generatedAt;
    @SerializedName("invocation_id")
    @Expose
    private Object invocationId;
    @SerializedName("env")
    @Expose
    private Env__3 env;
    /**
     * Name of the root project
     * 
     */
    @SerializedName("project_name")
    @Expose
    private Object projectName = null;
    /**
     * A unique identifier for the project, hashed from the project name
     * 
     */
    @SerializedName("project_id")
    @Expose
    private Object projectId = null;
    /**
     * A unique identifier for the user
     * 
     */
    @SerializedName("user_id")
    @Expose
    private Object userId = null;
    /**
     * Whether dbt is configured to send anonymous usage statistics
     * 
     */
    @SerializedName("send_anonymous_usage_stats")
    @Expose
    private Object sendAnonymousUsageStats = null;
    /**
     * The type name of the adapter
     * 
     */
    @SerializedName("adapter_type")
    @Expose
    private Object adapterType = null;
    private final static long serialVersionUID = 2925435053380638749L;

    public String getDbtSchemaVersion() {
        return dbtSchemaVersion;
    }

    public void setDbtSchemaVersion(String dbtSchemaVersion) {
        this.dbtSchemaVersion = dbtSchemaVersion;
    }

    public String getDbtVersion() {
        return dbtVersion;
    }

    public void setDbtVersion(String dbtVersion) {
        this.dbtVersion = dbtVersion;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Object getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(Object invocationId) {
        this.invocationId = invocationId;
    }

    public Env__3 getEnv() {
        return env;
    }

    public void setEnv(Env__3 env) {
        this.env = env;
    }

    /**
     * Name of the root project
     * 
     */
    public Object getProjectName() {
        return projectName;
    }

    /**
     * Name of the root project
     * 
     */
    public void setProjectName(Object projectName) {
        this.projectName = projectName;
    }

    /**
     * A unique identifier for the project, hashed from the project name
     * 
     */
    public Object getProjectId() {
        return projectId;
    }

    /**
     * A unique identifier for the project, hashed from the project name
     * 
     */
    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

    /**
     * A unique identifier for the user
     * 
     */
    public Object getUserId() {
        return userId;
    }

    /**
     * A unique identifier for the user
     * 
     */
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    /**
     * Whether dbt is configured to send anonymous usage statistics
     * 
     */
    public Object getSendAnonymousUsageStats() {
        return sendAnonymousUsageStats;
    }

    /**
     * Whether dbt is configured to send anonymous usage statistics
     * 
     */
    public void setSendAnonymousUsageStats(Object sendAnonymousUsageStats) {
        this.sendAnonymousUsageStats = sendAnonymousUsageStats;
    }

    /**
     * The type name of the adapter
     * 
     */
    public Object getAdapterType() {
        return adapterType;
    }

    /**
     * The type name of the adapter
     * 
     */
    public void setAdapterType(Object adapterType) {
        this.adapterType = adapterType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Metadata__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("dbtSchemaVersion");
        sb.append('=');
        sb.append(((this.dbtSchemaVersion == null)?"<null>":this.dbtSchemaVersion));
        sb.append(',');
        sb.append("dbtVersion");
        sb.append('=');
        sb.append(((this.dbtVersion == null)?"<null>":this.dbtVersion));
        sb.append(',');
        sb.append("generatedAt");
        sb.append('=');
        sb.append(((this.generatedAt == null)?"<null>":this.generatedAt));
        sb.append(',');
        sb.append("invocationId");
        sb.append('=');
        sb.append(((this.invocationId == null)?"<null>":this.invocationId));
        sb.append(',');
        sb.append("env");
        sb.append('=');
        sb.append(((this.env == null)?"<null>":this.env));
        sb.append(',');
        sb.append("projectName");
        sb.append('=');
        sb.append(((this.projectName == null)?"<null>":this.projectName));
        sb.append(',');
        sb.append("projectId");
        sb.append('=');
        sb.append(((this.projectId == null)?"<null>":this.projectId));
        sb.append(',');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("sendAnonymousUsageStats");
        sb.append('=');
        sb.append(((this.sendAnonymousUsageStats == null)?"<null>":this.sendAnonymousUsageStats));
        sb.append(',');
        sb.append("adapterType");
        sb.append('=');
        sb.append(((this.adapterType == null)?"<null>":this.adapterType));
        sb.append(',');
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
        result = ((result* 31)+((this.generatedAt == null)? 0 :this.generatedAt.hashCode()));
        result = ((result* 31)+((this.dbtSchemaVersion == null)? 0 :this.dbtSchemaVersion.hashCode()));
        result = ((result* 31)+((this.dbtVersion == null)? 0 :this.dbtVersion.hashCode()));
        result = ((result* 31)+((this.sendAnonymousUsageStats == null)? 0 :this.sendAnonymousUsageStats.hashCode()));
        result = ((result* 31)+((this.env == null)? 0 :this.env.hashCode()));
        result = ((result* 31)+((this.projectName == null)? 0 :this.projectName.hashCode()));
        result = ((result* 31)+((this.invocationId == null)? 0 :this.invocationId.hashCode()));
        result = ((result* 31)+((this.projectId == null)? 0 :this.projectId.hashCode()));
        result = ((result* 31)+((this.userId == null)? 0 :this.userId.hashCode()));
        result = ((result* 31)+((this.adapterType == null)? 0 :this.adapterType.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metadata__1) == false) {
            return false;
        }
        Metadata__1 rhs = ((Metadata__1) other);
        return (((((((((((this.generatedAt == rhs.generatedAt)||((this.generatedAt!= null)&&this.generatedAt.equals(rhs.generatedAt)))&&((this.dbtSchemaVersion == rhs.dbtSchemaVersion)||((this.dbtSchemaVersion!= null)&&this.dbtSchemaVersion.equals(rhs.dbtSchemaVersion))))&&((this.dbtVersion == rhs.dbtVersion)||((this.dbtVersion!= null)&&this.dbtVersion.equals(rhs.dbtVersion))))&&((this.sendAnonymousUsageStats == rhs.sendAnonymousUsageStats)||((this.sendAnonymousUsageStats!= null)&&this.sendAnonymousUsageStats.equals(rhs.sendAnonymousUsageStats))))&&((this.env == rhs.env)||((this.env!= null)&&this.env.equals(rhs.env))))&&((this.projectName == rhs.projectName)||((this.projectName!= null)&&this.projectName.equals(rhs.projectName))))&&((this.invocationId == rhs.invocationId)||((this.invocationId!= null)&&this.invocationId.equals(rhs.invocationId))))&&((this.projectId == rhs.projectId)||((this.projectId!= null)&&this.projectId.equals(rhs.projectId))))&&((this.userId == rhs.userId)||((this.userId!= null)&&this.userId.equals(rhs.userId))))&&((this.adapterType == rhs.adapterType)||((this.adapterType!= null)&&this.adapterType.equals(rhs.adapterType))));
    }

}
