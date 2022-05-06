import React, { useState, useEffect } from 'react';
import './styles/dot.scss';

interface DotsProps {
  count: number;
  curIndex: number;
  setCurIndex: (index: number) => void;
  duration?: number;
}

/**
 * 点组件
 * @param props 参数
 * @returns 点组件
 */
const Dots = (props: DotsProps) => {
  const { count, duration = 3000, curIndex, setCurIndex } = props;
  const [dotIndex, setDotIndex] = useState(-1);

  useEffect(() => {
    setDotIndex(curIndex);
  }, [curIndex]);

  return (
    <div className="dotContainer">
      {Array.from({ length: count }).map((_, i) => (
        <div key={i} className="dotBox" onClick={() => setCurIndex(i)}>
          <div
            className={['dotCenter', dotIndex === i ? 'active' : ''].join(' ')}
            style={dotIndex === i ? { transition: `width ${duration}ms` } : undefined}
          ></div>
        </div>
      ))}
    </div>
  );
};

export default Dots;
