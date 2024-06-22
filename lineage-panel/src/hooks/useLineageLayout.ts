import dagre from 'dagre';
import { Dispatch, SetStateAction, useCallback, useMemo } from 'react';
import { Edge, Node, Position, MarkerType, Connection } from 'reactflow';

import { LineageInfo } from '../types';
import { DEdge, DNode } from '../types.ts';

const dagreGraph = new dagre.graphlib.Graph();
dagreGraph.setDefaultEdgeLabel(() => ({}));

const nodeWidth = 200;
const nodeHeight = 50;

type UseLineageLayoutProps = {
    setNodes: Dispatch<SetStateAction<Node[]>>;
    setEdges: Dispatch<SetStateAction<Edge[]>>;
    addEdge: (edgeParams: Edge | Connection, edges: Edge[]) => Edge[];
};

export const useLineageLayout = ({ setNodes, setEdges, addEdge }: UseLineageLayoutProps) => {
    const configureNodes = useMemo(() => {
        return (info: LineageInfo): Node[] => {
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
        };
    }, []);

    const configureEdges = useMemo(() => {
        return (info: LineageInfo): Edge[] => {
            return info.edges.map((edge: DEdge) => ({
                id: `${edge.parent}-${edge.child}`,
                source: edge.parent,
                target: edge.child,
                markerEnd: {
                    type: MarkerType.ArrowClosed,
                },
            }));
        };
    }, []);

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
    }, [configureNodes, configureEdges]);

    const setLineageInfo = useCallback((info: LineageInfo) => {
        const { nodes: layoutedNodes, edges: layoutedEdges } = getLayoutElements(info);
        setNodes(layoutedNodes);
        layoutedEdges.forEach(layoutEdge => setEdges((edges) => addEdge(layoutEdge, edges)));
    }, [getLayoutElements, setNodes, setEdges, addEdge]);

    return { setLineageInfo };
};