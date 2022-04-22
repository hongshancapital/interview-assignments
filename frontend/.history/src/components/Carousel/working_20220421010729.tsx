import React from 'react';

interface Props {
  count: number;
  time: number;
  activeIndex: number; 
}

export default function Progress(props: Props) {
  const {count, time, activeIndex } = props;
  return <div className='carousel-progress'>
    {Array.from({length: count}).map((_, index) => {
      const isActive = index === activeIndex;
      return (<div className='carousel-progress-item'>
        <div className={isActive ? 'active' : 'disable'} style={{transition: isActive ? `${time/1000}s`: ''}}></div>
      </div>)
    })}
  </div>
} 