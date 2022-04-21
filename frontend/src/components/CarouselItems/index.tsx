import React, { FC } from 'react';
import { ICarouselItem } from '../../interface/carousel';
import { IMAGES_MAPPER } from '../../constants';
import './index.scss';

const CarouselItems: FC<ICarouselItem> = props => {
  const { title, subtitle, text, image, backgroundColor = '#fff', color = '#000' } = props;

  const renderText = () => {
    if (Array.isArray(text)) {
      return text.map((paragraph, index) => <p key={index}>{paragraph}</p>);
    } else {
      return <p>{text}</p>;
    }
  };

  return (
    <div className="carouselItems" style={{ backgroundColor, color }}>
      <div className="title">{title}</div>
      {subtitle && <div className="title">{subtitle}</div>}
      <div className="text">{renderText()}</div>
      <div className="img">
        <img alt="" src={IMAGES_MAPPER[image]} />
      </div>
    </div>
  );
};

export default CarouselItems;
