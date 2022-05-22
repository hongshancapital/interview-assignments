import React, { ReactNode, useEffect, useState } from 'react';
import SwipeIndicator from './SwipeIndicator';
import { useDelayTask, useLock, useTimeLimt } from '../../hooks/index'

import Style from  './Swipe.module.scss';

const DEFAULT_DURATION = 3000;  // 默认切换间隔

interface SwipeProps {
    duration?: number;
    children: Array<ReactNode>
}
const Swipe: React.FC<SwipeProps> = (props) => {
    const duration = props.duration || DEFAULT_DURATION;
    const [activeIndex, updataActiveIndex] = useState(0);    // 当前激活的banner
    const {
        isLock: isLockAnimation,
        lock: lockAnimation,
        unLock: unLockAnimation
    } = useLock(false);
    const { start: startRecordTime, getRunTime, isTimeout } = useTimeLimt(duration)
    const { newDelayTask, cancelDelayTask } = useDelayTask()

    // 轮播到下一张banner
    const toNext = () => {
        const { length } = props.children;
        if (length > 1) {
            const newActiveIndex = (activeIndex + 1) % length;
            updataActiveIndex(newActiveIndex);
        };
    }

    useEffect(() => {
        if (isLockAnimation()) return;
        newDelayTask(toNext, duration);
        // eslint-disable-next-line
    }, [activeIndex]);

    // 鼠标移入进度条：暂停自动切换
    const onMouseEntryHander = (index: number) => {
        startRecordTime();
        updataActiveIndex(index);
        lockAnimation();
        // 取消延时动画
        cancelDelayTask()
    }

    // 鼠标移出进度条：恢复自动切换
    const onMouseLeaveHander = () => {
        unLockAnimation();
        if (isTimeout()) {
            // 如果已经超过动画间隔时间，则立即切到下一张banner
            toNext();
        } else {
            // 停留时间如果没有超过动画间隔时间，则恢复动画
            newDelayTask(toNext, duration - getRunTime());
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