import { Edge, Node, Position, MarkerType, addEdge, useReactFlow, useNodesState, useEdgesState } from '@xyflow/react';
import dagre from 'dagre';
import { useCallback, useEffect, useMemo, useState } from 'react';

import { LineageInfo } from '../types';
import { DEdge, DNode } from '../types.ts';

const dagreGraph = new dagre.graphlib.Graph();
dagreGraph.setDefaultEdgeLabel(() => ({}));

const nodeWidth = 200;
const nodeHeight = 50;

export const useLineageLayout = () => {
    const [lineageInfo, setLineageInfo] = useState<LineageInfo | undefined>();
    const [renderCompleteLineage, setRenderCompleteLineage] = useState(false);
    const [nodes, setNodes, onNodesChange] = useNodesState<Node>([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState<Edge>([]);
    const reactFlow = useReactFlow();

    const filterNodesEdges = useMemo(() => {
        const currentNode = nodes.find(n => n.data.isSelected);
        if (!currentNode) return { newNodes: nodes, newEdges: edges };

        const visited = new Set<string>();
        const traverse = (nodeId: string, direction: 'up' | 'down'): string[] => {
            if (visited.has(nodeId)) return [];
            visited.add(nodeId);

            const relatedNodes = edges
                .filter(edge => direction === 'up' ? edge.target === nodeId : edge.source === nodeId)
                .map(edge => direction === 'up' ? edge.source : edge.target);

            return relatedNodes.reduce((acc, relatedNode) => {
                return acc.concat(relatedNode, traverse(relatedNode, direction));
            }, [] as string[]);
        };

        const upStreamEdges = traverse(currentNode.id, 'up');
        const downStreamEdges = traverse(currentNode.id, 'down');
        const upDownStreamEdges = [...upStreamEdges, ...downStreamEdges];
        const newEdges = edges.filter(e => upDownStreamEdges.includes(e.source) || upDownStreamEdges.includes(e.target));
        const newNodes = nodes.filter(n => upDownStreamEdges.includes(n.id) || n.data.isSelected);

        return { newNodes, newEdges };
    }, [nodes, edges]);

    const configureNodes = useMemo(() => {
        return (info: LineageInfo): Node[] => {
            if (renderCompleteLineage) {
                return info.nodes.map((node: DNode) => ({
                    id: node.id,
                    position: { x: 0, y: 0 },
                    data: {
                        label: node.id,
                        isSelected: node.isSelected,
                        relativePath: node.relativePath,
                    },
                    type: 'dbtModel',
                }));
            } else {
                return filterNodesEdges.newNodes;
            }
        };
    }, [renderCompleteLineage, filterNodesEdges]);

    const configureEdges = useMemo(() => {
        return (info: LineageInfo): Edge[] => {
            if (renderCompleteLineage) {
                return info.edges.map((edge: DEdge) => ({
                    id: `${edge.parent}-${edge.child}`,
                    source: edge.parent,
                    target: edge.child,
                    markerEnd: {
                        type: MarkerType.ArrowClosed,
                    },
                }));
            } else {
                return filterNodesEdges.newEdges;
            }
        };
    }, [renderCompleteLineage, filterNodesEdges]);

    const getLayoutElements = useMemo(() => {
        return (info: LineageInfo) => {
            const nodes = configureNodes(info);
            const edges = configureEdges(info);

            dagreGraph.setGraph({ rankdir: 'LR' });

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

                node.position = {
                    x: nodeWithPosition.x - nodeWidth / 2,
                    y: nodeWithPosition.y - nodeHeight / 2,
                };

                return node;
            });

            return { nodes, edges };
        };
    }, [lineageInfo, renderCompleteLineage]);

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

    useEffect(() => {
        if (lineageInfo) {
            const { nodes: layoutedNodes, edges: layoutedEdges } = getLayoutElements(lineageInfo);
            setNodes(layoutedNodes);
            layoutedEdges.forEach(layoutEdge => setEdges((edges) => addEdge(layoutEdge, edges)));
            setTimeout(() => reactFlow.fitView({ duration: 300 }), 500);
        }
    }, [renderCompleteLineage, lineageInfo]);

    return {
        nodes,
        setActiveNode,
        onNodesChange,
        edges,
        onEdgesChange,
        processLineageInfo: setLineageInfo,
        renderCompleteLineage,
        setRenderCompleteLineage,
    };
};