import React, { useMemo } from 'react';
import { parseClassName } from '../common';

interface ProgressProps {
    percent?: number;
    width?: number|string;
    height?: number|string;
    strokeColor?: string;
    backgroundColor?: string;
    className?: string;
}

const Progress = (props: ProgressProps)=> {
  const {
    percent = 0,
    width = 60,
    height = 4,
    strokeColor = '#fafafa',
    backgroundColor = '#a9a9a9',
    className
  } = props;
  const eleHeight = useMemo(()=>{
    if(typeof height === 'number') return `${height}px`;
    return height;
  }, [height]);
  const wrapperClassName = useMemo(()=>{
    const classNameArr = ['carousel-progress', className];
    return parseClassName(classNameArr);
  }, [className]);
  return (
        <div className={wrapperClassName} style={{ width, height: eleHeight, backgroundColor }}>
            <div className="carousel-progress-bg" style={{ backgroundColor: strokeColor, width: `${percent}%`, height: '100%' }}></div>
        </div>
  );
};

export type {
  ProgressProps
};

export default React.memo(Progress);