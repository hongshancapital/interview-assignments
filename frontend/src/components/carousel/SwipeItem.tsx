import React, { useImperativeHandle, useMemo, useRef, useState } from 'react';
import './index.css';

export interface SwipeItemRef {
    setOffset: React.Dispatch<React.SetStateAction<number>>
}

export interface SwipeItemProps {
    readonly style?: React.CSSProperties;
    children: React.ReactNode;
}

export const SwipeItem = React.forwardRef<SwipeItemRef, SwipeItemProps>((props, ref) => {
    const { children, style } = props;
    const [offset, setOffset] = useState(0);
    const swipeItemRef = useRef<HTMLDivElement>(null);

    useImperativeHandle(ref, () => {
        return {
            setOffset
        }
    });

    const itemStyle = useMemo(() => {
        return {
            transform: offset ? `translateX(${offset}px)` : '',
            ...style
        }
    }, [offset, style]);

    return (
        <div ref={swipeItemRef} className='swipe__item' style={itemStyle}>
            {children}
        </div>
    )
});

