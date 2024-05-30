import {
    addEdge,
    Background,
    Controls,
    Edge,
    MarkerType,
    MiniMap,
    Node,
    Position,
    ReactFlow,
    useEdgesState,
    useNodesState
} from "reactflow";

import {type MouseEvent as ReactMouseEvent, useEffect} from "react";

import "reactflow/dist/style.css";

import {LineageInfo} from "./nodes";
import {edgeTypes} from "./edges";
import dagre from "dagre";
import {DbtModelNode} from "./nodes/DbtModelNode.tsx";

declare global {
    interface Window {
        selectNode: (nodeId: string) => void;
        testKotlinRuntimeCall: (info: LineageInfo) => void;
    }
}

const dagreGraph = new dagre.graphlib.Graph();
dagreGraph.setDefaultEdgeLabel(() => ({}));

const nodeWidth = 200;
const nodeHeight = 50;

const getLayoutedElements = (nodes: Node[], edges: Edge[], direction = 'LR') => {
    dagreGraph.setGraph({ rankdir: direction });

    nodes.forEach((node) => {
        dagreGraph.setNode(node.id, { width: nodeWidth, height: nodeHeight });
    });

    edges.forEach((edge) => {
        dagreGraph.setEdge(edge.source, edge.target);
    });

    dagre.layout(dagreGraph);

    nodes.forEach((node) => {
        const nodeWithPosition = dagreGraph.node(node.id);
        node.targetPosition = Position.Left;
        node.sourcePosition = Position.Right;

        // We are shifting the dagre node position (anchor=center center) to the top left
        // so it matches the React Flow node anchor point (top left).
        node.position = {
            x: nodeWithPosition.x - nodeWidth / 2,
            y: nodeWithPosition.y - nodeHeight / 2,
        };

        return node;
    });

    return { nodes, edges };
};

const nodeTypes = {
    dbtModel: DbtModelNode
}

export default function App() {
    const [nodes, setNodes, onNodesChange] = useNodesState([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState([]);

    function configureNodes(info: LineageInfo): Node[] {
        const newNodes: Node[] = [];

        info.nodes.forEach((node) => {
            newNodes.push({
                id: node.id,
                position: {x: 0, y: 0},
                data: {
                    label: node.id,
                    isSelected: node.isSelected,
                    relativePath: node.relativePath,
                },
                type: "dbtModel",
            });
        });

        return newNodes
    }

    function configureEdges(info: LineageInfo): Edge[] {
        return info.edges.map((e) => {
            return {
                id: `${e.parent}-${e.child}`,
                source: e.parent,
                target: e.child,
                markerEnd: {
                    type: MarkerType.ArrowClosed,
                },
            };
        });
    }

    function setLineageInfo(info: LineageInfo) {
        console.log(info)
        const nodes = configureNodes(info)
        const edges = configureEdges(info)
        const { nodes: layoutedNodes, edges: layoutedEdges } = getLayoutedElements(
            nodes,
            edges
        );
        setNodes(layoutedNodes)
        layoutedEdges.forEach(layoutEdge => setEdges((edges) => addEdge(layoutEdge, edges)));
    }

    useEffect(() => {
        // making function available from the browser console
        (window as any).setLineageInfo = setLineageInfo;
        if (process.env.NODE_ENV === 'development') {
            fetch('./test-data.json').then(response => response.json()).then(data => {
                setLineageInfo(data);
            });
        }
        return () => {
            (window as any).setLineageInfo = undefined;
        }
    }, []);

    function onNodeClick(_event: ReactMouseEvent, node: Node) : void {
        // ts-ignore
        window?.selectNode(node.data?.relativePath);
    }

    return (
        <div style={{ height: "100vh", width: "100vw" }}>
            <ReactFlow
                nodes={nodes}
                nodeTypes={nodeTypes}
                onNodesChange={onNodesChange}
                edges={edges}
                edgeTypes={edgeTypes}
                onEdgesChange={onEdgesChange}
                onNodeClick={onNodeClick}
                nodesConnectable={false}
                proOptions={{ hideAttribution: true }}
                fitView
                autoPanOnConnect={true}
            >
                <Background color="#E9E3E6"/>
                <MiniMap/>
                <Controls/>
            </ReactFlow>
        </div>
    );
}
