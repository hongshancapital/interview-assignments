import React, { FC, useMemo } from "react";
import "./index.scss";

export interface Props {
  currentIndex: number;
  activeIndex: number;
  animationDuration: string;
  onAnimationEnd: Function;
}

const Indicator: FC<Props> = props => {

  const { currentIndex, animationDuration, onAnimationEnd, activeIndex } = props;

  const isActive = useMemo(() => currentIndex === activeIndex, [currentIndex, activeIndex]);

  const style = useMemo(() => ({
    animationDuration: `${animationDuration}`
  })
    , [animationDuration])

  const classNames = useMemo(() => {
    const cls = {
      className: ['indicator-item', isActive ? 'is-active' : ''].join(' ')
    }
    if (typeof onAnimationEnd === 'function') {
      Reflect.set(cls, 'onAnimationEnd', onAnimationEnd);
    }
    return cls;
  }, [isActive, onAnimationEnd]);

  return <div className="indicator">
    <div {...classNames} style={style}></div>
  </div>
}

export default Indicator;
