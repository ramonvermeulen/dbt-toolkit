
import { FaChartArea } from 'react-icons/fa';
import { FaRegNoteSticky } from 'react-icons/fa6';
import { GoDatabase } from 'react-icons/go';
import { MdQueryStats } from 'react-icons/md';
import { PiCubeDuotone } from 'react-icons/pi';
import { RiSeedlingLine } from 'react-icons/ri';
import { TbBoxModel } from 'react-icons/tb';
import { Handle, Position } from 'reactflow';
import type { NodeProps } from 'reactflow';


export type PositionLoggerNodeData = {
    isSelected?: boolean;
    label?: string;
};

export function DbtModelNode({
    data,
}: NodeProps<PositionLoggerNodeData>) {
    const parts = data?.label?.split('.');
    const type = parts?.[0];
    const name = parts?.[2];
    const source = parts?.[3];

    const renderIcon = () => {
        if (type === 'seed') {
            return <RiSeedlingLine size={14}/>;
        } else if (type === 'model') {
            return <PiCubeDuotone size={14}/>;
        } else if (type === 'source') {
            return <GoDatabase size={14}/>;
        } else if (type === 'saved_query') {
            return <MdQueryStats size={14}/>;
        } else if (type === 'semantic_model') {
            return <TbBoxModel size={14}/>;
        } else if (type === 'metric') {
            return <FaChartArea size={14}/>;
        } else if (type === 'unit_test') {
            return <FaRegNoteSticky size={14}/>;
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
                {name && <div className="dbt-model-title">{name}{(type == 'source') && ': ' + source}</div>}
            </div>
            <Handle type="source" position={Position.Right} />
            <Handle type="target" position={Position.Left} />
        </div>
    );
}