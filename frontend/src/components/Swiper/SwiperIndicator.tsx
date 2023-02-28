import React from 'react';

import Style from './Swiper.module.scss';

interface IndicatorProp {
    isActive: boolean;
    index: number;
    duration: number;
    onMouseEntry: Function;
    onMouseLeave: Function;
}
const SwiperIndicator: React.FC<IndicatorProp> = (prop) => {
    return <div
        className={Style['swiper-indicator']}
        key={prop.index}
        onMouseEnter={() => prop.onMouseEntry(prop.index)}
        onMouseLeave={() => prop.onMouseLeave()}>
        <div
            className={`${Style['swiper-indicator-content']} ${prop.isActive ? Style['swiper-indicator-active'] : ''}`}
            style={{ animationDuration: `${prop.duration / 1000}s` }}></div>
    </div>
}

export default SwiperIndicator