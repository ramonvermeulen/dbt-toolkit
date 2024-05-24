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

import {useEffect} from "react";

import "reactflow/dist/style.css";

import {LineageInfo} from "./nodes";
import {edgeTypes} from "./edges";
import dagre from "dagre";
import {DbtModelNode} from "./nodes/DbtModelNode.tsx";

const testData = {
    "nodes": [
        {
            "id": "seed.jaffle_shop.raw_customers",
            "isSelected": false,
            "tests": []
        },
        {
            "id": "model.jaffle_shop.stg_customers",
            "isSelected": false,
            "tests": [
                "test.jaffle_shop.not_null_stg_customers_customer_id.e2cfb1f9aa",
                "test.jaffle_shop.unique_stg_customers_customer_id.c7614daada"
            ]
        },
        {
            "id": "seed.jaffle_shop.raw_payments",
            "isSelected": false,
            "tests": []
        },
        {
            "id": "model.jaffle_shop.stg_payments",
            "isSelected": false,
            "tests": [
                "test.jaffle_shop.accepted_values_stg_payments_payment_method__credit_card__coupon__bank_transfer__gift_card.3c3820f278",
                "test.jaffle_shop.not_null_stg_payments_payment_id.c19cc50075",
                "test.jaffle_shop.unique_stg_payments_payment_id.3744510712"
            ]
        },
        {
            "id": "model.jaffle_shop.customers",
            "isSelected": false,
            "tests": [
                "test.jaffle_shop.not_null_customers_customer_id.5c9bf9911d",
                "test.jaffle_shop.relationships_orders_customer_id__customer_id__ref_customers_.c6ec7f58f2",
                "test.jaffle_shop.unique_customers_customer_id.c5af1ff4b1"
            ]
        },
        {
            "id": "seed.jaffle_shop.raw_orders",
            "isSelected": false,
            "tests": []
        },
        {
            "id": "model.jaffle_shop.stg_orders",
            "isSelected": false,
            "tests": [
                "test.jaffle_shop.accepted_values_stg_orders_status__placed__shipped__completed__return_pending__returned.080fb20aad",
                "test.jaffle_shop.not_null_stg_orders_order_id.81cfe2fe64",
                "test.jaffle_shop.unique_stg_orders_order_id.e3b841c71a"
            ]
        },
        {
            "id": "model.jaffle_shop.orders",
            "isSelected": true,
            "tests": [
                "test.jaffle_shop.accepted_values_orders_status__placed__shipped__completed__return_pending__returned.be6b5b5ec3",
                "test.jaffle_shop.not_null_orders_amount.106140f9fd",
                "test.jaffle_shop.not_null_orders_bank_transfer_amount.7743500c49",
                "test.jaffle_shop.not_null_orders_coupon_amount.ab90c90625",
                "test.jaffle_shop.not_null_orders_credit_card_amount.d3ca593b59",
                "test.jaffle_shop.not_null_orders_customer_id.c5f02694af",
                "test.jaffle_shop.not_null_orders_gift_card_amount.413a0d2d7a",
                "test.jaffle_shop.not_null_orders_order_id.cf6c17daed",
                "test.jaffle_shop.relationships_orders_customer_id__customer_id__ref_customers_.c6ec7f58f2",
                "test.jaffle_shop.unique_orders_order_id.fed79b3a6e"
            ]
        }
    ],
    "edges": [
        {
            "parent": {
                "id": "model.jaffle_shop.stg_customers",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "seed.jaffle_shop.raw_customers",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.stg_customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "seed.jaffle_shop.raw_customers",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.stg_customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_customers",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_orders",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_payments",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_payments",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.orders",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "seed.jaffle_shop.raw_payments",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.stg_payments",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "seed.jaffle_shop.raw_payments",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.stg_payments",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_payments",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_orders",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.customers",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_orders",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.orders",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "seed.jaffle_shop.raw_orders",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.stg_orders",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "seed.jaffle_shop.raw_orders",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.stg_orders",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_orders",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.orders",
                "isSelected": false,
                "tests": []
            }
        },
        {
            "parent": {
                "id": "model.jaffle_shop.stg_payments",
                "isSelected": false,
                "tests": []
            },
            "child": {
                "id": "model.jaffle_shop.orders",
                "isSelected": false,
                "tests": []
            }
        }
    ]
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
                    isSelected: node.isSelected
                },
                type: "dbtModel",
            });
        });

        return newNodes
    }

    function configureEdges(info: LineageInfo): Edge[] {
        return info.edges.map((e) => {
            return {
                id: `${e.parent.id}-${e.child.id}`,
                source: e.parent.id,
                target: e.child.id,
                markerEnd: {
                    type: MarkerType.ArrowClosed,
                },
            };
        });
    }

    function testKotlinRuntimeCall(info: LineageInfo) {
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
        (window as any).testKotlinRuntimeCall = testKotlinRuntimeCall;
        if (process.env.NODE_ENV === 'development') {
            testKotlinRuntimeCall(testData);
        }
        return () => {
            (window as any).testKotlinRuntimeCall = undefined;
        }
    }, []);

    return (
        <div style={{ height: "100vh", width: "100vw" }}>
            <ReactFlow
                nodes={nodes}
                nodeTypes={nodeTypes}
                onNodesChange={onNodesChange}
                edges={edges}
                edgeTypes={edgeTypes}
                onEdgesChange={onEdgesChange}
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
