import java.io.Serializable;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * WritableManifest(metadata: dbt.contracts.graph.manifest.ManifestMetadata, nodes: Mapping[str, Union[dbt.contracts.graph.nodes.AnalysisNode, dbt.contracts.graph.nodes.SingularTestNode, dbt.contracts.graph.nodes.HookNode, dbt.contracts.graph.nodes.ModelNode, dbt.contracts.graph.nodes.RPCNode, dbt.contracts.graph.nodes.SqlNode, dbt.contracts.graph.nodes.GenericTestNode, dbt.contracts.graph.nodes.SnapshotNode, dbt.contracts.graph.nodes.SeedNode]], sources: Mapping[str, dbt.contracts.graph.nodes.SourceDefinition], macros: Mapping[str, dbt.contracts.graph.nodes.Macro], docs: Mapping[str, dbt.contracts.graph.nodes.Documentation], exposures: Mapping[str, dbt.contracts.graph.nodes.Exposure], metrics: Mapping[str, dbt.contracts.graph.nodes.Metric], selectors: Mapping[str, Any], disabled: Optional[Mapping[str, List[Union[dbt.contracts.graph.nodes.AnalysisNode, dbt.contracts.graph.nodes.SingularTestNode, dbt.contracts.graph.nodes.HookNode, dbt.contracts.graph.nodes.ModelNode, dbt.contracts.graph.nodes.RPCNode, dbt.contracts.graph.nodes.SqlNode, dbt.contracts.graph.nodes.GenericTestNode, dbt.contracts.graph.nodes.SnapshotNode, dbt.contracts.graph.nodes.SeedNode, dbt.contracts.graph.nodes.SourceDefinition]]]], parent_map: Optional[Dict[str, List[str]]], child_map: Optional[Dict[str, List[str]]])
 * 
 */
@Generated("jsonschema2pojo")
public class V8 implements Serializable
{

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    @SerializedName("metadata")
    @Expose
    private ManifestMetadata__8 metadata;
    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("nodes")
    @Expose
    private Nodes__10 nodes;
    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("sources")
    @Expose
    private Sources__10 sources;
    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("macros")
    @Expose
    private Macros__10 macros;
    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("docs")
    @Expose
    private Docs__10 docs;
    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("exposures")
    @Expose
    private Exposures__10 exposures;
    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("metrics")
    @Expose
    private Metrics__7 metrics;
    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    @SerializedName("selectors")
    @Expose
    private Selectors__10 selectors;
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
    private final static long serialVersionUID = -5050146674833912482L;

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    public ManifestMetadata__8 getMetadata() {
        return metadata;
    }

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    public void setMetadata(ManifestMetadata__8 metadata) {
        this.metadata = metadata;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Nodes__10 getNodes() {
        return nodes;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setNodes(Nodes__10 nodes) {
        this.nodes = nodes;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Sources__10 getSources() {
        return sources;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setSources(Sources__10 sources) {
        this.sources = sources;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Macros__10 getMacros() {
        return macros;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMacros(Macros__10 macros) {
        this.macros = macros;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Docs__10 getDocs() {
        return docs;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setDocs(Docs__10 docs) {
        this.docs = docs;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Exposures__10 getExposures() {
        return exposures;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setExposures(Exposures__10 exposures) {
        this.exposures = exposures;
    }

    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Metrics__7 getMetrics() {
        return metrics;
    }

    /**
     * The metrics defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMetrics(Metrics__7 metrics) {
        this.metrics = metrics;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public Selectors__10 getSelectors() {
        return selectors;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public void setSelectors(Selectors__10 selectors) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V8 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        result = ((result* 31)+((this.exposures == null)? 0 :this.exposures.hashCode()));
        result = ((result* 31)+((this.metadata == null)? 0 :this.metadata.hashCode()));
        result = ((result* 31)+((this.macros == null)? 0 :this.macros.hashCode()));
        result = ((result* 31)+((this.nodes == null)? 0 :this.nodes.hashCode()));
        result = ((result* 31)+((this.sources == null)? 0 :this.sources.hashCode()));
        result = ((result* 31)+((this.docs == null)? 0 :this.docs.hashCode()));
        result = ((result* 31)+((this.parentMap == null)? 0 :this.parentMap.hashCode()));
        result = ((result* 31)+((this.childMap == null)? 0 :this.childMap.hashCode()));
        result = ((result* 31)+((this.disabled == null)? 0 :this.disabled.hashCode()));
        result = ((result* 31)+((this.metrics == null)? 0 :this.metrics.hashCode()));
        result = ((result* 31)+((this.selectors == null)? 0 :this.selectors.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof V8) == false) {
            return false;
        }
        V8 rhs = ((V8) other);
        return ((((((((((((this.exposures == rhs.exposures)||((this.exposures!= null)&&this.exposures.equals(rhs.exposures)))&&((this.metadata == rhs.metadata)||((this.metadata!= null)&&this.metadata.equals(rhs.metadata))))&&((this.macros == rhs.macros)||((this.macros!= null)&&this.macros.equals(rhs.macros))))&&((this.nodes == rhs.nodes)||((this.nodes!= null)&&this.nodes.equals(rhs.nodes))))&&((this.sources == rhs.sources)||((this.sources!= null)&&this.sources.equals(rhs.sources))))&&((this.docs == rhs.docs)||((this.docs!= null)&&this.docs.equals(rhs.docs))))&&((this.parentMap == rhs.parentMap)||((this.parentMap!= null)&&this.parentMap.equals(rhs.parentMap))))&&((this.childMap == rhs.childMap)||((this.childMap!= null)&&this.childMap.equals(rhs.childMap))))&&((this.disabled == rhs.disabled)||((this.disabled!= null)&&this.disabled.equals(rhs.disabled))))&&((this.metrics == rhs.metrics)||((this.metrics!= null)&&this.metrics.equals(rhs.metrics))))&&((this.selectors == rhs.selectors)||((this.selectors!= null)&&this.selectors.equals(rhs.selectors))));
    }

}
