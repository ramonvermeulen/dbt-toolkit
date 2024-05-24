import {MarkerType, OnConnect, Position} from "reactflow";

import {useCallback, useEffect} from "react";
import {
    Background,
    Controls,
    MiniMap,
    ReactFlow,
    Node,
    addEdge,
    useNodesState,
    useEdgesState,
} from "reactflow";

import "reactflow/dist/style.css";

import {initialNodes, LineageInfo, nodeTypes} from "./nodes";
import {initialEdges, edgeTypes} from "./edges";

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

export default function App() {
    const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
    const onConnect: OnConnect = useCallback(
        (connection) => setEdges((edges) => addEdge(connection, edges)),
        [setEdges]
    );

    function configureNodes(info: LineageInfo) {
        const newNodes: Node[] = [];
        const baseX = 0; // base x position
        const baseY = 0; // base y position
        const offset = 100; // offset for each node

        // Find the selected node
        const selectedNode = info.nodes.find(node => node.isSelected);

        // Add the selected node
        if (selectedNode) {
            newNodes.push({
                id: selectedNode.id,
                position: {x: baseX, y: baseY},
                data: {label: selectedNode.id},
                sourcePosition: Position.Right,
                targetPosition: Position.Left,
            });
        }

        // Add the child nodes
        info.nodes.forEach((node) => {
            if (node.id !== selectedNode?.id) {
                newNodes.push({
                    id: node.id,
                    position: {x: baseX + offset, y: baseY + offset},
                    data: {label: node.id},
                    sourcePosition: Position.Right,
                    targetPosition: Position.Left,
                });
            }
        });

        // Update the nodes state
        setNodes(newNodes);
    }

    function configureEdges(info: LineageInfo) {
        info.edges.map((e) => {
            const edge = {
                id: `${e.parent.id}-${e.child.id}`,
                source: e.parent.id,
                target: e.child.id,
                markerEnd: {
                    type: MarkerType.ArrowClosed,
                },
            };
            setEdges((edges) => addEdge(edge, edges));
        });
    }

    function testKotlinRuntimeCall(info: LineageInfo) {
        console.log(info);
        configureNodes(info)
        configureEdges(info)
    }

    useEffect(() => {
        // making function available from the browser console
        (window as any).testKotlinRuntimeCall = testKotlinRuntimeCall;
        testKotlinRuntimeCall(testData)
        return () => {
            (window as any).testKotlinRuntimeCall = undefined;
        }
    }, []);

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
