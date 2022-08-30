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
      >
        {slideItems.map((item) => {
          const { slideId, imgUrl, descContents } = item;
          return (
            <div key={slideId} className='swiper-item'>
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
      <div className='indicator-wrapper'>
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
