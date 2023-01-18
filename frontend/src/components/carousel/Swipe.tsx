import React, { useEffect, useMemo, useRef } from 'react';
import { SwipeItem } from './SwipeItem';
import useRect from './hooks/useRect';
import useSwipe from './hooks/useSwipe';
import SwipeDots from './SwipeDots';
import './index.css';

export interface SwipeRef {
    next: () => void;
    prev: () => void;
    slideTo: (to: number, swiping?: boolean) => void;
}

export interface SwipeProps {
    autoplay?: number;
    duration?: number;
    loop?: boolean;
    showIndicators?: boolean;
    style?: React.CSSProperties;
    children: React.ReactNode;
}

export const Swipe = React.forwardRef<SwipeRef, SwipeProps>((props, ref) => {
    const {
        duration = 500,
        autoplay = 3000,
        loop = true,
        showIndicators = true
    } = props;
    const timer = useRef<NodeJS.Timeout | null>(null);
    const count = useMemo(() => React.Children.count(props.children), [props.children]);
    const { size, root } = useRect<HTMLDivElement>([count]);
    const itemSize = useMemo(() => size.width, [size]);
    const wrappStyle = useMemo(() => ({ 'width': itemSize * count }), [count, itemSize]);

    // 核心方法
    const {
        setRefs,
        current,
        swipeRef,
        loopMove
    } = useSwipe({ count, duration, size: itemSize, loop });

    const onPlay = () => {
        if (count <= 1) return;
        if (!autoplay) return;
        timer.current = setTimeout(() => {
            loopMove();
        }, autoplay);
    }

    const onPause = () => {
        timer.current && clearTimeout(timer.current);
        timer.current = null;
    }


    useEffect(() => {
        if (itemSize) {
            onPlay();
        }
        return () => {
            onPause();
        }
    }, [count, autoplay, current, itemSize]);

    return (
        <div
            ref={root}
            style={props.style}
            className='swipe'>
            <div
                ref={swipeRef}
                style={wrappStyle}
                className='swipe__container'>
                {
                    React.Children.map(props.children, (child, index) => {
                        if (!React.isValidElement(child)) return null;
                        if (child.type !== SwipeItem) return null;
                        return React.cloneElement(child, {
                            // @ts-ignore
                            ref: setRefs(index)
                        })
                    })
                }
            </div>
            {
                showIndicators && <SwipeDots duration={duration} current={current}  count={count}/>
            }
        </div>
    )
});

