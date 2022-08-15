import React from 'react';
import { ICarouselItemProps } from '../../index';
import './index.css';

export const CarouselItem = ({
  color,
  title,
  subTitle,
  imageName
}: ICarouselItemProps) => {
  return (
    <div className={`carousel-item`}>
      <img
        className='product-img'
        src={imageName}
        alt="product"
      />
      <div className='title'>
        {
          title.map((item: string, index: number) => {
            return <h1 style={{ color }} key={index}>{item}</h1>
          })
        }
        {
          (subTitle || []).map((item: string, index: number) => {
            return <h3 style={{ color }} key={index}>{item}</h3>
          })
        }
      </div>
    </div>
  );
};