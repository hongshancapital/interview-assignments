import React from 'react';
import './index.scss';

interface CarouselItemProps {
    bgColor: 'string';
    textColor: 'string';
    title: 'string';
    subtitle?: 'string';
    imgUrl: 'string';
}

const Progress = (length: number) => {
    const ProgressItem = <div className='progress-item'></div>;
    let count = 0, childNodeList = [];

    while (count < length) {
        count++
        childNodeList.push(ProgressItem)
    }

    return <div className='progress-content'>
        {childNodeList}
    </div>
}

const CarouselItem = (data: CarouselItemProps) => {
    const { bgColor, title, subtitle, imgUrl, textColor } = data;

    return <div className='carousel-item-content' style={{ "backgroundColor": bgColor }}>

        <div className='carousel-item-title' style={{ "color": textColor }}>{title}</div>
        {
            subtitle ?
                <div className='carousel-item-subtitle' style={{ "color": textColor }}>{subtitle}</div> :
                null
        }
        <div className='carousel-item-img'>
            <img src={imgUrl} alt={title} />
        </div>

    </div>
}


const Carousel: any = (options: { data: CarouselItemProps[] }) => {
    const { data } = options;
    return <>
        <div className='carousel-content'>
            {
                data.map((item, index) => <CarouselItem {...item} />)
            }
        </div>
        {Progress(data.length)}
    </>
}

export {
    Carousel,
    Progress
};