import {
    Background,
    ControlButton,
    Controls,
    MiniMap,
    Node,
    ReactFlow,
    NodeTypes,
} from '@xyflow/react';
import { type MouseEvent as ReactMouseEvent, useEffect } from 'react';
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
}

type JsEvent = {
    event: JsEventType;
    data: object
}

export default function Flow() {
    const {
        nodes,
        setActiveNode,
        onNodesChange,
        edges,
        onEdgesChange,
        setLineageInfo,
        renderCompleteLineage,
        setRenderCompleteLineage,
    } = useLineageLayout();


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
    }, [nodes.length, setActiveNode, setLineageInfo]);

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
                    <input type="checkbox" checked={renderCompleteLineage} onChange={() => setRenderCompleteLineage(complete => !complete)}/>
                    <span className="checkbox-label">Show full lineage</span>
                </label>
            </Controls>
        </ReactFlow>
    );
}