import React from 'react';
import { SlideItem } from '../slide-item';
import { useCarousel } from './hook';
import './index.css';

export const Carousel: React.FC = () => {
  const { curIndex, slideItems, animationConfig, changeIndicator } = useCarousel();
  const { duration, transitionDuration } = animationConfig;

  /** 渲染滑块区 */
  const renderContent = () => {
    return (
      <div
        className='swiper-wrapper'
        style={{
          transitionDuration: transitionDuration + 'ms',
          transform: `translateX(${-curIndex * 100}%)`
        }}
        data-testid='swiper-wrapper'
      >
        {slideItems.map((item) => {
          const { slideId, bgColor, imgUrl, descContents } = item;
          return (
            <div key={slideId} className='swiper-item' style={{ backgroundColor: bgColor }}>
              <SlideItem imgUrl={imgUrl} descContents={descContents}/>
            </div>
          )
        })}
      </div>
    );
  }

  /** 渲染锚点指示器 */
  const renderIndicator = () => {
    return (
      <div className='indicator-wrapper' data-testid='indicator-wrapper'>
        {slideItems.map((item, index) => {
          return (<div
            key={item.slideId}
            className='indicator-item'
            onClick={() => changeIndicator(index)}
          >
              <div
                className='indicator-process'
                style={
                  index === curIndex
                    ? { width: '50px', transition: `${duration}ms ease-in-out` } 
                    : {}
              } />
          </div>);
        })}
      </div>
    );
  }

  return <div className='carousel'>
    {renderContent()}
    {renderIndicator()}
  </div>;
}
