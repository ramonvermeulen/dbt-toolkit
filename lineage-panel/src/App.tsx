import 'reactflow/dist/style.css';
import { ReactFlowProvider } from 'reactflow';

import Flow from './Flow.tsx';


export default function App() {
    return (
        <div style={{ height: '100vh', width: '100vw' }}>
            <ReactFlowProvider>
                <Flow />
            </ReactFlowProvider>
        </div>
    );
}