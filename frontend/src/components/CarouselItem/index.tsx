import React from "react";
import './index.scss';

export interface ICarouselItem {
  title: string;
  describe?: string;
  className?: string;     // 每一页的样式名称
}

// 每一页中的内容的结构体如下
interface CarouselItemProps {
  active: boolean; // 是否为选中状态
  item: ICarouselItem;
}

const CarouselItem: React.FC<CarouselItemProps> = ({ active, item }) => {
  const { title, describe, className } = item;
  return <div className={`carousel-item ${active ? "active" : ""}`}>
    <div className={`carousel-item-content ${className}`}>
      <h2 className='title'>{title}</h2>
      {
        describe && <p className='descript'>{describe}</p>
      }
    </div>
  </div>
};

export default CarouselItem;