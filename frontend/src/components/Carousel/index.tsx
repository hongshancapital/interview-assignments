import React, { useState } from 'react';
import './index.scss';

interface CarouselItemProps {
    bgColor: 'string';
    textColor: 'string';
    title: 'string' | string[];
    subtitle?: 'string' | string[];
    imgUrl: 'string';
    width?: string;
    height?: string
}

const CarouselItem = (data: CarouselItemProps, key: number, width: string, height: string) => {
    const { bgColor, title, subtitle, imgUrl, textColor } = data;

    const text = (text: string, classname: string) => <div className={classname} style={{ "color": textColor }}>{text}</div>
    return <div className='carousel-item-content' key={key} style={{ "backgroundColor": bgColor, width, height }}>

        {
            typeof title === 'string' ? text(title, 'carousel-item-title') :
                title?.map(item => text(item, 'carousel-item-title'))
        }
        {
            subtitle ?
                typeof subtitle === 'string' ? text(subtitle, 'carousel-item-subtitle') :
                    subtitle?.map(item => text(item, 'carousel-item-subtitle'))
                :
                null
        }
        <div className='carousel-item-img'>
            <img src={imgUrl} alt={'产品'} />
        </div>
    </div>
}


let timer: NodeJS.Timeout | null = null;
const Carousel: any = (options: { data: CarouselItemProps[], width?: string, height?: string }) => {
    const { data, width = '800px', height = '600px' } = options;

    const [activeKey, setActiveKey] = useState(0);
    const [left, setLeft] = useState('0')


    if (timer) {
        clearInterval(timer);
        timer = null;

        timer = setInterval(() => {
            setActiveKey(activeKey === data.length ? 0 : activeKey + 1)
            setLeft(activeKey === data.length ? '0' : `${activeKey * -800}px`)
        }, 3000)
    } else {
        timer = setInterval(() => {
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
        <div className='carousel-content' style={{
            left,
            "animation": 'left 3s linear infinite'
        }}>
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