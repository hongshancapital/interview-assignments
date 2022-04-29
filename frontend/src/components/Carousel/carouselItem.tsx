import React from 'react';

interface CarouselItemProps {
    bgColor: 'string';
    textColor: 'string';
    title: 'string' | string[];
    subtitle?: 'string' | string[];
    imgUrl: 'string';
    width?: string;
    height?: string
}

// 单页组件
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

export { CarouselItem };
export type { CarouselItemProps };
