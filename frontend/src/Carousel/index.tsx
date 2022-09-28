import React, {
  CSSProperties,
  Fragment,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from 'react';
import { CarouselProps, CarouselItem, Timer } from './types';
import './styles.scss';

export const Carousel: React.FC<CarouselProps> = ({
  dataSource = [],
  interval = 2000,
  defaultKey,
  className,
  style,
}) => {
  // 支持设置轮播的初始项
  const [activeKey, setActiveKey] = useState(defaultKey || dataSource?.[0]?.key);
  
    const activeIndex = useMemo(
      () => dataSource.findIndex((x: CarouselItem) => x.key === activeKey),
      [activeKey]
    );
    const renderMultiLine = useCallback((text: string) => (
      <>
        {String(text)
          .split('\n')
          .map((x: string, i: number) => (
            <Fragment key={x}>
              {i > 0 ? <br /> : null}
              {x}
            </Fragment>
          ))}
      </>
    ),
    []
  );

  const timer = useRef<Timer | null>(null);

  const clearTimer = useCallback(() => {
    if (timer.current) {clearInterval(timer.current);
      timer.current = null;
    }
  }, []);

  const initTimer = useCallback(() => {
    timer.current = setInterval(() => {
      const currentIndex = dataSource.findIndex((x) => x.key === activeKey);
      const nextIndex = (currentIndex + 1) % dataSource?.length;
      const nextKey = dataSource?.[nextIndex]?.key;setActiveKey(nextKey);
    }, interval);
  }, [dataSource, activeKey, interval]);

  useEffect(() => {
    clearTimer();
    initTimer();
    const handleVisibilityChange = () => {
      clearTimer();
      if (!document.hidden) {
        initTimer();
      }
    };
    document.addEventListener('visibilitychange', handleVisibilityChange);

    return () => {
      clearTimer();
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    };
  }, [clearTimer, initTimer]);

  if (!Array.isArray(dataSource) || !dataSource?.length) return null;

  return (
    <div className={`carousel ${className || ''}`.trim()} style={style}>
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
              <div className="contentCarousel">
                {title && <div className="title">{renderMultiLine(title)}</div>}
                {description && (
                  <div className="description">
                    {renderMultiLine(description)}
                  </div>
                )}
              </div>
            </div>
          )
        )}
      </div>
      <div
        className="control-bar"
        style={
          { '--carousel-interval': `${interval / 1000}s` } as CSSProperties}
          >
            {dataSource.map(({ key }: CarouselItem) => (
              <div
                className={`control-dot ${key === activeKey ? 'active' : ''}`.trim()} onClick={() => {
                  setActiveKey(key);
                }}
                key={`dot-${key}`}
              />
            ))}
          </div>
        </div>
      );
    };
    
    export default Carousel;