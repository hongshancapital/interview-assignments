import React, { ReactNode, useEffect, useState, useRef } from 'react';
import SwipeIndicator from './SwipeIndicator';
import { useTimeLimt } from '../../hooks/index'

import Style from './Swipe.module.scss';

const DEFAULT_DURATION = 3000;  // 默认切换间隔

interface SwipeProps {
    duration?: number;
    children: Array<ReactNode>
}
const Swipe: React.FC<SwipeProps> = (props) => {
    const duration = props.duration || DEFAULT_DURATION;
    const [activeIndex, updateActiveIndex] = useState(0);    // 当前激活的banner
    const [isLockAnimation, setIsLockAnimation] = useState(false);
    const { start: startRecordTime, getRunTime, isTimeout } = useTimeLimt(duration);
    const timeoutTaskId = useRef<NodeJS.Timeout | null>(null);

    const cancelTimeoutTask = () => {
        if (timeoutTaskId.current) clearTimeout(timeoutTaskId.current);
        timeoutTaskId.current = null;
    }

    // 组件退出时清理多余的setTimeout
    useEffect(() => {
        return cancelTimeoutTask
    }, [])

    useEffect(() => {
        if (isLockAnimation) {
            cancelTimeoutTask();
            return;
        }

        // 如果当前动画没有执行完
        if (timeoutTaskId.current) return

        const { length } = props.children;
        if (length > 1) {
            timeoutTaskId.current = setTimeout(()=> {
                updateActiveIndex((activeIndex + 1) % length)
                timeoutTaskId.current = null;
            }, duration)
        };
    }, [duration, isLockAnimation, props.children, activeIndex]);

    // 鼠标移入进度条：暂停自动切换
    const onMouseEntryHander = (index: number) => {
        setIsLockAnimation(true);
        if (index === activeIndex) return
        startRecordTime();
        updateActiveIndex(index);
    }

    // 鼠标移出进度条：恢复自动切换
    const onMouseLeaveHander = () => {
        setIsLockAnimation(false);
        if (isTimeout()) {
            // 如果已经超过动画间隔时间，则立即切到下一张banner
            updateActiveIndex((activeIndex + 1) % props.children.length)
        } else {
            // 停留时间如果没有超过动画间隔时间，则恢复动画
            timeoutTaskId.current = setTimeout(() => {
                updateActiveIndex((activeIndex + 1) % props.children.length);
                timeoutTaskId.current = null;
            }, duration - getRunTime())
        }
    }

    return <div className={Style["swipe-panel"]}>
        <div className={Style['swipe-container']} style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
            {props.children}
        </div>
        {/* 底部进度条 */}
        <div className={Style['swipe-indicators']}>
            {props.children.map((_, index) => {
                return <SwipeIndicator
                    key={index}
                    index={index}
                    isActive={activeIndex === index}
                    duration={duration}
                    onMouseEntry={onMouseEntryHander}
                    onMouseLeave={onMouseLeaveHander} />
            })}
        </div>
    </div>
}

export default Swipe