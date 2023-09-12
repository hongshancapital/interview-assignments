import React, { useState, useEffect, useRef, ReactNode } from 'react';
import './index.css'

interface IProps {
  images: string[]; // 图片链接数组
  interval?: number; // 轮播间隔时间，默认3000ms
  texts:ReactNode[]
}

const Carousel: React.FC<IProps> = ({ images, interval = 5,texts }) => {
  const [currentIdx, setCurrentIdx] = useState(0);
  const [translateX, setTranslateX] = useState(0);
  const [isTransitioning, setIsTransitioning] = useState(false);
  const carouselRef = useRef<HTMLDivElement>(null);

  const slide = (idx: number) => {
    setIsTransitioning(true);
    setCurrentIdx(idx);
    setTranslateX(-(idx) * 100);
  };

  useEffect(() => {
    const nextIdx = (currentIdx + 1) % images.length;
    const timeoutId = setTimeout(() => {
      slide(nextIdx);
    }, interval*1000);

    return () => {
      clearTimeout(timeoutId);
    };
  }, [currentIdx, images.length, interval]);

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel"
        ref={carouselRef}
        style={{ transform: `translateX(${translateX}vw)`, transition: isTransitioning ? 'transform 0.5s' : 'none' }}
      >
        {images.map((imageUrl, idx) => (
          <div className="carousel-item" key={idx} style={{background:idx===0?'rgba(160,0,16)':'#666'}}>
            <div className='carousel-item-txt'>
              {texts[idx]}
            </div>
            
            <img key={imageUrl} src={imageUrl} alt={`image-${idx}`} onClick={() => slide(idx)} />
          </div>
        ))}
      </div>
      <div className="carousel-spotbox">
        {images.map((imageUrl, idx) => (
          <div className="carousel-spot">
            <div className='carousel-spot-actived' style={{width:currentIdx===idx?'100%':0,transition:currentIdx===idx?`all ${interval}s ease-in-out`: 'none'}}></div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
