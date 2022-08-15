import React from 'react';

import Style from  './Swipe.module.scss';

interface IndicatorProp {
    isActive: boolean;
    index: number;
    duration: number;
    onMouseEntry: Function;
    onMouseLeave: Function;
}
const SwipeIndicator: React.FC<IndicatorProp> = (prop) => {
    return <div
        className={Style['swipe-indicator']}
        key={prop.index}
        onMouseEnter={() => prop.onMouseEntry(prop.index)}
        onMouseLeave={() => prop.onMouseLeave()}>
        <div
            className={`${Style['swipe-indicator-content']} ${prop.isActive ? Style['swipe-indicator-active'] : ''}`}
            style={{ animationDuration: `${prop.duration / 1000}s` }}></div>
    </div>
}

export default SwipeIndicator