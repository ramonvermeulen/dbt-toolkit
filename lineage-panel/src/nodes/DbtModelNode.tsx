import type { NodeProps } from "reactflow";
import { Handle, Position } from "reactflow";
import { RiSeedlingLine } from "react-icons/ri";
import { PiCubeDuotone } from "react-icons/pi";
import { GoDatabase } from "react-icons/go";

export type PositionLoggerNodeData = {
    isSelected?: boolean;
    label?: string;
};

export function DbtModelNode({
    data,
}: NodeProps<PositionLoggerNodeData>) {
    const parts = data?.label?.split(".");
    const type = parts?.[0];
    const name = parts?.[2];

    const renderIcon = () => {
        if (type === 'seed') {
            return <RiSeedlingLine size={14}/>;
        } else if (type === 'model') {
            return <PiCubeDuotone size={14}/>;
        } else if (type === 'source') {
            return <GoDatabase size={14}/>;
        }
        return null;
    };

    return (
        <div className={`react-flow__node-default dbt-model-node ${data?.isSelected ? 'active' : ''}`}>
            <div className="node-title">
                <div className="icon-box">
                    <div className="icon">{renderIcon()}</div>
                    <div className="type">{type?.toUpperCase().substring(0, 3)}</div>
                </div>
                {name && <div className="dbt-model-title">{name}</div>}
            </div>
            <Handle type="source" position={Position.Right} />
            <Handle type="target" position={Position.Left} />
        </div>
    );
}