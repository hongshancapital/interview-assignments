import React from 'react';
import './CarouselItem.css';

const CarouselItem: React.FC<CarouselItemProps> = ({ data }) => {
  const titles = data.titles.map((v, i) => <h1 className="carousel-item-content_title" key={i}>{v}</h1>);
  const pas = data.texts.map((v, i) => <p className="carousel-item-content_text" key={i}>{v}</p>);
  const className = data.theme ? `carousel-item carousel-item-${data.theme}` : 'carousel-item';

  return (
    <div className={className} >
      <div className='carousel-item-content'>
        {titles}
        {pas}
      </div>
      <img className='carousel-item-content_img' src={data.image} alt='icon'></img>
    </div>
  )
}
export default CarouselItem 