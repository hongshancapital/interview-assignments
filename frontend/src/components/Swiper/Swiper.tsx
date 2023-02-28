import React, { ReactNode, useEffect, useState } from 'react';
import { useDelayTask, useMemoizedFn, TASK_STATUS } from '../../hooks'

import SwiperIndicator from './SwiperIndicator';

import Style from './Swiper.module.scss';

const DEFAULT_DURATION = 3000;  // 默认切换间隔

interface SwiperProps {
    duration?: number;
    children: Array<ReactNode>
}
const Swipe: React.FC<SwiperProps> = (props) => {
    const [activeIndex, setActiveIndex] = useState(0);    // 当前激活的banner

    const duration = props.duration || DEFAULT_DURATION;
    const { length } = props.children;
    const switchToNext = () => setActiveIndex(activeIndex => (activeIndex + 1) % length);
    const { runDelayTask, cancelDelayTask, taskStatus } = useDelayTask(switchToNext, duration);

    useEffect(() => {
        // 初始化时或延时任务执行完后: 起一个新的延时任务
        if (taskStatus === TASK_STATUS.INIT || taskStatus === TASK_STATUS.DONE) {
            runDelayTask();
        }
    }, [taskStatus, runDelayTask])

    // 鼠标移入进度条：立即进入该banner并停止自动切换
    const handleMouseEntry = useMemoizedFn((index: number) => {
        cancelDelayTask();
        if (activeIndex !== index) {
            setActiveIndex(index)
        }
    })

    // 鼠标移出进度条：恢复自动切换
    const handleMouseLeave = useMemoizedFn(() => {
        switchToNext();
        runDelayTask();
    })

    return <div className={Style["swiper-panel"]}>
        <div className={Style['swiper-container']} style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
            {props.children}
        </div>
        {/* 底部进度条 */}
        <div className={Style['swiper-indicators']}>
            {props.children.map((_, index) => {
                return <SwiperIndicator
                    key={index}
                    index={index}
                    isActive={activeIndex === index}
                    duration={duration}
                    onMouseEntry={handleMouseEntry}
                    onMouseLeave={handleMouseLeave} />
            })}
        </div>
    </div>
}

export default Swipe