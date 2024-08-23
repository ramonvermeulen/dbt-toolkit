import '@xyflow/react/dist/style.css';
import { ReactFlowProvider } from '@xyflow/react';

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