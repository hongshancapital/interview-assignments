import { useEffect, useState } from 'react';
import { DEFAULT_ANIMATION_CONFIG } from '../../constant';
import { AnimationConfig, SlideItem } from '../../models';
import { getCarouselData } from '../../services';

let timer: NodeJS.Timeout | null = null;

/** 走马灯组件hooks */
export const useCarousel = () => {
  const [curIndex, setCurIndex] = useState(0);
  const [slideItems, setSlideItems] = useState<SlideItem[]>([]);
  const [animationConfig, setAnimationConfig] = useState<AnimationConfig>(DEFAULT_ANIMATION_CONFIG);
    
  useEffect(() => {
    const { slideItems, animationConfig } = getCarouselData();
    setSlideItems(slideItems);
    setAnimationConfig(animationConfig);

    // 启动duration间隔轮换
    timer = setInterval(() => {
      setCurIndex(pre => pre + 1 >= slideItems.length ? 0 : pre + 1)
    }, animationConfig.duration ?? 3000);

    // 组件卸载，清除定时器
    return () => {
        if (timer) {
            clearInterval(timer)
        }
    }
  }, []);

  /** 手动切换后，重启定时器 */
  const doRestart = () => {
    timer && clearInterval(timer);
    timer = setInterval(() => {
      setCurIndex(pre => pre + 1 >= slideItems.length ? 0 : pre + 1)
    }, animationConfig.duration ?? 3000);
  }

  /** 点击指示器切换滑块 */
  const changeIndicator = (index: number) => {
    if (index === curIndex) return;
    
    setCurIndex(index);
    doRestart();
  };

  return {
    curIndex,
    slideItems,
    animationConfig,
    changeIndicator,
  }
}
