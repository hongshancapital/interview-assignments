import React, { FunctionComponent, useEffect, useRef, useState } from 'react';
import './index.scss';
import { IndicatorProps } from './interface';


const Indicator: React.FC<IndicatorProps> = (props) => {
    const {
        style,
        className,
        type = 'line',
        activeStyle,
        count = 1,
        currentIndex = 0,
        progress = 0,
        orientation = 'horizontal',
        onClick
    } = props;


    const renderContent = () => {
        const indicatorList = [];
        for (let i = 0; i < count; i++) {
            indicatorList.push(renderItem(i));
        }
        return indicatorList;
    };


    const renderItem = (index: number) => {
        const newStyle = {
            ...activeStyle,
        };
        if (orientation === 'vertical') {
            newStyle.height = `${index === currentIndex ? progress : 0}%`;
        } else {
            newStyle.width = `${index === currentIndex ? progress : 0}%`;
        }
        switch (type) {
            case 'line':
                return <div key={index} onClick={() => onClick && onClick(index)}
                    className={`${className || ''} ${orientation} indicator ${type} ${index === currentIndex ? 'active' : ''}`}
                    style={style}>
                    <div className={`${orientation}  ${index === currentIndex ? 'line-active' : ''}`}
                        style={newStyle}></div>
                </div>;
            case 'dot':
                return <div key={index} onClick={() => onClick && onClick(index)}
                    className={`${className || ''} ${orientation}  indicator ${type} ${index === currentIndex ? 'active' : ''}`}
                    style={index === currentIndex ? activeStyle : style}>
                </div>;
        }
    };

    return (
        <div className={`${orientation === 'horizontal' ? 'fx-row fx-v-center' : 'fx-column fx-v-center'}`}>
            {renderContent()}
        </div>

    );
};

export default Indicator;
