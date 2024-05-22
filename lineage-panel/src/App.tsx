import {OnConnect, Position} from "reactflow";

import {useCallback, useEffect} from "react";
import {
    Background,
    Controls,
    MiniMap,
    ReactFlow,
    addEdge,
    useNodesState,
    useEdgesState,
} from "reactflow";

import "reactflow/dist/style.css";

import {initialNodes, LineageInfo, nodeTypes} from "./nodes";
import {initialEdges, edgeTypes} from "./edges";

export default function App() {
    const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
    const onConnect: OnConnect = useCallback(
        (connection) => setEdges((edges) => addEdge(connection, edges)),
        [setEdges]
    );

    function configureNodes(info: LineageInfo) {
        const newNodes = [];
        const baseX = 0; // base x position
        const baseY = 0; // base y position
        const offset = 100; // offset for each node

        // Add the main node
        newNodes.push({
            id: info.node,
            position: {x: baseX, y: baseY},
            data: {label: info.node},
            sourcePosition: Position.Right,
            targetPosition: Position.Left,
        });

        // Add children nodes to the left
        info.children.forEach((child, index) => {
            newNodes.push({
                id: child,
                type: "input",
                sourcePosition: Position.Right,
                position: {x: baseX - 500, y: baseY + (offset * index)},
                data: {label: child},
            });
        });

        // Add parent nodes to the right
        info.parents.forEach((parent, index) => {
          if (!parent.includes("test")) {
            newNodes.push({
              id: parent,
              type: "output",
              position: {x: baseX + 500, y: baseY + (offset * index)},
              data: {label: parent},
              targetPosition: Position.Left,
            });
          }
        });

        // Update the nodes state
        setNodes(newNodes);
    }

    function configureEdges(info: LineageInfo) {
        info.children.map((child) => {
            const edge = {
                id: `${info.node}-${child}`,
                source: child,
                target: info.node,
                type: "smoothstep",
            };
            setEdges((edges) => addEdge(edge, edges));
        });
        info.parents.map((parent) => {
            const edge = {
                id: `${parent}-${info.node}`,
                source: info.node,
                target: parent,
                type: "smoothstep",
            };
            setEdges((edges) => addEdge(edge, edges));
        });
    }

    function testKotlinRuntimeCall(info: LineageInfo) {
        console.log(info);
        configureNodes(info);
        configureEdges(info);
    }

    useEffect(() => {
        // making function available from the browser console
        (window as any).testKotlinRuntimeCall = testKotlinRuntimeCall;

        return () => {
            (window as any).testKotlinRuntimeCall = undefined;
        }
    });

    return (
        <ReactFlow
            nodes={nodes}
            nodeTypes={nodeTypes}
            onNodesChange={onNodesChange}
            edges={edges}
            edgeTypes={edgeTypes}
            onEdgesChange={onEdgesChange}
            onConnect={onConnect}
            nodesConnectable={false}
            proOptions={{ hideAttribution: true }}
            fitView
            attributionPosition="bottom-left"
        >
            <Background/>
            <MiniMap/>
            <Controls/>
        </ReactFlow>
    );
}
