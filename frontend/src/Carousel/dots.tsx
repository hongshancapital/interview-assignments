import React from "react";
import cls from 'classnames'
import { ClickProps } from './slider';
import './index.css';
interface DotsProps {
  slideCount: number;
  dotPosition: 'left'|'right'|'top'|'bottom',
  clickHandler: Function;
  currentSlide: number;
}
const Dots = (props: DotsProps) => {
  const onClickLi = (e: any, options: ClickProps) => {
    e.preventDefault();
    props.clickHandler(options)
  }
  const { dotPosition } = props;
  let dots = [];
  let dotCount = props.slideCount;
  for (let i = 0; i < dotCount; i++) {
    dots.push(<li 
      style={{background: props.currentSlide === i ? '#fff': '#efefef'}}
      key={i} onClick={(e) => {
      onClickLi(e, {
        idx:i,
        type: 'dots'
      })
    }}></li>)
  }
  let dotsClass = cls({
    "syq-dots": true,
    [`syq-dots-${dotPosition}`]: true,
    "syq-dot-vertical": ['left', 'right'].includes(dotPosition)
  })
  return  <ul className={dotsClass}>{dots}</ul>

}

export default Dots