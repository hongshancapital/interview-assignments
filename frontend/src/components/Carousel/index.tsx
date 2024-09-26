
import { useEffect, useMemo, useState } from 'react';
import { CarouselProps } from './types';
import useDrivingModel from './hooks/useDrivingModel';
import ItemRenderer from './components/ItemRenderer';
import Indicator from './components/Indicator';
import styles from './index.module.scss';

function Carousel({ duration, dataSource }: CarouselProps) {
  const [current, setCurrent] = useState<number>(0);

  // 创建数据模型
  const donutLinkedMap = useDrivingModel(dataSource);

  // 根据模型设定虚拟渲染器[a,b,c]
  const [prevItem, currItem, nextItem] = useMemo(() => {
    if (!donutLinkedMap) {
      return [undefined, undefined, undefined];
    }
    const curr = donutLinkedMap[current];
    const prev = donutLinkedMap[curr.prev];
    const next = donutLinkedMap[curr.next];
    return [{ ...prev }, { ...curr }, { ...next }];
  }, [donutLinkedMap, current]);

  // 指示器部分
  const Indicators = useMemo(() => {
    const ds = dataSource || [];
    // 太多或太少的情况下忽略
    if (ds.length < 1 || ds.length > 8) {
      return null;
    }
    return (
      <div className={styles.Indicators}>
        {
          (dataSource || []).map((_, idx) => {
            let key = current;
            if (current >= dataSource.length) {
              key = current % dataSource.length;
            }
            return (
              <Indicator
                key={idx.toString()}
                isCurrent={key === idx}
                duration={duration}
              />
            )
          })
        }
      </div>
    )
  }, [dataSource, duration, current]);

  // 根据模型关系实现 autoloop 逻辑
  useEffect(() => {
    if (!nextItem) {
      return;
    }
    setTimeout(() => {
      setCurrent(nextItem.key);
    }, duration);
  }, [nextItem, duration]);

  // 1、利用 react 渲染机制完成动画效果
  // 2、优化动画路径由 触底反弹 模式为 首尾循环 模式
  // 3、考虑大数据场景的渲染
  return (
    <div className={styles.Carousel}>
      {
        prevItem && (
          <ItemRenderer
            key={prevItem.key}
            {...prevItem.data}
            style={{ transform: 'translateX(-100%)' }}
          />
        )
      }
      {
        currItem && (
          <ItemRenderer
            key={currItem.key}
            {...currItem.data}
            style={{ transform: 'translateX(0)' }}
          />
        )
      }
      {
        nextItem && (
          <ItemRenderer
            key={nextItem.key}
            {...nextItem.data}
            style={{ transform: 'translateX(100%)' }}
          />
        )
      }
      {Indicators}
    </div>
  );
}

export default Carousel;