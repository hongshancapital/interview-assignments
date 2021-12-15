import React, { Fragment, useEffect, useMemo, useRef, useState } from "react";
import "./style.css";

import { formateTextToArray } from './config';
import { ICarouselPropsType, ICarouselViewType, ICarouselRefType } from './type';


/**
 * 工具函数
 */
 let animateFlag: number;

 let timerFlag: number;

const renderText = (className: string) => {
  return (texts: string[], color: string | undefined): JSX.Element => {
    let style = {}
    if (color) {
      style = { color };
    }
    return (
      <Fragment>
        {
          texts.map(t => {
            return (
              <div className={className} style={style} key={t}>
                <span>{t}</span>
              </div>
            );
          })
        }
      </Fragment>
    );
  };
};

const renderTitle = renderText('carousel-view-title');

const renderSubTitle = renderText('carousel-view-subTitle');

const lastActive = (cur: number, length: number): number => (cur + length - 1) % length;

const nextActive = (cur: number, length: number): number => (cur + 1) % length;

const playFlagMask = (el: HTMLDivElement | null, frames: number) => {
  if (!el) {
    return;
  }
  let count = 1;
  const w = Math.round((100 / frames) * 100) / 100;
  const animate = () => {
    if (count <= frames) {
      el.style.width = `${w * count}%`;
      count += 1;
      animateFlag = requestAnimationFrame(animate);
    }
  };

  animate();
};

const resetMaskState = (el: HTMLDivElement | null, initValue: number = 0) => {
  if (el) {
    el.style.width = `${initValue}%`;
  }
};

/**
 * 轮播图组件
 */
const Carousel = (props: ICarouselPropsType): JSX.Element => {
  const { delay = 3000, auto = true, onChange } = props;

  const flagRef = useRef<ICarouselRefType>({}); 
  const [active, setActive] = useState<number>(0);
  const [translateX, setTranslateX] = useState<string>('translateX(0)');

  const list = useMemo<ICarouselViewType[]>(() => {
    const config = props?.config;
    if (!Array.isArray(props.config)) {
      return [];
    }
    const width = 100 / config.length + 'px';
    return config.map(item => {
      const { title, subTitle, img } = item;
      return {
        ...item,
        title: formateTextToArray(title),
        subTitle: formateTextToArray(subTitle),
        imgStyle: {
          width,
          // backgroundColor: `${backgroundColor}`
          backgroundImage: `url(${img})`
        }
      }
    });
  }, [props.config]);

  // 动画完成需要多少帧
  const frames = useMemo<number>(() => delay === 0 ? 1 : Math.round(delay / 16), [delay]);

  const autoPlay = (index: number) => {
    const last = lastActive(index, list.length);
    const curEl = flagRef.current[index];
    const lastEl = flagRef.current[last];
    resetMaskState(lastEl);
    playFlagMask(curEl, frames);
  };

  const handlePlay = (index: number) => {
    if (index < 0 || index >= list.length) {
      return
    }
    setActive(v => {
      if (animateFlag) {
        cancelAnimationFrame(animateFlag);
      }
      resetMaskState(flagRef.current[v]);
      const x = Math.round(100 / list.length * index * 100) / 100;
      setTranslateX(`translateX(-${x}%)`);
      if (auto) {
        autoPlay(index);
      } else {
        resetMaskState(flagRef.current[index], 100);
      }
      return index;
    });
  };

  const handleClick = (index: number) => {
    clearTimeout(timerFlag);
    handlePlay(index);
  }

  useEffect(() => {
    clearTimeout(timerFlag);
    if (auto) {  
      timerFlag = window.setTimeout(() => handlePlay(nextActive(active, list.length)), delay);
    }
  }, [auto, delay, active, list]);

  useEffect(() => onChange && onChange(active), [active, onChange]);

  useEffect(() => {
    if (auto) {
      autoPlay(active);
    } else {
      resetMaskState(flagRef.current[active], 100);
    }
    return () => {
      clearTimeout(timerFlag);
      cancelAnimationFrame(animateFlag);
    };
  }, []);

  
  if (!list.length) {
    return <Fragment></Fragment>;
  }

  return (
    <div className="carousel-view">
      <div className="carousel-view-box" style={{
        width: `${list.length * 100}%`,
        transform: translateX
      }}>
        {
          list.map(item => {
            return (
              <div className="carousel-view-img" key={item.id} style={item.imgStyle}>
                {/* <img src={item.img} alt="not found" /> */}
                <div className="carousel-view-text">
                  {renderTitle(item.title, item?.color)}
                  {renderSubTitle(item.subTitle, item?.color)}
                </div>
              </div>
            );
          })
        }
      </div>
      <div className="carousel-view-flag">
        {
          list.map((item, index) => {
            const isActive = index === active;
            return (
              <div className={`carousel-view-rect ${isActive ? 'active' : ''}`} key={item.id} onClick={() => handleClick(index)}>
                <div className="carousel-view-mask" ref={el => flagRef.current[index] = el}></div>
              </div>
            );
          })
        }
      </div>
    </div>
  );
}

export default React.memo(Carousel);
