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
        // javascript -> kotlin bridge functions (set by the Kotlin code)
        kotlinCallback: (jsonEncodedValue: string) => void;
        // kotlin -> javascript bridge functions (set by the JavaScript code)
        setLineageInfo?: (info: LineageInfo) => void;
        setActiveNode?: (nodeId: string) => void;
    }
}

const nodeTypes = {
    'dbtModel': DbtModelNode,
} satisfies NodeTypes;

enum JsEventType {
    NODE_CLICK = 'NODE_CLICK',
    REFRESH_CLICK = 'REFRESH_CLICK',
    SHOW_COMPLETE_LINEAGE_CLICK = 'SHOW_COMPLETE_LINEAGE_CLICK'
}

type JsEvent = {
    event: JsEventType;
    data: object
}

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

    function stringifyEvent(event: JsEvent): string {
        return JSON.stringify(event);
    }

    async function onNodeClick(_event: ReactMouseEvent, node: Node) : Promise<void> {
        await setActiveNode(node.id);
        if (isDevMode) {
            return;
        }
        if (node.data && node.data.relativePath) {
            const event: JsEvent = {
                event: JsEventType.NODE_CLICK,
                data: {
                    node: node.data.relativePath
                }
            };
            window.kotlinCallback(stringifyEvent(event));
        }
    }

    function onRefreshClick() {
        if (isDevMode) {
            return;
        }
        const event: JsEvent = {
            event: JsEventType.REFRESH_CLICK,
            data: {}
        };
        window.kotlinCallback(stringifyEvent(event));
    }

    function completeLineageToggle() {
        const newState = !showCompleteLineage;
        const event: JsEvent = {
            event: JsEventType.SHOW_COMPLETE_LINEAGE_CLICK,
            data: {
                completeLineage: newState
            }
        };
        window.kotlinCallback(stringifyEvent(event));
        if (!newState) {
            filterNodesEdgesDownToUpstreamDownstream();
        }
        setShowCompleteLineage(newState);
    }

    function filterNodesEdgesDownToUpstreamDownstream() {
        const currentNode = nodes.find(n => n.data.isSelected);
        if (currentNode?.id != null) {
            const visitedUpstream = new Set<string>();
            const visitedDownStream = new Set<string>();
            const upStreamEdges = findAncestors(currentNode.id, edges, visitedUpstream);
            const downStreamEdges = findChildren(currentNode.id, edges, visitedDownStream);
            const upDownStreamEdges = [...upStreamEdges, ...downStreamEdges];
            const newEdges = edges.filter(e => upDownStreamEdges.includes(e.source) || upDownStreamEdges.includes(e.target));
            const newNodes = nodes.filter(n => upDownStreamEdges.includes(n.id) || n.data.isSelected);
            setEdges(newEdges);
            setNodes(newNodes);
        }
    }

    function findAncestors(nodeId: string, edges: Edge[], visited: Set<string> = new Set()): string[] {
        if (visited.has(nodeId)) return [];
        visited.add(nodeId);

        const ancestors = edges
            .filter(edge => edge.target === nodeId)
            .map(edge => edge.source);

        return ancestors.reduce((acc, ancestor) => {
            return acc.concat(ancestor, findAncestors(ancestor, edges, visited));
        }, [] as string[]);
    }

    function findChildren(nodeId: string, edges: Edge[], visited: Set<string> = new Set()): string[] {
        if (visited.has(nodeId)) return [];
        visited.add(nodeId);

        const children = edges
            .filter(edge => edge.source === nodeId)
            .map(edge => edge.target);

        return children.reduce((acc, child) => {
            return acc.concat(child, findChildren(child, edges, visited));
        }, [] as string[]);
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
                    <input type="checkbox" checked={showCompleteLineage} onChange={completeLineageToggle}/>
                    <span className="checkbox-label">Show full lineage</span>
                </label>
            </Controls>
        </ReactFlow>
    );
}