import React from 'react';
import CarouselitemP from './CarouselitemP'

interface itemProps {
    title:string
    describe:string
    [index:string]:any
}

const Carouselitem: React.FC<itemProps> = (props) => {
    const {title, describe, role='carousel-item', bgImage} : 
          {title:string, describe:string, role?:any, bgImage?:any } = props;
    const style: React.CSSProperties = bgImage ? {
        backgroundImage : `url(${bgImage})`
    }:{}
    return (
        <div className='item' role={role} style={style}>
            <CarouselitemP className='title' htmlString={title} />
            <CarouselitemP className='describe' htmlString={describe} />
        </div>
    );
}

export default Carouselitem;