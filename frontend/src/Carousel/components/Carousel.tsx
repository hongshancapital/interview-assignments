import { forwardRef, useImperativeHandle, useMemo, useRef } from 'react';
import useCarousel from '../hooks/useCarousel';
import { IUseCarouselOptions } from '../types';

import CarouselItem from './CarouselItem';
import CarouselDot from './CarouseDot';

import '../style/index.scss';


/** React Component Carousel ref */
interface ICarouselRef {
  /** 跳转到指定step */
  goTo: (key: string) => void;
  next: () => void;
  last: () => void;
}

interface ICarouselProps extends IUseCarouselOptions {
  className?: string;
}

const Carousel = forwardRef<ICarouselRef, ICarouselProps>((props, ref) => {
  const { items, defaultActiveKey, autoPlay = false, autoPlayGap } = props;

  const containerRef = useRef<HTMLDivElement>(null);

  const { activeKey, itemWidth, itemListStyle, goTo, next, last } = useCarousel({
    items,
    autoPlay,
    defaultActiveKey,
    autoPlayGap,
    containerRef,
  })

  useImperativeHandle(ref, () => ({
    goTo,
    next,
    last,
  }), [goTo, next, last])

  const className = useMemo(() => {
    const classNames = ['carousel'];
    if (typeof props.className === 'string') classNames.push(props.className);
    return classNames.join(' ');
  }, [props.className])

  // 第一次渲染，itemWidth 不存在, 不用进行卡片的渲染
  if (itemWidth === undefined) return <div ref={containerRef} className={className} />;

  return (
    <div ref={containerRef} className={className}>
      <div className='carousel-dot-wrapper'>
        <div className='carousel-dot-list'>
          {items.map(item => (
            <CarouselDot onDotClick={() => goTo(item.key)} key={item.key} active={activeKey === item.key && autoPlay} selected={activeKey === item.key && !autoPlay}/>
          ))}
        </div>
      </div>

      <div style={itemListStyle} className="carousel-item-list">
        {items.map(item => (
          <CarouselItem key={item.key} width={itemWidth}>{item.children}</CarouselItem>
        ))}
      </div>
    </div>
  )
})

if (process.env.NODE_ENV !== 'production') {
  Carousel.displayName = 'Carousel';
}

export default Carousel;