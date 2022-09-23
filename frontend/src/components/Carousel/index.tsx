import React, { FC, useEffect, useState } from 'react';
import './index.css';

export type ListType = {
    id: number,
    title: string;
    desc: string;
    url: string;
    color: string;
}


type Props = {
    list: ListType[],
    timeout?: number,
    speed?: number;
}

const Carousel: FC<Props> = ({ list = [], timeout = 2000, speed = 0.5 }) => {
    const [current, setCurrent] = useState<number>(0)
  
    const fnText = (text: string) => {
        return {__html: text}
    }

    useEffect(() => {
        let timer = window.setTimeout(() => {
            setCurrent((cur) => cur === list.length - 1 ? 0 : cur + 1)
        }, timeout);
        return () => clearTimeout(timer)
    }, [current])

  return <div className='carousel'>
    {/* 背景图部分 */}
    <div className='carousel-ul' style={{ 
        transform: `translateX(-${current * 100}%)`, 
        transitionDuration: `${speed}s` 
    }}>
        {
            list.map(item => (
                <div key={item.id} className='carousel-li' style={{ backgroundImage: `url(${item.url})`, color: item.color }}>
                    <div className='title' dangerouslySetInnerHTML={fnText(item.title)}></div>
                    <div className='desc' dangerouslySetInnerHTML={fnText(item.desc)}></div>
                </div>
            ))
        }
    </div>

    {/* 底部progress部分 */}
    <div className='progress-ul'>
        {
            list.map((item, index) => (
                <div className={`progress-li ${index === list.length - 1 && 'not-mg-right'}`}>
                    <div className={current === index ? 'active' : ''} style={{animationDuration: `${timeout}ms`}}></div>
                </div>
            ))
        }
    </div>
  </div>
}

export default Carousel;