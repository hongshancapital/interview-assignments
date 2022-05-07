import React, { FunctionComponent } from 'react';
import './index.scss';

interface ProgressProps {
    active: boolean;
    duration: number;
}

const ProgressItem: FunctionComponent<ProgressProps> = ({ active, duration }) => {
    return <div className='progress-item'>
        <div
            className={`line ${active ? 'active' : ''}`}
            style={{ animationDuration: `${duration}ms` }}
        />
    </div>;
}

export default ProgressItem;