import React, { CSSProperties, FC } from 'react';

import '../style.css';

type TextObj = {
    text: string;
    style: CSSProperties;
}

type CommonCarouselItemData = {
    id: string | number;
    title: TextObj;
    subTitle: TextObj;
    img: string;
    [key: string]: any;
}

type Props = {
    data: CommonCarouselItemData;
    style?: CSSProperties;
};

const HomeCarouselItems: FC<Props> = ({ data, style }) => {
    const { title, subTitle, img } = data;
    return <div className='item' style={style}>
        <div className='title-group'>
            <div className='title' style={title.style}>{title.text}</div>
            <div className='subtitle' style={subTitle.style}>{subTitle.text}</div>
        </div>
        <img className='img' src={img} alt='img' />
    </div>
};

export default HomeCarouselItems;
