import java.io.Serializable;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * WritableManifest
 * <p>
 * 
 * 
 */
@Generated("jsonschema2pojo")
public class V12 implements Serializable
{

    /**
     * ManifestMetadata
     * <p>
     * Metadata about the manifest
     * (Required)
     * 
     */
    @SerializedName("metadata")
    @Expose
    private Metadata__1 metadata;
    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("nodes")
    @Expose
    private Nodes__3 nodes;
    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("sources")
    @Expose
    private Sources__3 sources;
    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("macros")
    @Expose
    private Macros__3 macros;
    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("docs")
    @Expose
    private Docs__3 docs;
    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("exposures")
    @Expose
    private Exposures__3 exposures;
    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("metrics")
    @Expose
    private Metrics__2 metrics;
    /**
     * The groups defined in the dbt project
     * (Required)
     * 
     */
    @SerializedName("groups")
    @Expose
    private Groups__2 groups;
    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    @SerializedName("selectors")
    @Expose
    private Selectors__3 selectors;
    /**
     * A mapping of the disabled nodes in the target
     * (Required)
     * 
     */
    @SerializedName("disabled")
    @Expose
    private Object disabled;
    /**
     * A mapping from child nodes to their dependencies
     * (Required)
     * 
     */
    @SerializedName("parent_map")
    @Expose
    private Object parentMap;
    /**
     * A mapping from parent nodes to their dependents
     * (Required)
     * 
     */
    @SerializedName("child_map")
    @Expose
    private Object childMap;
    /**
     * A mapping from group names to their nodes
     * (Required)
     * 
     */
    @SerializedName("group_map")
    @Expose
    private Object groupMap;
    /**
     * The saved queries defined in the dbt project
     * (Required)
     * 
     */
    @SerializedName("saved_queries")
    @Expose
    private SavedQueries__1 savedQueries;
    /**
     * The semantic models defined in the dbt project
     * (Required)
     * 
     */
    @SerializedName("semantic_models")
    @Expose
    private SemanticModels__2 semanticModels;
    /**
     * The unit tests defined in the project
     * (Required)
     * 
     */
    @SerializedName("unit_tests")
    @Expose
    private UnitTests unitTests;
    private final static long serialVersionUID = -2215764574028394305L;

    /**
     * ManifestMetadata
     * <p>
     * Metadata about the manifest
     * (Required)
     * 
     */
    public Metadata__1 getMetadata() {
        return metadata;
    }

    /**
     * ManifestMetadata
     * <p>
     * Metadata about the manifest
     * (Required)
     * 
     */
    public void setMetadata(Metadata__1 metadata) {
        this.metadata = metadata;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Nodes__3 getNodes() {
        return nodes;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setNodes(Nodes__3 nodes) {
        this.nodes = nodes;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Sources__3 getSources() {
        return sources;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setSources(Sources__3 sources) {
        this.sources = sources;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Macros__3 getMacros() {
        return macros;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMacros(Macros__3 macros) {
        this.macros = macros;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Docs__3 getDocs() {
        return docs;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setDocs(Docs__3 docs) {
        this.docs = docs;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Exposures__3 getExposures() {
        return exposures;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setExposures(Exposures__3 exposures) {
        this.exposures = exposures;
    }

    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Metrics__2 getMetrics() {
        return metrics;
    }

    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMetrics(Metrics__2 metrics) {
        this.metrics = metrics;
    }

    /**
     * The groups defined in the dbt project
     * (Required)
     * 
     */
    public Groups__2 getGroups() {
        return groups;
    }

    /**
     * The groups defined in the dbt project
     * (Required)
     * 
     */
    public void setGroups(Groups__2 groups) {
        this.groups = groups;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public Selectors__3 getSelectors() {
        return selectors;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public void setSelectors(Selectors__3 selectors) {
        this.selectors = selectors;
    }

    /**
     * A mapping of the disabled nodes in the target
     * (Required)
     * 
     */
    public Object getDisabled() {
        return disabled;
    }

    /**
     * A mapping of the disabled nodes in the target
     * (Required)
     * 
     */
    public void setDisabled(Object disabled) {
        this.disabled = disabled;
    }

    /**
     * A mapping from child nodes to their dependencies
     * (Required)
     * 
     */
    public Object getParentMap() {
        return parentMap;
    }

    /**
     * A mapping from child nodes to their dependencies
     * (Required)
     * 
     */
    public void setParentMap(Object parentMap) {
        this.parentMap = parentMap;
    }

    /**
     * A mapping from parent nodes to their dependents
     * (Required)
     * 
     */
    public Object getChildMap() {
        return childMap;
    }

    /**
     * A mapping from parent nodes to their dependents
     * (Required)
     * 
     */
    public void setChildMap(Object childMap) {
        this.childMap = childMap;
    }

    /**
     * A mapping from group names to their nodes
     * (Required)
     * 
     */
    public Object getGroupMap() {
        return groupMap;
    }

    /**
     * A mapping from group names to their nodes
     * (Required)
     * 
     */
    public void setGroupMap(Object groupMap) {
        this.groupMap = groupMap;
    }

    /**
     * The saved queries defined in the dbt project
     * (Required)
     * 
     */
    public SavedQueries__1 getSavedQueries() {
        return savedQueries;
    }

    /**
     * The saved queries defined in the dbt project
     * (Required)
     * 
     */
    public void setSavedQueries(SavedQueries__1 savedQueries) {
        this.savedQueries = savedQueries;
    }

    /**
     * The semantic models defined in the dbt project
     * (Required)
     * 
     */
    public SemanticModels__2 getSemanticModels() {
        return semanticModels;
    }

    /**
     * The semantic models defined in the dbt project
     * (Required)
     * 
     */
    public void setSemanticModels(SemanticModels__2 semanticModels) {
        this.semanticModels = semanticModels;
    }

    /**
     * The unit tests defined in the project
     * (Required)
     * 
     */
    public UnitTests getUnitTests() {
        return unitTests;
    }

    /**
     * The unit tests defined in the project
     * (Required)
     * 
     */
    public void setUnitTests(UnitTests unitTests) {
        this.unitTests = unitTests;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V12 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("metadata");
        sb.append('=');
        sb.append(((this.metadata == null)?"<null>":this.metadata));
        sb.append(',');
        sb.append("nodes");
        sb.append('=');
        sb.append(((this.nodes == null)?"<null>":this.nodes));
        sb.append(',');
        sb.append("sources");
        sb.append('=');
        sb.append(((this.sources == null)?"<null>":this.sources));
        sb.append(',');
        sb.append("macros");
        sb.append('=');
        sb.append(((this.macros == null)?"<null>":this.macros));
        sb.append(',');
        sb.append("docs");
        sb.append('=');
        sb.append(((this.docs == null)?"<null>":this.docs));
        sb.append(',');
        sb.append("exposures");
        sb.append('=');
        sb.append(((this.exposures == null)?"<null>":this.exposures));
        sb.append(',');
        sb.append("metrics");
        sb.append('=');
        sb.append(((this.metrics == null)?"<null>":this.metrics));
        sb.append(',');
        sb.append("groups");
        sb.append('=');
        sb.append(((this.groups == null)?"<null>":this.groups));
        sb.append(',');
        sb.append("selectors");
        sb.append('=');
        sb.append(((this.selectors == null)?"<null>":this.selectors));
        sb.append(',');
        sb.append("disabled");
        sb.append('=');
        sb.append(((this.disabled == null)?"<null>":this.disabled));
        sb.append(',');
        sb.append("parentMap");
        sb.append('=');
        sb.append(((this.parentMap == null)?"<null>":this.parentMap));
        sb.append(',');
        sb.append("childMap");
        sb.append('=');
        sb.append(((this.childMap == null)?"<null>":this.childMap));
        sb.append(',');
        sb.append("groupMap");
        sb.append('=');
        sb.append(((this.groupMap == null)?"<null>":this.groupMap));
        sb.append(',');
        sb.append("savedQueries");
        sb.append('=');
        sb.append(((this.savedQueries == null)?"<null>":this.savedQueries));
        sb.append(',');
        sb.append("semanticModels");
        sb.append('=');
        sb.append(((this.semanticModels == null)?"<null>":this.semanticModels));
        sb.append(',');
        sb.append("unitTests");
        sb.append('=');
        sb.append(((this.unitTests == null)?"<null>":this.unitTests));
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
        result = ((result* 31)+((this.metadata == null)? 0 :this.metadata.hashCode()));
        result = ((result* 31)+((this.macros == null)? 0 :this.macros.hashCode()));
        result = ((result* 31)+((this.sources == null)? 0 :this.sources.hashCode()));
        result = ((result* 31)+((this.groups == null)? 0 :this.groups.hashCode()));
        result = ((result* 31)+((this.childMap == null)? 0 :this.childMap.hashCode()));
        result = ((result* 31)+((this.unitTests == null)? 0 :this.unitTests.hashCode()));
        result = ((result* 31)+((this.selectors == null)? 0 :this.selectors.hashCode()));
        result = ((result* 31)+((this.exposures == null)? 0 :this.exposures.hashCode()));
        result = ((result* 31)+((this.nodes == null)? 0 :this.nodes.hashCode()));
        result = ((result* 31)+((this.groupMap == null)? 0 :this.groupMap.hashCode()));
        result = ((result* 31)+((this.semanticModels == null)? 0 :this.semanticModels.hashCode()));
        result = ((result* 31)+((this.docs == null)? 0 :this.docs.hashCode()));
        result = ((result* 31)+((this.parentMap == null)? 0 :this.parentMap.hashCode()));
        result = ((result* 31)+((this.savedQueries == null)? 0 :this.savedQueries.hashCode()));
        result = ((result* 31)+((this.disabled == null)? 0 :this.disabled.hashCode()));
        result = ((result* 31)+((this.metrics == null)? 0 :this.metrics.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof V12) == false) {
            return false;
        }
        V12 rhs = ((V12) other);
        return (((((((((((((((((this.metadata == rhs.metadata)||((this.metadata!= null)&&this.metadata.equals(rhs.metadata)))&&((this.macros == rhs.macros)||((this.macros!= null)&&this.macros.equals(rhs.macros))))&&((this.sources == rhs.sources)||((this.sources!= null)&&this.sources.equals(rhs.sources))))&&((this.groups == rhs.groups)||((this.groups!= null)&&this.groups.equals(rhs.groups))))&&((this.childMap == rhs.childMap)||((this.childMap!= null)&&this.childMap.equals(rhs.childMap))))&&((this.unitTests == rhs.unitTests)||((this.unitTests!= null)&&this.unitTests.equals(rhs.unitTests))))&&((this.selectors == rhs.selectors)||((this.selectors!= null)&&this.selectors.equals(rhs.selectors))))&&((this.exposures == rhs.exposures)||((this.exposures!= null)&&this.exposures.equals(rhs.exposures))))&&((this.nodes == rhs.nodes)||((this.nodes!= null)&&this.nodes.equals(rhs.nodes))))&&((this.groupMap == rhs.groupMap)||((this.groupMap!= null)&&this.groupMap.equals(rhs.groupMap))))&&((this.semanticModels == rhs.semanticModels)||((this.semanticModels!= null)&&this.semanticModels.equals(rhs.semanticModels))))&&((this.docs == rhs.docs)||((this.docs!= null)&&this.docs.equals(rhs.docs))))&&((this.parentMap == rhs.parentMap)||((this.parentMap!= null)&&this.parentMap.equals(rhs.parentMap))))&&((this.savedQueries == rhs.savedQueries)||((this.savedQueries!= null)&&this.savedQueries.equals(rhs.savedQueries))))&&((this.disabled == rhs.disabled)||((this.disabled!= null)&&this.disabled.equals(rhs.disabled))))&&((this.metrics == rhs.metrics)||((this.metrics!= null)&&this.metrics.equals(rhs.metrics))));
    }

}
