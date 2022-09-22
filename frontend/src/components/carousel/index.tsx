import React, { useState, useEffect, useRef } from 'react';

import { CarourselProps, CarourselRef } from './types';
import CarourselItem from './item';
import './style.css';
import { IBaseInf } from '../../types/modelTypes';

const Caroursel = ({
    //起始帧
    startIndex = 0,
    // 切换速度
    switchSpeed = 500,
    // 切换间隔
    switchInterval = 3000,
    // 点击事件
    onClick,
    // 切换帧事件
    onChange,
    // 数据
    data = []
}: CarourselProps) => {

    const [current, setCurrent] = useState(startIndex);
    const commonRef = useRef<CarourselRef>({});
    const len = data.length;

    // 切换帧事件
    const switchEvt = (index: undefined | number) => {
        setCurrent(((index == undefined ? current + 1 : index)) % len);
        onChange && onChange(data[current], current);
    };

    // 点击事件
    const clickEvt = () => {
        let result: boolean | void,
            itemData: IBaseInf = data[current];

        // 处理传入的点击事件，拿到结果进行判断
        onClick && (result = onClick(data[current], current));

        // 返回false，则不执行默认动作
        if (result === false) {
            return;
        }

        // 如果存在链接，则跳转页面
        itemData.link && window.open(itemData.link);
    }

    // 指示器点击事件
    const indicatorClientEvt = (index: number) => {
        commonRef.current.timer && clearTimeout(commonRef.current.timer);
        switchEvt(index);
    }

    useEffect(() => {
        // 开启定时轮换
        commonRef.current.timer = window.setTimeout(switchEvt, switchInterval);
    }, [current]);

    return (

        <div className='carousel' >
            <div className='carousel-container' onClick={clickEvt}
                style={{ transform: `translateX(-${current * 100}%)`, transitionDuration: `${switchSpeed}ms` }}>
                {data.map((itemData) => <CarourselItem key={itemData.id} {...itemData}></CarourselItem>)}
            </div>
            <div className='carousel-indicators'>
                {data.map((itemData, i) => {
                    return <div key={i} className='carousel-indicator' onClick={() => indicatorClientEvt(i)}>
                        <div className={current === i ? 'carousel-indicator-active' : ''}
                            style={{ animationDuration: `${switchInterval}ms` }} />
                    </div>
                })}
            </div>
        </div>
    )
}

export default Caroursel;