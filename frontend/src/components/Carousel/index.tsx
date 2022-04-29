import React, { useState } from 'react';
import './index.scss';
import classname from 'classnames';
import { CarouselItem, CarouselItemProps } from './carouselItem';



let timer: NodeJS.Timeout | null = null;
const Carousel: any = (options: { data: CarouselItemProps[], width?: string, height?: string }) => {
    const { data, width = '800px', height = '600px' } = options;
    const [activeKey, setActiveKey] = useState(0);
    const [left, setLeft] = useState('0')


    if (timer && activeKey !== 0) {
        clearInterval(timer);
        timer = null;

        timer = setTimeout(() => {
            setActiveKey(activeKey === data.length ? 0 : activeKey + 1)
            setLeft(activeKey === data.length ? '0' : `${activeKey * -800}px`)
        }, 3000)
    } else {
        timer = setTimeout(() => {
            setActiveKey(activeKey === data.length ? 0 : activeKey + 1)
            setLeft(activeKey === data.length ? '0' : `${activeKey * -800}px`)
        }, 0)
    }

    // 进度下标
    const Progress = (length: number) => {

        const ProgressItem = (key: number) => <div className='progress-item' key={key}>
            <span className={key === activeKey ? 'progress-item-span' : ''}></span>
        </div>;

        let count = 0, childNodeList = [];

        while (count < length) {
            count++
            childNodeList.push(ProgressItem(count))
        }

        return <div className='progress-content'>
            {childNodeList}
        </div>
    }

    return <div className='carousel-contain' style={{ width, height }}>
        <div className={classname('carousel-content')} style={{ left, transition: 'left 1s linear' }}>
            {
                data.map((item, index) => CarouselItem(item, index, width, height))
            }
        </div>
        {Progress(data.length)}
    </div>
}

export {
    Carousel
};