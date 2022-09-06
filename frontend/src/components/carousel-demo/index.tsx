import React, { CSSProperties, ReactNode } from 'react';
import { Carousel } from '../carousel';
import { ICarouselItem } from './type';

import './index.css';

/**
 * 渲染跑马灯单幅内容
 * @param {ICarouselItem} item 跑马灯渲染数据项
 */
const renderCarouselItem = (item: ICarouselItem): ReactNode => {
  const { id, titles, contents, image, bgColor, titleColor, contentColor } = item;

  // 每一帧背景色设置
  const cardStyles: CSSProperties = {
    backgroundColor: bgColor,
    backgroundImage: `url(${image})`,
  };

  return <div className="carousel-card" key={id} style={cardStyles}>
    <div className="carousel-card-titles">
      {
        titles?.map((title, idx) => {
          return <div key={idx} className="carousel-card-title" style={{ color: titleColor }}>{title}</div>
        }) ?? null
      }
    </div>
    <div className="carousel-card-contents">
      {
        contents?.map((content, idx) => {
          return <div key={idx} className="carousel-card-content" style={{ color: contentColor }}>{content}</div>
        }) ?? null
      }
    </div>
  </div>;
};

export interface CarouselDemoProps {
  items: ICarouselItem[];
}
/**
 * 实现示例跑马灯效果
 * @param {ICarouselItem} items 跑马灯示例数据列表
 */
export const CarouselDemo: React.FC<CarouselDemoProps> = ({ items }) => (<div className="carousel-demo-container">
    <Carousel>{ items.map(renderCarouselItem) }</Carousel>
  </div>);
