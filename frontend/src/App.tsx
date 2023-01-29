import './App.css';
import { useState, useEffect, useRef, useCallback } from 'react';
import useCarouselContent from './customHook/carouselContentHook';
import useProgress from './customHook/progressHook';

function App() {
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
    <div className='App'>
      {
        useCarouselContent({
          children: <div><p className='title'>xPhone</p><p className='text'>Lots to love. Less to spend.</p><p className='text'>Starting at $399.</p></div>,
          iconName: 'iphone',
          index: 0,
          activeIndex,
        })
      }
      {
        useCarouselContent({
          children: <div><p className='title'>Tablet</p><p className='text'>Just the right amount of everything.</p></div>,
          iconName: 'tablet',
          index: 1,
          activeIndex,
        })
      }
      {
        useCarouselContent({
          children: <div className='title'><p>Buy a Tablet or xPhone for college.</p><p>Get arPods.</p></div>,
          iconName: 'airpods',
          index: 2,
          activeIndex,
        })
      }
      <div className='footer'>
        {
          useProgress(activeIndex, 0)
        }
        {
          useProgress(activeIndex, 1)
        }
        {
          useProgress(activeIndex, 2)
        }
      </div>
    </div>
  );
}

export default App;
