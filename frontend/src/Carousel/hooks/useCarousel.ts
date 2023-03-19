import { useState, useEffect, useRef, useMemo, useLayoutEffect, useCallback } from 'react';
import { IUseCarouselOptions } from '../types';

/**
 * 轮播图逻辑 封装为 hooks
 */
export default function useCarousel(options: IUseCarouselOptions & {
  /** 轮播图容器 dom ref */
  containerRef: React.RefObject<HTMLDivElement>
}) {

  const { autoPlay = false, items, defaultActiveKey, autoPlayGap = 3000, containerRef } = options;

  // 当前 active 的 key
  const [activeKey, setActiveKey] = useState<string | undefined>(() => {
    if (defaultActiveKey) {
      const idx = items.findIndex(item => {
        return item.key === defaultActiveKey
      })
      if (idx !== -1) return defaultActiveKey;
    }
    return items[0]?.key;
  })

  const [itemWidth, setItemWidth] = useState<number | undefined>();

  const ref = useRef<ReturnType<typeof setTimeout>>();
  
  // 轮训改变 activeIndex
  useEffect(() => {
    const count = items.length;
    clearTimeout(ref.current);
    if (!autoPlay || !count) {
      ref.current = undefined;
      return;
    }
    ref.current = setTimeout(() => {
      let next: number = Infinity;
      // 没有指定 activeKey 且上次 items 为空数组
      if(!activeKey) {
        next = 0
      } else {
        // 
        const idx = items.findIndex(item => item.key === activeKey);
        if (idx !== -1) {
          // 递增判断
          next = idx + 1;
          // 我想回到过去
          if (next >= count) {
            next = 0;
          }
        } else {
          // items 大变样，换了一批 key
          next = 0;
        }
      }
      if (next !== Infinity) setActiveKey(items[next].key);
    }, autoPlayGap);
  } , [autoPlay, autoPlayGap, activeKey, items])

  // 计算容器的宽度，确认走马灯区域
  useLayoutEffect(() => {
    if (!containerRef.current) return;
    // 初始化一次，写里面就行
    const calculateItemWidth = () => {
      if (containerRef.current) {
        setItemWidth(containerRef.current.clientWidth);
      }
    };
    // 初始化执行一次
    calculateItemWidth();
    window.addEventListener('resize', calculateItemWidth);
    return () => {
      // 清除定时器
      window.clearTimeout(ref.current);
      // 清除事件注册
      window.removeEventListener('resize', calculateItemWidth);
    }
  }, [])

  const activeIndex = useMemo(() => {
    const idx = items.findIndex(item => item.key === activeKey);
    if (idx === -1) return 0;
    return idx;
  }, [activeKey, items])

  const next = useCallback(() => {
    if (!items.length) return;
    if (activeIndex >= items.length - 1) {
      setActiveKey(items[0].key);
    } else {
      setActiveKey(items[activeIndex + 1].key)
    }
  }, [activeIndex, items]);

  const last = useCallback(() => {
    const len = items.length;
    if (!len) return;
    if (activeIndex < 1) {
      setActiveKey(items[len - 1].key);
    } else {
      setActiveKey(items[activeIndex - 1].key)
    }
  }, [activeIndex, items]);

  const goTo = useCallback((key: string) => {
    if (!items.length) return;
    if (items.find(p => p.key === key)) {
      setActiveKey(key);
    }
  }, [items]);

  /** 卡片容器宽度相关样式 */
  const itemListStyle = useMemo(() => {
    if (!itemWidth) return undefined;
    return {
      width: itemWidth * items.length,
      marginLeft: 0 - activeIndex * itemWidth,
    }
  }, [itemWidth, items.length, activeIndex])
  

  return useMemo(() => ({
    /** 当前轮播中的卡片 key */
    activeKey,
    /** 当前轮播中的卡片 index */
    // activeIndex,
    /** 轮播组件容器宽度 */
    itemWidth,
    /** 卡片容器宽度相关样式 */
    itemListStyle,
    /** 轮播到指定卡片 */
    goTo,
    /** 轮播前进 */
    next,
    /** 轮播后退 */
    last,
  }), [activeKey, itemWidth, itemListStyle, goTo, next, last])
}