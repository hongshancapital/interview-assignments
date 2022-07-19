import React, { useState, useEffect } from 'react';
import './styles/dot.css';

interface DotsProps {
  count: number;
  duration?: number;
  curIndex: number;
}

/**
 * 点组件
 * @param props 参数
 * @returns 点组件
 */
const Dots = (props: DotsProps) => {
  const { count, duration = 3000, curIndex } = props;
  const [dotIndex, setDotIndex] = useState(-1);

  useEffect(() => {
    setDotIndex(curIndex);
  }, [curIndex]);

  return (
    <div className='dotContainer'>
      {
        Array.from({ length: count }).map((_, i) => (
          <div key={i} className='dotBox'><div className={['dotCenter', dotIndex === i ? 'active' : ''].join(' ')} style={dotIndex === i ? {transition: `width ${duration}ms`} : undefined}></div></div>
        ))
      }
    </div>
  );
};

export default Dots;
