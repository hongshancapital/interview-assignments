import React, { useEffect, useRef, useState } from 'react';
import { fixEasing } from './utils';
import './index.css';

interface ICarouselProps {
  autoplay?: boolean; // 是否自动切换 
  interval?: number; // 自动切换的间隔, default 3000
  pauseOnHover?: boolean; // 只有在自动切换时有效
  dots?: boolean; // 是否显示位置
  easing?: 'linear' | 'ease' | 'ease-in' | 'ease-out' | 'ease-in-out' | string; // default linear, string 可以设置为cubic-bezier(n,n,n,n);
  duration?: number; // 动画持续时间, 单位ms, default 500
  delay?: number; // 动画效果延迟时间, default 0
  effect?: 'scroll' | 'fade'; // default scroll
  beforeChange?: (from: number, to: number) => void;
  afterChange?: (current: number) => void;
  children: JSX.Element[];
}

/**
 * @description 支持父元素设置大小时自适应, 支持子元素设置大小时自适应
 */
const Carousel: React.FC<ICarouselProps> = (props: ICarouselProps) => {
  const {
    autoplay = true,
    interval = 3000,
    pauseOnHover = true,
    dots = true,
    easing = 'linear',
    duration = 500,
    delay = 0,
    effect = 'scroll',
    afterChange = null,
    beforeChange = null,
    children } = props;
  const fixEasingStr = fixEasing(easing);
  let listStyle: React.CSSProperties = { display: 'flex' };
  const containerRef = useRef<HTMLDivElement>(null);

  const len = children.length;
  const [current, setCurrent] = useState(0);
  const slideNumber = useRef<number>(0); 

  useEffect(() => {
    afterChange?.(current);
  }, [current]);

  const animationObj = useRef<any>(null);
  const goTo = (next: number) => {
    beforeChange?.(current, next);
    setCurrent(next);
    slideNumber.current = next;
    if (animationObj.current?.runedTime) {
      animationObj.current.runedTime = 0;
    }
  };

  // autoplay 与暂停/开始
  useEffect(() => {
    if (autoplay) {
      animationObj.current = { runedTime: 0, startTime: 0};
      let timer: NodeJS.Timer;
      const setTimer = () => {
        animationObj.current.startTime = Date.now();
        timer = setTimeout(() => {
          const next = slideNumber.current + 1 >= len ? 0 : slideNumber.current + 1;
          goTo(next);
          animationObj.current.runedTime = 0;
          setTimer();
        }, interval - animationObj.current.runedTime);
      };
      const mouseoverHandler = () => {
        clearTimeout(timer);
        animationObj.current.runedTime += Date.now() - animationObj.current.startTime;
      };
      const mouseleaveHandler = () => {
        setTimer();
      };
      if (pauseOnHover && containerRef.current) {
        containerRef.current.addEventListener('mouseenter', mouseoverHandler);
        containerRef.current.addEventListener('mouseleave', mouseleaveHandler);
      }
      setTimer();
      return () => {
        clearInterval(timer);
        containerRef.current?.removeEventListener('mouseenter', mouseoverHandler);
        containerRef.current?.removeEventListener('mouseleave', mouseleaveHandler);
      };
    }
  }, []);

  listStyle = { ...listStyle, flexDirection: 'row', width: '100%' };
  const childStyle: React.CSSProperties = {
    flex: '1 0 100%',
    position: 'relative',
    width: '100%',
    transition: `${effect === 'fade' ? 'opacity':'transform'} ${duration}ms ${fixEasingStr} ${delay}ms`,
  };

  return (
    <div className='carousel-container' ref={containerRef}>
      <div className='carousel-list' style={{ overflow: 'hidden' }}>
        <div className='carousel-inner-list' style={listStyle}>
          {children.map((item, index) => {
            const effectStyle: React.CSSProperties = {};
            if (effect === 'fade') {
              effectStyle.opacity = index === current ? 1 : 0;
              effectStyle.position = index ? 'absolute' : 'relative'; // 只留第一个占位, 其他的与第一个重叠.
            } else {
              effectStyle.transform = `translate(${-current * 100}%, 0)`;
            }
            return (
              <div key={index} style={{ ...childStyle, ...effectStyle }}>
                {item}
              </div>
            );
          })}
        </div>
      </div>
      {dots &&
        <ul className='scdt-carousel-dots'>
          {children.map((item, index) => {
            const isActive = index === current;
            const animation = autoplay && isActive? `progress ${interval}ms linear 1` : '';
            return (
              <li key={index} className={isActive ? 'is-active' : ''} onClick={() => {goTo(index);}}>
                <div style={{ animation }}>{index}</div>
              </li>);
          })}
        </ul>
      }
    </div>
  );
};

export default Carousel;
