import React, { FC } from 'react';
interface CarouselItemProps {
    id: number,
    title: Array<string>,
    details?: Array<string>,
    color: string,
    imageSrc: string,
    active: Boolean,
    animating: Boolean,
    translate: string
}

export const CarouselItem: FC<CarouselItemProps> =
    ({
         id,
         title,
         details,
         imageSrc,
         active,
         animating,
         translate,
         color,
     }) => {
        return <>
            <span className={['carousel-item', active?' is-active': '', animating ? ' is-animating': ''].join('')}
                 style={{transform: `translateX(${translate})`,transition: '3s', color: color}}
            >
                <img className='carousel-item-background' src={imageSrc} alt="产品图片"/>

                <div className='carousel-item-content'>
                    {title && title.map((i: string, index:number) => <h1 key={`title-${index}`}>{i}</h1>)}
                    {details && details.map((i: string, index: number) => <h3 key={`details-${index}`}>{i}</h3>)}
                </div>
            </span>

        </>
    }

