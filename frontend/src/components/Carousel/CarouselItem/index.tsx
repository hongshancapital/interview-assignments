import React, { FC } from 'react';
import './index.css'

const CarouselItem: FC = ({ children }) => {
    return (
        <div className='carousel-item'>
            {/* 用户自定义处 */}
            { children }
        </div>
    )
}

export default CarouselItem
