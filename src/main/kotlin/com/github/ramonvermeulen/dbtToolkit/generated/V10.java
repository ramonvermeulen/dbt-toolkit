import java.io.Serializable;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * WritableManifest(metadata: dbt.contracts.graph.manifest.ManifestMetadata, nodes: Mapping[str, Union[dbt.contracts.graph.nodes.AnalysisNode, dbt.contracts.graph.nodes.SingularTestNode, dbt.contracts.graph.nodes.HookNode, dbt.contracts.graph.nodes.ModelNode, dbt.contracts.graph.nodes.RPCNode, dbt.contracts.graph.nodes.SqlNode, dbt.contracts.graph.nodes.GenericTestNode, dbt.contracts.graph.nodes.SnapshotNode, dbt.contracts.graph.nodes.SeedNode]], sources: Mapping[str, dbt.contracts.graph.nodes.SourceDefinition], macros: Mapping[str, dbt.contracts.graph.nodes.Macro], docs: Mapping[str, dbt.contracts.graph.nodes.Documentation], exposures: Mapping[str, dbt.contracts.graph.nodes.Exposure], metrics: Mapping[str, dbt.contracts.graph.nodes.Metric], groups: Mapping[str, dbt.contracts.graph.nodes.Group], selectors: Mapping[str, Any], disabled: Union[Mapping[str, List[Union[dbt.contracts.graph.nodes.AnalysisNode, dbt.contracts.graph.nodes.SingularTestNode, dbt.contracts.graph.nodes.HookNode, dbt.contracts.graph.nodes.ModelNode, dbt.contracts.graph.nodes.RPCNode, dbt.contracts.graph.nodes.SqlNode, dbt.contracts.graph.nodes.GenericTestNode, dbt.contracts.graph.nodes.SnapshotNode, dbt.contracts.graph.nodes.SeedNode, dbt.contracts.graph.nodes.SourceDefinition, dbt.contracts.graph.nodes.Exposure, dbt.contracts.graph.nodes.Metric, dbt.contracts.graph.nodes.SemanticModel]]], NoneType], parent_map: Union[Dict[str, List[str]], NoneType], child_map: Union[Dict[str, List[str]], NoneType], group_map: Union[Dict[str, List[str]], NoneType], semantic_models: Mapping[str, dbt.contracts.graph.nodes.SemanticModel])
 * 
 */
@Generated("jsonschema2pojo")
public class V10 implements Serializable
{

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    @SerializedName("metadata")
    @Expose
    private ManifestMetadata__1 metadata;
    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("nodes")
    @Expose
    private Nodes__1 nodes;
    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("sources")
    @Expose
    private Sources__1 sources;
    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("macros")
    @Expose
    private Macros__1 macros;
    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("docs")
    @Expose
    private Docs__1 docs;
    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("exposures")
    @Expose
    private Exposures__1 exposures;
    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("metrics")
    @Expose
    private Metrics metrics;
    /**
     * The groups defined in the dbt project
     * (Required)
     * 
     */
    @SerializedName("groups")
    @Expose
    private Groups groups;
    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    @SerializedName("selectors")
    @Expose
    private Selectors__1 selectors;
    /**
     * A mapping of the disabled nodes in the target
     * 
     */
    @SerializedName("disabled")
    @Expose
    private Object disabled;
    /**
     * A mapping from child nodes to their dependencies
     * 
     */
    @SerializedName("parent_map")
    @Expose
    private Object parentMap;
    /**
     * A mapping from parent nodes to their dependents
     * 
     */
    @SerializedName("child_map")
    @Expose
    private Object childMap;
    /**
     * A mapping from group names to their nodes
     * 
     */
    @SerializedName("group_map")
    @Expose
    private Object groupMap;
    /**
     * The semantic models defined in the dbt project
     * (Required)
     * 
     */
    @SerializedName("semantic_models")
    @Expose
    private SemanticModels semanticModels;
    private final static long serialVersionUID = 7435006159450417138L;

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    public ManifestMetadata__1 getMetadata() {
        return metadata;
    }

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    public void setMetadata(ManifestMetadata__1 metadata) {
        this.metadata = metadata;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Nodes__1 getNodes() {
        return nodes;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setNodes(Nodes__1 nodes) {
        this.nodes = nodes;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Sources__1 getSources() {
        return sources;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setSources(Sources__1 sources) {
        this.sources = sources;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Macros__1 getMacros() {
        return macros;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMacros(Macros__1 macros) {
        this.macros = macros;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Docs__1 getDocs() {
        return docs;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setDocs(Docs__1 docs) {
        this.docs = docs;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Exposures__1 getExposures() {
        return exposures;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setExposures(Exposures__1 exposures) {
        this.exposures = exposures;
    }

    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * The groups defined in the dbt project
     * (Required)
     * 
     */
    public Groups getGroups() {
        return groups;
    }

    /**
     * The groups defined in the dbt project
     * (Required)
     * 
     */
    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public Selectors__1 getSelectors() {
        return selectors;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public void setSelectors(Selectors__1 selectors) {
        this.selectors = selectors;
    }

    /**
     * A mapping of the disabled nodes in the target
     * 
     */
    public Object getDisabled() {
        return disabled;
    }

    /**
     * A mapping of the disabled nodes in the target
     * 
     */
    public void setDisabled(Object disabled) {
        this.disabled = disabled;
    }

    /**
     * A mapping from child nodes to their dependencies
     * 
     */
    public Object getParentMap() {
        return parentMap;
    }

    /**
     * A mapping from child nodes to their dependencies
     * 
     */
    public void setParentMap(Object parentMap) {
        this.parentMap = parentMap;
    }

    /**
     * A mapping from parent nodes to their dependents
     * 
     */
    public Object getChildMap() {
        return childMap;
    }

    /**
     * A mapping from parent nodes to their dependents
     * 
     */
    public void setChildMap(Object childMap) {
        this.childMap = childMap;
    }

    /**
     * A mapping from group names to their nodes
     * 
     */
    public Object getGroupMap() {
        return groupMap;
    }

    /**
     * A mapping from group names to their nodes
     * 
     */
    public void setGroupMap(Object groupMap) {
        this.groupMap = groupMap;
    }

    /**
     * The semantic models defined in the dbt project
     * (Required)
     * 
     */
    public SemanticModels getSemanticModels() {
        return semanticModels;
    }

    /**
     * The semantic models defined in the dbt project
     * (Required)
     * 
     */
    public void setSemanticModels(SemanticModels semanticModels) {
        this.semanticModels = semanticModels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V10 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("semanticModels");
        sb.append('=');
        sb.append(((this.semanticModels == null)?"<null>":this.semanticModels));
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
        result = ((result* 31)+((this.selectors == null)? 0 :this.selectors.hashCode()));
        result = ((result* 31)+((this.exposures == null)? 0 :this.exposures.hashCode()));
        result = ((result* 31)+((this.nodes == null)? 0 :this.nodes.hashCode()));
        result = ((result* 31)+((this.groupMap == null)? 0 :this.groupMap.hashCode()));
        result = ((result* 31)+((this.semanticModels == null)? 0 :this.semanticModels.hashCode()));
        result = ((result* 31)+((this.docs == null)? 0 :this.docs.hashCode()));
        result = ((result* 31)+((this.parentMap == null)? 0 :this.parentMap.hashCode()));
        result = ((result* 31)+((this.disabled == null)? 0 :this.disabled.hashCode()));
        result = ((result* 31)+((this.metrics == null)? 0 :this.metrics.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof V10) == false) {
            return false;
        }
        V10 rhs = ((V10) other);
        return (((((((((((((((this.metadata == rhs.metadata)||((this.metadata!= null)&&this.metadata.equals(rhs.metadata)))&&((this.macros == rhs.macros)||((this.macros!= null)&&this.macros.equals(rhs.macros))))&&((this.sources == rhs.sources)||((this.sources!= null)&&this.sources.equals(rhs.sources))))&&((this.groups == rhs.groups)||((this.groups!= null)&&this.groups.equals(rhs.groups))))&&((this.childMap == rhs.childMap)||((this.childMap!= null)&&this.childMap.equals(rhs.childMap))))&&((this.selectors == rhs.selectors)||((this.selectors!= null)&&this.selectors.equals(rhs.selectors))))&&((this.exposures == rhs.exposures)||((this.exposures!= null)&&this.exposures.equals(rhs.exposures))))&&((this.nodes == rhs.nodes)||((this.nodes!= null)&&this.nodes.equals(rhs.nodes))))&&((this.groupMap == rhs.groupMap)||((this.groupMap!= null)&&this.groupMap.equals(rhs.groupMap))))&&((this.semanticModels == rhs.semanticModels)||((this.semanticModels!= null)&&this.semanticModels.equals(rhs.semanticModels))))&&((this.docs == rhs.docs)||((this.docs!= null)&&this.docs.equals(rhs.docs))))&&((this.parentMap == rhs.parentMap)||((this.parentMap!= null)&&this.parentMap.equals(rhs.parentMap))))&&((this.disabled == rhs.disabled)||((this.disabled!= null)&&this.disabled.equals(rhs.disabled))))&&((this.metrics == rhs.metrics)||((this.metrics!= null)&&this.metrics.equals(rhs.metrics))));
    }

}
