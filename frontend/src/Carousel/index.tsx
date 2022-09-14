import React, {
  CSSProperties,
  useCallback,
  useEffect,
  useMemo,
  useState,
} from 'react';
import MultiLine from './MultiLine';
import useInterval from './useInterval';
import { CarouselProps, CarouselItem } from './types';
import './style.scss';

export const Carousel: React.FC<CarouselProps> = ({
  dataSource = [],
  interval = 3000,
  defaultKey,
  className,
  style,
}) => {
  // 支持设置轮播的初始项
  const [activeKey, setActiveKey] = useState(
    defaultKey || dataSource?.[0]?.key
  );

  const activeIndex = useMemo(
    () => dataSource.findIndex((x: CarouselItem) => x.key === activeKey),
    [activeKey]
  );

  // 动画播放状态
  const [isRunning, setIsRunning] = useState(true);

  useInterval(
    () => {
      if (!dataSource?.length) return;

      const currentIndex = dataSource.findIndex((x) => x.key === activeKey);
      if (currentIndex < 0) return;

      const nextIndex = (currentIndex + 1) % dataSource.length;
      const nextKey = dataSource[nextIndex]?.key;

      if (nextKey) {
        setActiveKey(nextKey);
      }
    },
    isRunning ? interval : null
  );

  // 根据页面可见性 暂停/重启 轮播
  useEffect(() => {
    const handleVisibilityChange = () => {
      setIsRunning(!document.hidden);
    };
    document.addEventListener('visibilitychange', handleVisibilityChange);
    return () => {
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    };
  }, []);

  if (!Array.isArray(dataSource) || !dataSource?.length) return null;

  return (
    <div className={`carousel ${className || ''}`} style={style}>
      {/* 轮播的内容 */}
      <div
        className="carousel-inner"
        style={{
          width: `${100 * dataSource?.length}%`,
          transform: `translate(-${
            (100 * activeIndex) / dataSource?.length
          }%, 0)`,
        }}
      >
        {dataSource.map(
          ({ key, title, description, illustration }: CarouselItem) => (
            <div
              className="carousel-item"
              style={
                {
                  '--item-width': `${100 / dataSource?.length}%`,
                  '--item-bg-image': illustration
                    ? `url("${illustration}")`
                    : '',
                } as CSSProperties
              }
              key={`item-${key}`}
            >
              <div className="content">
                {title && (
                  <div className="title">
                    <MultiLine text={title} />
                  </div>
                )}
                {description && (
                  <div className="description">
                    <MultiLine text={description} />
                  </div>
                )}
              </div>
            </div>
          )
        )}
      </div>

      {/* 控制栏 */}
      <div
        className="ctrl-bar"
        style={
          { '--carousel-interval': `${interval / 1000}s` } as CSSProperties
        }
      >
        {dataSource.map(({ key }: CarouselItem) => (
          <div
            className={`ctrl-dot ${key === activeKey ? 'active' : ''}`}
            onClick={() => {
              setIsRunning(false);
              setActiveKey(key);
            }}
            onMouseLeave={() => {
              setIsRunning(true);
            }}
            key={`dot-${key}`}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;
