import React, { FC, useState, useEffect, useCallback, useMemo } from 'react';
import classnames from 'classnames';
import './index.scss';

interface SliderProps {
  duration?: number;
  speed?: number;
  items: React.ReactElement[];
}

let t: NodeJS.Timer | null = null;
let isStop: boolean = false;

const Slider: FC<SliderProps> = ({
  duration = 2000,
  speed = 180,
  items,
}) => {
  const [curIndex, setCurIndex] = useState(0);
  const size = items.length;

  const clear = useCallback(() => {
    t && clearInterval(t);
  }, [])

  const play = useCallback(() => {
    t = setInterval(() => {
      !isStop && setCurIndex((index) => (index + 1) % size);
    }, duration);
  }, [size, duration]);

  const handleOver = useCallback(
    (index: number, isOver: boolean) => {
      setCurIndex(index);
      clear();
      play();
      isStop = isOver;
    },
    [play, clear],
  );

  useEffect(() => {
    play();
    return () => {
      clear();
    };
  }, [play, clear]);

  return (
    <div className='slider-wrapper'>
      <div className={classnames('loop-list', { [`loop-list-item${curIndex}`]: true })}>
        {items.map((item, index) => {
          return <div className='loop-item' key={index}>{item}</div>;
        })}
      </div>
      <div className='loop-btn'>
        {items.map((item, index) => {
          return <div className='slider-btn' onMouseOver={() => handleOver(index, true)} onMouseOut={() => handleOver(index, false)}>
            <span className={classnames('slider-btn-p', { active: index === curIndex })} style={{ animationDuration: `${duration}ms` }} />
          </div>
        })}
      </div>
    </div >
  );
};

export default Slider;
