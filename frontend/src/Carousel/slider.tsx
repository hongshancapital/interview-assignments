import React, { useEffect, useRef, useState } from "react";
import Dots from "./dots";
import { PrevArrow, NextArrow } from './arrows';
import './index.css';
import { CarouselProps } from "./index";

export interface ClickProps {
  idx: number, 
  type: 'dots'|'prevArrow'|'nextArrow'
}
const Slider = (props: Partial<CarouselProps>) => {
  // 获取一个slide宽度，记录当前slide
  let listWrapRef: any = useRef();
  let autoplayTimer: any = useRef();
  let isFirstRender: any = useRef(true);
  const [ currentSlide, setCurrentSlide] = useState(0)

  const { dots = true, dotPosition = 'bottom' } = props;

  // 每个slide属性添加
  let children: any = React.Children.toArray(props.children)
  let childrenCount = React.Children.count(props.children)
  let newChildren = []
  for (let i = 0;i < children.length;i++) {
    newChildren.push(
      React.cloneElement(children[i], {
          key: i,
          tabIndex: i,
          style: {
            width: '100%',
            display: 'inline-block',
            flexShrink: '0',
            ...children[i].props.style
          }
      })
    )
  }

  // 切换slide
  const changeSlide = (idx: number) => {
    let wrapWidth = listWrapRef.current.offsetWidth;
    setCurrentSlide(idx);
    listWrapRef.current.style.transform = `translate3d(-${idx * wrapWidth}px, 0px, 0px)`
  }

  // 处理指示点，箭头
  const clickHandler = (options: ClickProps)  => {
    switch(options.type) {
      case 'dots': 
        changeSlide(options.idx);
        break;
      case 'prevArrow':
        if (currentSlide - 1 >= 0) {
          changeSlide(currentSlide - 1);
        }
        break;
      case 'nextArrow':
        if (currentSlide + 1 < childrenCount) {
          changeSlide(currentSlide + 1);
        }
        break;
    }
  }

  // 自动处理

  useEffect(() => {
    const autoPlay =() => {
      if (isFirstRender.current) {
        isFirstRender.current = false;
        return;
      }
      if (currentSlide + 1 >= childrenCount) {
        changeSlide(0);
        return;
      }

      changeSlide(currentSlide + 1)
    }
    if (props.autoplay) {
      autoplayTimer.current = setInterval(autoPlay, 1000)
    }
    return () => {
      clearInterval(autoplayTimer.current)
    }
  }, [props.autoplay, currentSlide, childrenCount])

  

  return (
    <div className="syq-slider">
      {currentSlide !== 0 && !!props.needArrows  && <PrevArrow clickHandler={clickHandler} />}
      <div className="syq-slider-list">
        <div className="syq-slider-list-wrap" ref={listWrapRef}>
          {newChildren}
        </div>
      </div>
      {currentSlide + 1 !== childrenCount && !!props.needArrows  && <NextArrow clickHandler={clickHandler} />}
      {
        !!dots &&
          <Dots 
            slideCount={newChildren.length} 
            dotPosition={dotPosition} 
            clickHandler={clickHandler}
            currentSlide={currentSlide}
            />
      }
    </div>
  )
}

export default Slider;