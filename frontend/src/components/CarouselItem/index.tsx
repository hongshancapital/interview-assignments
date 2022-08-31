import React from 'react';
import './style.css';

export interface CarouselItemProps {
  // 标题
  title: string;
  // 副标
  desc?: string;
  // 背景图
  img: string;
  // 文字颜色
  color: string;
}

const CarouselItem = ({ title, desc, img, color }: CarouselItemProps) => {
  const renderTexts = (ws: string) =>
    ws.split(/\n/).map((t) => <div key={t}>{t}</div>);

  return (
    <div className="container" style={{ color, backgroundImage: `url(${img})` }}>
      <div className="infos">
        {title ? <div className="title">{renderTexts(title)}</div> : null}
        {desc ? <div className="desc">{renderTexts(desc)}</div> : null}
      </div>
    </div>
  );
};

export default CarouselItem;
