import React, { useEffect, useState } from 'react';
import './index.css';

interface IProps {
  children: React.ReactNode[] | React.ReactNode;
  // 轮播一次所用时间
  duration?: number;
  // 是否展示指示器
  showIndicator?: boolean;
  // 是否自动播放
  autoPlay?: boolean;
}

export default function Carousel(props: IProps) {
  const {
    children,
    duration = 2000,
    showIndicator = true,
    autoPlay = true,
  } = props;

  const [left, setLeft] = useState<number>(0);
  const [innerWidth, setWidth] = useState<string[]>(new Array(Array.isArray(children) ? children.length : 1).fill('0'));

  useEffect(() => {
    setWidth(innerWidth.map((item, index) => index + left === 0 ? '100%' : '0'));
    if (!autoPlay) {
      return;
    }
    const timer = setTimeout(() => {
      if (children && Array.isArray(children)) {
        if (left === 1 - children.length) {
          setLeft(0);
        } else {
          setLeft(left - 1);
        }
      }
    }, duration);
    return () => {
      clearTimeout(timer);
    }
  }, [left]);

  const play2frame = (index:number) => {
    setLeft(-index);
    setWidth(innerWidth.map((item, i) => i === index ? '100%' : '0'));
  }

  return (
    <div className='carousel-container'>
      <div className='carousel-box' data-testid='carousel-box' style={{ left: `${left * 100}vw` }}>
        {props.children}
      </div>
      <div className='indicator-container'>
        {showIndicator && children && Array.isArray(children) && children.length > 1 && children.map((node, index) => 
          <div className='indicator' key={index} onClick={() => { play2frame(index) }}>
            <div 
              data-testid={`indicator-${index}`}
              className='indicator-inner' 
              style={{ transition: autoPlay ? `width ${duration/1000}s linear` : '', visibility: innerWidth[index] === '0' ? 'hidden' : 'visible', width: innerWidth[index] }}
            />
          </div>
        )}
      </div>
    </div>
  )
}
