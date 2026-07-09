import React, { useState, useEffect } from 'react';
import { CarouselInfo, carouselData } from '../data';
import ProgressBar from './ProgressBar';
import './index.css';

const getContent = (item: CarouselInfo, index: number) => {
    return <li key={`content${index}`} style={{ background: `url(${item.carouselImgUrl}) no-repeat center bottom / cover`, color: item.fontColor }}>
        {
            item.carouselTitle.map(title => <h2>{title}</h2>)
        }
        {
            item.carouselContent?.map(content => <p>{content}</p>)
        }
    </li>
}

const Carousel: React.FC = () => {
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentIndex((currentIndex + 1) % carouselData.length);
    }, 2000);
    return () => timer && clearInterval(timer)
  }, [currentIndex]);

  return (
    <>
        <ul className='carouselArea' style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
            {
                carouselData.map((item, index) => {
                    return getContent(item, index)
                })
            }
        </ul>
        <ProgressBar currentIndex={currentIndex} totalNumber={carouselData.length} />
    </>
  );
};

export default Carousel;