import React, { useState, useEffect, memo } from 'react';

import { ICarouselInf } from '../../types/modelTypes';

const CarourselItem = ({
    title = '',
    description = '',
    imageUrl = '',
    color = '#fff',
    backgroundColor = '',
}: ICarouselInf) => {

    // 根据换行符格式化（换行的控制在数据输入侧控制）
    const formatText = (text: string) => {
        return text.split(/\n/).map((textItem, i) => {
            return <span key={i}>{textItem}</span>
        })
    }

    return (
        <div className='carousel-item' style={{ backgroundImage: `url(${imageUrl})`, color, backgroundColor  }}>
            <h3 className='carousel-title'>{formatText(title)}</h3>
            <div className='carousel-desc'>{formatText(description)}</div>
        </div>
    );
};

export default CarourselItem;