import React, { FC, useEffect, useMemo, useRef, useState } from 'react';
import CarouselItem, { CarouselItemPropType } from './carousel-item';
import Indicators from './indicators';
import styles from './index.module.less';

interface CarouselPropType {
  autoPlay?: boolean; // 是否自动轮播
  activeIndex?: number; // 初始化轮播index
  dataSource: CarouselItemPropType[]; // 数据源
  duration?: number; // 停留时间
  speed?: number; // 播放速度
  indicators?: boolean;
}

const Carousel: FC<CarouselPropType> = (props) => {
  const { autoPlay, activeIndex, dataSource, duration, speed, indicators } = props;
  const maxIndex = dataSource.length;

  const [currentIndex, setCurrentIndex] = useState(activeIndex);

  // 播放定时器
  const playTimer = useRef(null);

    // 播放
    const play = () => {
        playTimer.current = setInterval(() => {
          setCurrentIndex((prev) => (prev + 1) % maxIndex);
        }, duration || 10);
      };
    
      // stop
      const stop = () => {
        playTimer?.current && clearInterval(playTimer?.current);
      };
      
      const handleClickIndicator = (_index) => {
        setCurrentIndex(_index);
        if (autoPlay) {
          stop();
          play();
        }
      };
    
      const transformStyle = useMemo(() => {
        return {
          width: `${maxIndex * 100}%`,
          transform: `translate(-${currentIndex / maxIndex * 100}%, 0px) translateZ(0px) translate3d(0, 0, 0)`,
          transitionDuration: `${speed}ms`,
        }
      }, [maxIndex, currentIndex, speed]);
    
      useEffect(() => {
        if (autoPlay) {
          play();
        }
        return () => {
          stop();
        }
      }, [])
    
      return (
        <div className={styles.carousel}>
          {/* 轮播内容 */}
          <div className={styles['carousel-list']} style={transformStyle}>
            {
              dataSource.map((item, index) => (
                <CarouselItem
                  key={index}
                  title={item?.title}
                  subTitle={item?.subTitle}
                  imgUrl={item?.imgUrl}
                />
              ))
            }
          </div>
      {/* 进度条 */}
      {
        indicators && (
          <Indicators
            activeIndex={currentIndex}
            autoPlay={autoPlay}
            dataSource={dataSource}
            duration={duration}
            clickItem={handleClickIndicator}
          />
        )
      }
    </div>
  );
};

export default Carousel;

Carousel.defaultProps = {
  autoPlay: true,
  activeIndex: 0,
  duration: 3000,
  speed: 500,
  indicators: true,
};    