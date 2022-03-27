import React, { CSSProperties, ReactElement, useState } from 'react';
import { ICarouselIndexProps, useCarouselIndex } from '../../hooks/CarouselIndex';
import './index.scss';

const defaultTitleColor = '#000';
const defaultDurationMS = 3000;
export function Carousel(props: ICarouselProps) {
  const { durationMS = defaultDurationMS, items = [] } = props;
  const [carouselItems] = useState(items);
  const { currentIndex } = useCarouselIndex({
    count: items.length,
    durationMS,
  });

  const renderCarouselItem = (arr: typeof carouselItems) => {
    return arr.map((v, idx) => <div className={`carousel-item ${idx === currentIndex ? 'current' : ''}`} key={idx} style={{ transform: `translateX(-${(currentIndex) * 100}%)` }}>
      <div className='title-wrapper' style={{ color: v.titleColor ?? defaultTitleColor }}>
        <div className='title'>{v.title}</div>
        {v.subtitle && <div className='carousel-subtitle'>{v.subtitle}</div>}
      </div>

      <div className={'img ' + v.classname}></div>
    </div>)
  }
  return <div className='carousel-container' style={props.style}>
    {renderCarouselItem(carouselItems)}
    <Indicator durationMS={props.durationMS} count={props.items.length} />
  </div>
}

function Indicator(props: ICarouselIndexProps) {
  const { currentIndex } = useCarouselIndex({...props});
  const { durationMS = defaultDurationMS } = props;

  return <div className='mod-indicator'>
    {Array(props.count).fill('').map((_, idx) => {
      const isCurrent = idx === currentIndex;
      return <div key={idx} className={'wrapper ' + (isCurrent ? 'current' : '')}>
        <div style={{ width: isCurrent ? '100%' : 0, transition: isCurrent ? `width ${durationMS / 1e3}s ease` : 'none' }} className="progress"></div>
      </div>
    })}
  </div>
}

export interface ICarouselProps {
  items: {
    title: string | ReactElement;
    subtitle?: string | ReactElement;
    /** 标题颜色，默认#000 */
    titleColor?: `#${string}` | `rgb(${string})` | `rgba(${string})`;
    classname?: string;
  }[];
  /** 轮播间隔，单位：毫秒，默认值3000 */
  durationMS?: number;
  style?: CSSProperties;
}
