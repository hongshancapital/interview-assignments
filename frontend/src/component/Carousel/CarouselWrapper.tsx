import React, {
  CSSProperties,
  Children,
  cloneElement,
  useRef,
  useReducer,
  useEffect,
  useImperativeHandle
} from 'react';
import {
  CarouselPagination
} from './CarouselPagination';
import { classnames, getPrefixCls, getElementSize, IElementSize, getDefaultElementSize } from '../../util';
import { initModelValue, modelReducer, modelContext as Context } from './model';
import { CarouselWrapperRefType, ICarouselWrapperProps } from './model/interface';

const containerCls = getPrefixCls('carousel-container');

export const CarouselWrapper = React.forwardRef<
  CarouselWrapperRefType,
  ICarouselWrapperProps
>((props, ref) => {

  const { className, children, extra } = props;

  const {  
    delay = 400,
    speed = 3000,
    autoplay = false,
    paginationColor = '#989999',
    paginationPosition = 'center',
    paginationActiveColor = '#fff', 
  } = extra;

  const wrapperCls = classnames(getPrefixCls('carousel-wrapper'), className);
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  const carouselSizeRef= useRef<IElementSize>(getDefaultElementSize());
  const timeId = useRef<NodeJS.Timeout | null >(null);
  const [state, dispatch] = useReducer(modelReducer, initModelValue);
  const {
    currentIndex,
    sliderLen,
    containerTransform
  } = state;

  const clearTimer = (): void => {
    if (timeId.current) {
      clearTimeout(timeId.current);
      timeId.current = null;
    }
  }

  const getTranslateStyle = (index: number, carouselSize: IElementSize): CSSProperties  => {
    const translateSize =
      ((sliderLen === 1) ? 0 :  -index) * carouselSize.width;
      
    return {
      transform: `translateX(${translateSize}px)`,
      transitionDuration: `${delay}ms`,
    };
  }

  const slideTo = (nextIndex: number): void  => {
    dispatch({
      type: 'UPDATE_STATE',
      payload: {
        preIndex: currentIndex,
        currentIndex:nextIndex,
        containerTransform: getTranslateStyle(nextIndex, carouselSizeRef.current)
      }
    });

    if (
      currentIndex > sliderLen - 1 &&
      sliderLen > 1
    ) {
      slideTo(0);
    }

    if (currentIndex < 0) {
      slideTo(sliderLen - 1)
    }
  }

  useImperativeHandle(ref, () => ({
    goTo: (nextIndex) => slideTo(nextIndex),
    prev: () => {
      if (sliderLen <= 1) {
        slideTo(0);
      } else {
        slideTo(currentIndex - 1);
      }
    },
    next: () => slideTo(currentIndex + 1),
  }));

  useEffect(()=>{
    // 初始化
    carouselSizeRef.current= getElementSize(wrapperRef.current as HTMLDivElement);
    dispatch({
      type: 'UPDATE_STATE',
      payload: {
        sliderLen: Children.count(children),
        carouselSize: carouselSizeRef.current
      }
    });
  }, [])

  useEffect(() => {
    if (autoplay) {
      timeId.current = setTimeout(() => {
        slideTo((currentIndex + 1) % Children.count(children));
      }, speed);
    } 

    return () => clearTimer();

  }, [currentIndex]);

  return (
    <Context.Provider value={{state, dispatch}}>
      <div ref={wrapperRef} className={wrapperCls}>
        <div className={containerCls} style={{width: `${sliderLen * carouselSizeRef.current.width}px`, ...containerTransform}}>
          {
            Children.map(children, (child: any, index) =>
              cloneElement(child, {
                key: index,
              }),
            )
          }
        </div>
        <CarouselPagination  
            speed = {speed}
            delay = {delay}
            autoplay
            paginationColor={paginationColor}
            paginationPosition={paginationPosition}
            paginationActiveColor={paginationActiveColor}  
        />
      </div>
    </Context.Provider>
  );
});
