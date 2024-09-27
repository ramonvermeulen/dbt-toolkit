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
    useNodesState, NodeTypes, useReactFlow,
} from '@xyflow/react';
import { type MouseEvent as ReactMouseEvent, useCallback, useEffect, useState } from 'react';
import { MdRefresh } from 'react-icons/md';

import { isDevMode } from './constants.ts';
import { edgeTypes } from './edges';
import { useLineageLayout } from './hooks/useLineageLayout.ts';
import { DbtModelNode } from './nodes/DbtModelNode.tsx';
import { LineageInfo } from './types.ts';



declare global {
    interface Window {
        // javascript -> kotlin bridge functions (set by the kotlin code)
        kotlinCallback: (jsonEncodedValue: string) => void;
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
    const reactFlow = useReactFlow();
    const [showCompleteLineage, setShowCompleteLineage] = useState(false);
    const { setLineageInfo } = useLineageLayout({ setNodes, setEdges, addEdge });

    const setActiveNode = useCallback(async (nodeId: string) => {
        const newNodes = nodes.map(n => ({
            ...n,
            data: {
                ...n.data,
                isSelected: n.id === nodeId,
            }
        }));
        setNodes(newNodes);
        const newActiveNode = nodes.find(n => n.id === nodeId);
        if (newActiveNode) {
            await reactFlow.setCenter(newActiveNode.position.x, newActiveNode.position.y, { duration: 100, zoom: reactFlow.getZoom() });
        }
    }, [reactFlow, nodes, setNodes]);


    async function onNodeClick(_event: ReactMouseEvent, node: Node) : Promise<void> {
        await setActiveNode(node.id);
        if (isDevMode) {
            return;
        }
        window.kotlinCallback(JSON.stringify({
            node: node.data?.relativePath,
        }));
    }

    function onRefreshClick() {
        if (isDevMode) {
            return;
        }
        window.kotlinCallback(JSON.stringify({
            refresh: true,
        }));
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
                nodeColor={'#CECECE'}
            />

            <Controls>
                <ControlButton onClick={onRefreshClick}>
                    <MdRefresh size={14}/>
                </ControlButton>
                <label className="fancy-checkbox">
                    <input type="checkbox" checked={showCompleteLineage}
                        onChange={() => {
                            window.kotlinCallback(JSON.stringify({
                                showCompleteLineage: !showCompleteLineage
                            }));
                            setShowCompleteLineage(!showCompleteLineage);
                        }}/>
                    <span className="checkbox-label">Show full lineage</span>
                </label>
            </Controls>
        </ReactFlow>
    );
}