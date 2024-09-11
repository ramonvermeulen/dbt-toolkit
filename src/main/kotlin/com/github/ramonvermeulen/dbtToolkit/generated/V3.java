import java.io.Serializable;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * WritableManifest(metadata: dbt.contracts.graph.manifest.ManifestMetadata, nodes: Mapping[str, Union[dbt.contracts.graph.compiled.CompiledAnalysisNode, dbt.contracts.graph.compiled.CompiledDataTestNode, dbt.contracts.graph.compiled.CompiledModelNode, dbt.contracts.graph.compiled.CompiledHookNode, dbt.contracts.graph.compiled.CompiledRPCNode, dbt.contracts.graph.compiled.CompiledSchemaTestNode, dbt.contracts.graph.compiled.CompiledSeedNode, dbt.contracts.graph.compiled.CompiledSnapshotNode, dbt.contracts.graph.parsed.ParsedAnalysisNode, dbt.contracts.graph.parsed.ParsedDataTestNode, dbt.contracts.graph.parsed.ParsedHookNode, dbt.contracts.graph.parsed.ParsedModelNode, dbt.contracts.graph.parsed.ParsedRPCNode, dbt.contracts.graph.parsed.ParsedSchemaTestNode, dbt.contracts.graph.parsed.ParsedSeedNode, dbt.contracts.graph.parsed.ParsedSnapshotNode]], sources: Mapping[str, dbt.contracts.graph.parsed.ParsedSourceDefinition], macros: Mapping[str, dbt.contracts.graph.parsed.ParsedMacro], docs: Mapping[str, dbt.contracts.graph.parsed.ParsedDocumentation], exposures: Mapping[str, dbt.contracts.graph.parsed.ParsedExposure], selectors: Mapping[str, Any], disabled: Union[List[Union[dbt.contracts.graph.compiled.CompiledAnalysisNode, dbt.contracts.graph.compiled.CompiledDataTestNode, dbt.contracts.graph.compiled.CompiledModelNode, dbt.contracts.graph.compiled.CompiledHookNode, dbt.contracts.graph.compiled.CompiledRPCNode, dbt.contracts.graph.compiled.CompiledSchemaTestNode, dbt.contracts.graph.compiled.CompiledSeedNode, dbt.contracts.graph.compiled.CompiledSnapshotNode, dbt.contracts.graph.parsed.ParsedAnalysisNode, dbt.contracts.graph.parsed.ParsedDataTestNode, dbt.contracts.graph.parsed.ParsedHookNode, dbt.contracts.graph.parsed.ParsedModelNode, dbt.contracts.graph.parsed.ParsedRPCNode, dbt.contracts.graph.parsed.ParsedSchemaTestNode, dbt.contracts.graph.parsed.ParsedSeedNode, dbt.contracts.graph.parsed.ParsedSnapshotNode, dbt.contracts.graph.parsed.ParsedSourceDefinition]], NoneType], parent_map: Union[Dict[str, List[str]], NoneType], child_map: Union[Dict[str, List[str]], NoneType])
 * 
 */
@Generated("jsonschema2pojo")
public class V3 implements Serializable
{

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    @SerializedName("metadata")
    @Expose
    private ManifestMetadata__3 metadata;
    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("nodes")
    @Expose
    private Nodes__5 nodes;
    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("sources")
    @Expose
    private Sources__5 sources;
    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("macros")
    @Expose
    private Macros__5 macros;
    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("docs")
    @Expose
    private Docs__5 docs;
    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    @SerializedName("exposures")
    @Expose
    private Exposures__5 exposures;
    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    @SerializedName("selectors")
    @Expose
    private Selectors__5 selectors;
    /**
     * A list of the disabled nodes in the target
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
    private final static long serialVersionUID = -1065639011127298726L;

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    public ManifestMetadata__3 getMetadata() {
        return metadata;
    }

    /**
     * Metadata for the manifest.
     * (Required)
     * 
     */
    public void setMetadata(ManifestMetadata__3 metadata) {
        this.metadata = metadata;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Nodes__5 getNodes() {
        return nodes;
    }

    /**
     * The nodes defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setNodes(Nodes__5 nodes) {
        this.nodes = nodes;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Sources__5 getSources() {
        return sources;
    }

    /**
     * The sources defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setSources(Sources__5 sources) {
        this.sources = sources;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Macros__5 getMacros() {
        return macros;
    }

    /**
     * The macros defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setMacros(Macros__5 macros) {
        this.macros = macros;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Docs__5 getDocs() {
        return docs;
    }

    /**
     * The docs defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setDocs(Docs__5 docs) {
        this.docs = docs;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public Exposures__5 getExposures() {
        return exposures;
    }

    /**
     * The exposures defined in the dbt project and its dependencies
     * (Required)
     * 
     */
    public void setExposures(Exposures__5 exposures) {
        this.exposures = exposures;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public Selectors__5 getSelectors() {
        return selectors;
    }

    /**
     * The selectors defined in selectors.yml
     * (Required)
     * 
     */
    public void setSelectors(Selectors__5 selectors) {
        this.selectors = selectors;
    }

    /**
     * A list of the disabled nodes in the target
     * 
     */
    public Object getDisabled() {
        return disabled;
    }

    /**
     * A list of the disabled nodes in the target
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
        sb.append(V3 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        result = ((result* 31)+((this.selectors == null)? 0 :this.selectors.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof V3) == false) {
            return false;
        }
        V3 rhs = ((V3) other);
        return (((((((((((this.exposures == rhs.exposures)||((this.exposures!= null)&&this.exposures.equals(rhs.exposures)))&&((this.metadata == rhs.metadata)||((this.metadata!= null)&&this.metadata.equals(rhs.metadata))))&&((this.macros == rhs.macros)||((this.macros!= null)&&this.macros.equals(rhs.macros))))&&((this.nodes == rhs.nodes)||((this.nodes!= null)&&this.nodes.equals(rhs.nodes))))&&((this.sources == rhs.sources)||((this.sources!= null)&&this.sources.equals(rhs.sources))))&&((this.docs == rhs.docs)||((this.docs!= null)&&this.docs.equals(rhs.docs))))&&((this.parentMap == rhs.parentMap)||((this.parentMap!= null)&&this.parentMap.equals(rhs.parentMap))))&&((this.childMap == rhs.childMap)||((this.childMap!= null)&&this.childMap.equals(rhs.childMap))))&&((this.disabled == rhs.disabled)||((this.disabled!= null)&&this.disabled.equals(rhs.disabled))))&&((this.selectors == rhs.selectors)||((this.selectors!= null)&&this.selectors.equals(rhs.selectors))));
    }

}
