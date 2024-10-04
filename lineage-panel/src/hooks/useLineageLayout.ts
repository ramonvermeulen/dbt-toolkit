import { Edge, Node, Position, MarkerType, addEdge, useReactFlow, useNodesState, useEdgesState } from '@xyflow/react';
import dagre from 'dagre';
import { useCallback, useEffect, useMemo, useState } from 'react';

import { LineageInfo } from '../types';
import { DEdge, DNode } from '../types.ts';

const nodeWidth = 200;
const nodeHeight = 50;

export const useLineageLayout = () => {
    const [lineageInfo, setLineageInfo] = useState<LineageInfo>({ nodes: [], edges: [] });
    const [renderCompleteLineage, setRenderCompleteLineage] = useState(false);
    const [nodes, setNodes, onNodesChange] = useNodesState<Node>([]);
    const [edges, setEdges, onEdgesChange] = useEdgesState<Edge>([]);
    const reactFlow = useReactFlow();

    const filterNodesEdges = useMemo(() => {
        return (info: LineageInfo) => {
            const currentNode = info.nodes.find(n => n.isSelected);
            if (!currentNode) return { newNodes: info.nodes, newEdges: info.edges };

            const traverse = (nodeId: string, direction: 'up' | 'down'): string[] => {
                const visited = new Set<string>();
                const stack = [nodeId];
                const result = [];

                while (stack.length) {
                    const current = stack.pop();
                    if (!current || visited.has(current)) continue;
                    visited.add(current);
                    result.push(current);

                    const relatedNodes = info.edges
                        .filter(edge => direction === 'up' ? edge.parent === current : edge.child === current)
                        .map(edge => direction === 'up' ? edge.child : edge.parent);

                    stack.push(...relatedNodes);
                }

                return result;
            };

            const upStreamEdges = traverse(currentNode.id, 'up');
            const downStreamEdges = traverse(currentNode.id, 'down');
            const upDownStreamEdges = new Set([...upStreamEdges, ...downStreamEdges]);
            const newEdges = info.edges.filter(e => upDownStreamEdges.has(e.child) || upDownStreamEdges.has(e.parent));
            const newNodes = info.nodes.filter(n => upDownStreamEdges.has(n.id) || n.isSelected);

            return { newNodes, newEdges };
        };
    }, []);

    const mapNodes = (nodes: DNode[]): Node[] => {
        return nodes.map((node: DNode) => ({
            id: node.id,
            position: { x: 0, y: 0 },
            data: {
                label: node.id,
                isSelected: node.isSelected,
                relativePath: node.relativePath,
            },
            type: 'dbtModel',
        }));
    };

    const mapEdges = (edges: DEdge[]): Edge[] => {
        return edges.map((edge: DEdge) => ({
            id: `${edge.parent}-${edge.child}`,
            source: edge.parent,
            target: edge.child,
            markerEnd: {
                type: MarkerType.ArrowClosed,
            },
        }));
    };

    const configureNodes = useMemo(() => {
        return (info: LineageInfo): Node[] => {
            return renderCompleteLineage ? mapNodes(info.nodes) : mapNodes(filterNodesEdges(info).newNodes);
        };
    }, [renderCompleteLineage, filterNodesEdges]);

    const configureEdges = useMemo(() => {
        return (info: LineageInfo): Edge[] => {
            return renderCompleteLineage ? mapEdges(info.edges) : mapEdges(filterNodesEdges(info).newEdges);
        };
    }, [renderCompleteLineage, filterNodesEdges]);

    const getLayoutElements = useMemo(() => {
        return (info: LineageInfo) => {
            const dagreGraph = new dagre.graphlib.Graph();
            dagreGraph.setDefaultEdgeLabel(() => ({}));
            dagreGraph.setGraph({ rankdir: 'LR' });

            const nodes = configureNodes(info);
            const edges = configureEdges(info);

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
    }, [configureNodes, configureEdges]);

    const setActiveNode = useCallback(async (nodeId: string) => {
        const newLineageInfo: LineageInfo = {
            edges: lineageInfo.edges,
            nodes: lineageInfo.nodes.map(n => ({
                ...n,
                isSelected: n.id === nodeId,
            })),
        };
        setLineageInfo(newLineageInfo);

    }, [lineageInfo.nodes, lineageInfo.edges]);

    useEffect(() => {
        if (lineageInfo) {
            const { nodes: layoutedNodes, edges: layoutedEdges } = getLayoutElements(lineageInfo);
            setNodes(layoutedNodes);
            layoutedEdges.forEach(layoutEdge => setEdges((edges) => addEdge(layoutEdge, edges)));
            const newActiveNode = layoutedNodes.find(n => n.data.isSelected);
            if (newActiveNode) {
                reactFlow.setCenter(newActiveNode.position.x, newActiveNode.position.y, { duration: 300, zoom: reactFlow.getZoom() });
            }
        }
    }, [getLayoutElements, reactFlow, setEdges, setNodes, renderCompleteLineage, lineageInfo]);

    return {
        nodes,
        setActiveNode,
        onNodesChange,
        edges,
        onEdgesChange,
        setLineageInfo,
        renderCompleteLineage,
        setRenderCompleteLineage,
    };
};