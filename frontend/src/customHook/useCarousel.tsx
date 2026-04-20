import './index.css';
import { useState, useEffect, useRef, useCallback, ReactNode, Fragment } from 'react';
import carouselContent from './carouselContent';
import progress from './progress';

interface IData {
  // 自定义内容文本
  children: ReactNode,
  // icon
  iconName: string,
}

interface IProps {
  data: Array<IData>,
}

function useCarousel(props: IProps) {
  // 当前激活下标
  const [activeIndex , setActiveIndex] = useState(0);
  // 定时器
  const timer = useRef<number|null>(null);
  
  const clearTime = useCallback(() => {
    timer.current && clearInterval(timer.current);
  }, []);
  
  // 轮播函数
  const handleInterval = useCallback(() => {
    clearTime();
    timer.current = window.setInterval(() => {
      setActiveIndex(activeIndex === 2 ? 0 : activeIndex + 1);
    }, 3000);
  }, [activeIndex, clearTime]);

  useEffect(() => {
    handleInterval();
  }, [handleInterval]);

  // 清除定时器
  useEffect(() => {
    return () => {
      clearTime();
    }
  }, [clearTime]);
  
  return (
    <Fragment>
      {
        props.data.map((v, index) => {
          const { children, iconName } = v;
          return (
            carouselContent({
              children,
              iconName,
              index,
              activeIndex,
            })
          )
        })
      }
      <div className='footer'>
        {
          props.data.map((v, index) => {
            return (
              progress(activeIndex, index)
            )
          })
        }
      </div>
    </Fragment>
  );
}

export default useCarousel;
