import React, { useCallback, useMemo, useState } from 'react';
import AutoProgress from './AutoProgress';
import { ProgressProps } from './AutoProgress/Progress';
import { parseClassName } from './common';
import eventPluginHandler, { EventPlugin } from './event-plugin-handler';
import createHoverPausePlugin from './default-plugins/create-hover-pause-plugin';
import createDraggablePlugin from './default-plugins/create-draggable-plugin';
import { PluginContext, BaseEleProps } from './types';

import './index.scss';


interface CarouselProps extends BaseEleProps {
    children: React.ReactNode[];
    plugins?: EventPlugin<PluginContext>[];
    duration?: number;
    progressProps?: Partial<Omit<ProgressProps, 'percent'>>
}

const Carousel = (props: CarouselProps)=>{
  const {
    className,
    children,
    plugins,
    duration = 3000,
    progressProps = {},
    style,
    ...extra
  } = props;
    // 完整的className
  const wrapperClassName = useMemo(()=>{
    const classNameArr = ['carousel-layout', className];
    return parseClassName(classNameArr);
  }, [className]);
  // 当前active项
  const [activeIndex, setActiveIndex] = useState(0);
  // 控制活动项是否暂停
  const [paused, setPaused] = useState(false);
  // 容器样式控制，为插件提供样式控制机制
  const [cusStyle, setCusStyle] = useState<React.CSSProperties>({});

  // 插件方法的上下文，使自定义插件有控制当前活动项、暂停、容器样式设置的操作权限
  const context= useMemo<PluginContext>(()=>({
    setActiveIndex,
    setPaused,
    setStyle: setCusStyle,
    getActiveIndex: ()=>activeIndex,
    getLength: ()=>children.length
  }), [activeIndex, children.length]);

  const attrsAndEvents = useMemo(()=>{
    const eventObj = plugins? eventPluginHandler(plugins, context) : {};
    return {
      ...extra,
      ...eventObj
    } as BaseEleProps;
  }, [extra, plugins, context]);
  // 进度结束时跳转至下一项
  const goToNext = useCallback(()=>{
    setActiveIndex(idx=>{
      const nextIdx = (idx + 1) % children.length;
      return nextIdx;
    });
  }, [children.length]);
  return (
        <article
            className={wrapperClassName}
            {...attrsAndEvents}
            style={style}>
            <section className="carousel-wrapper" style={{ transform: `translateX(${-100 * activeIndex}%)`, ...cusStyle }}>
                {
                  children
                }
            </section>
            <section className="carousel-progress-wrapper">
                    {
                      Array(children.length).fill(null).map((_, idx) => (
                        <AutoProgress
                          key={idx} // progress表示第几项当前的状态，和每项具体内容没关联，key用idx不会引起逻辑错误
                          className="carousel-progress"
                          duration={duration}
                          stoped={activeIndex !== idx}
                          paused={activeIndex !== idx || paused}
                          onEnd={goToNext}
                          {...progressProps}></AutoProgress>
                      ))
                    }
                </section>
        </article>
  );
};
// 滚动条选项
const CarouselOption = (props: BaseEleProps) => {
  const { className, children, ...extra } = props;
  const wrapperClassName = useMemo(()=>{
    const classNameArr = ['carousel-option', className];
    return parseClassName(classNameArr);
  }, [className]);
  return (
        <section className={wrapperClassName} {...extra} style={{ width: '100%' }}>
          {
            children
          }
        </section>
  );
};
// Option是Carousel的children，为避免重复渲染，以React.memo包裹
Carousel.Option = React.memo(CarouselOption);
Carousel.plugins = {
  createHoverPausePlugin,
  createDraggablePlugin
};

export type {
  CarouselProps
};

export default Carousel;