import {
    Background,
    ControlButton,
    Controls,
    MiniMap,
    Node,
    Edge,
    ReactFlow,
    addEdge,
    useEdgesState,
    useNodesState, NodeTypes,
} from '@xyflow/react';
import { type MouseEvent as ReactMouseEvent, useCallback, useEffect } from 'react';
import { MdRefresh } from 'react-icons/md';

import { isDevMode } from './constants.ts';
import { edgeTypes } from './edges';
import { useLineageLayout } from './hooks/useLineageLayout.ts';
import { DbtModelNode } from './nodes/DbtModelNode.tsx';
import { LineageInfo } from './types.ts';



declare global {
    interface Window {
        // javascript -> kotlin bridge functions (set by the kotlin code)
        kotlinCallback: (value: unknown) => void;
        // kotlin -> javascript bridge functions (set by the JavaScript code)
        setLineageInfo?: (info: LineageInfo) => void;
        setActiveNode?: (nodeId: string) => void;
    }
}

const nodeTypes = {
    'dbtModel': DbtModelNode,
} satisfies NodeTypes;

export default function Flow() {
    const [nodes, setNodes, onNodesChange] = useNodesState<Node>([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState<Edge>([]);
    const { setLineageInfo } = useLineageLayout({ setNodes, setEdges, addEdge });

    const setActiveNode = useCallback((nodeId: string) => {
        const newNodes = nodes.map(n => ({
            ...n,
            data: {
                ...n.data,
                isSelected: n.id === nodeId,
            }
        }));
        setNodes(newNodes);
    }, [nodes, setNodes]);


    function onNodeClick(_event: ReactMouseEvent, node: Node) : void {
        setActiveNode(node.id);
        if (isDevMode) {
            return;
        }
        window.kotlinCallback(node.data?.relativePath);
    }

    function onRefreshClick() {
        if (isDevMode) {
            return;
        }
        window.kotlinCallback('refresh');
    }

    useEffect(() => {
        if (isDevMode && nodes.length === 0) {
            fetch('./test-data.json').then(response => response.json()).then(data => {
                setLineageInfo(data);
            });
        }

        if (!window.setLineageInfo || !window.setActiveNode) {
            (window as Window).setLineageInfo = setLineageInfo;
            (window as Window).setActiveNode = setActiveNode;
        }
        return () => {
            (window as Window).setLineageInfo = undefined;
            (window as Window).setActiveNode = undefined;
        };
    });

    /*
     @ts-expect-error - this is a known issue with the types
    */
    return (
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
                fitView={true}
                fitViewOptions={{
                    duration: 300,
                }}
                autoPanOnConnect={true}
                edgesFocusable={false}
            >
                <Background color="#E9E3E6"/>
                <MiniMap
                    pannable={true}
                    zoomable={true}
                    inversePan={false}
                    zoomStep={1}
                    offsetScale={1}
                    nodeColor={"#CECECE"}
                />
                <Controls>
                    <ControlButton onClick={onRefreshClick}>
                        <MdRefresh size={15} />
                    </ControlButton>
                </Controls>
            </ReactFlow>
    );
}