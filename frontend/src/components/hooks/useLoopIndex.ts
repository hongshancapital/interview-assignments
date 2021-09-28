/**
 * @desc 自定义hook，能在指定范围[min, max]以给定的时间间隔循环取值，
 *       这里使用requestAnimationFrame来模拟定时器
 */
import { useState, useEffect, useRef } from 'react';

export interface argTypes {
	duration?: number,
	max: number,
    min?: number,
    defaultIndex?: number,
};

let start: DOMHighResTimeStamp;

function useLoopIndex({ duration = 3000, max, min = 0, defaultIndex = min } : argTypes) {
    const [activeIndex, setActiveIndex] = useState(defaultIndex);
    const refActiveIndex = useRef(activeIndex); // 避免闭包引起的bug
    const step = (timestamp: DOMHighResTimeStamp) => {
        if (!refActiveIndex) { // 避免组件卸载的情况
            return;
        }
        if (timestamp - (start = start || timestamp) >= duration) {
            const nextIndex = refActiveIndex.current + 1;
            refActiveIndex.current = nextIndex > max ? min : nextIndex;
            setActiveIndex(refActiveIndex.current);
            start = timestamp;
        }
        window.requestAnimationFrame(step);
    };
    useEffect(() => {
        window.requestAnimationFrame(step);
    }, []);
    return activeIndex;
}

export default useLoopIndex;