import React, { FC } from 'react';
import { CarouselItemPropType } from './carousel-item';
import styles from './index.module.less';

interface IndicatorsPropsType {
  autoPlay?: boolean; // 是否自动轮播
  activeIndex?: number; // 初始化轮播index
  dataSource: CarouselItemPropType[]; // 数据源
  duration?: number; // 停留时间
  clickItem
}

const Indicators: FC<IndicatorsPropsType> = ({ dataSource, activeIndex, duration, autoPlay, clickItem }) => {
  console.log('duration', duration)
  return (
    <div className={styles['carousel-indicators']}>
      {
        dataSource.map((_item, _index) => {
          const isActive = activeIndex === _index;
          return (
            <div
              key={`indicators-${_index}`}
              className={`${styles['carousel-indicators-item']} ${isActive ? styles['active'] : ''}`}
              onClick={() => clickItem(_index)}
            >
              <div
                style={{
                  animationDuration: `${duration}ms`,
                  animationIterationCount: autoPlay ? 'infinite' : 1,
                }}
              />
            </div>
          )
        })
      }
    </div>
  );
}

export default Indicators;