/*
 * @Author: DJscript
 * @Date: 2023-03-31 14:57:46
 * @LastEditTime: 2023-03-31 16:12:32
 * @FilePath: /frontend/src/Carousel.tsx
 * @Description: 轮播图
 */
import React from 'react';
import { useInterval } from './hooks';

// TODO 后续优化点：1.指示器进度动画 2.用户鼠标悬停时停止轮播 3.用户点击指示器时切换到对应图片 4.支持用户设置图片宽度

/**
 * 指示器
 */
const Indicator: React.FC<{ count: number; current: number }> = ({
  count,
  current,
}) => {
  const indicators = Array.from({ length: count }).map((_, index) => (
    <div
      key={index}
      style={{
        background: current === index ? '#fff' : '#666',
        width: 50,
        height: 2,
      }}
    />
  ));
  return (
    <div
      style={{
        position: 'fixed',
        bottom: 100,
        left: '50%',
        transform: 'translateX(-50%)',
      }}
    >
      <div style={{ display: 'flex', gap: 5 }}>{indicators}</div>
    </div>
  );
};

const Carousel: React.FC<React.PropsWithChildren<{ delay?: number }>> = ({
  children,
  delay = 2,
}) => {
  const imgcount = React.Children.count(children);
  const [current, setCurrent] = React.useState(0);
  useInterval(() => {
    if (current < imgcount - 1) {
      setCurrent(current + 1);
    } else {
      setCurrent(0);
    }
  }, delay * 1000);
  return (
    <>
      <div
        style={{
          display: 'flex',
          transition: 'all 1s ease-in-out',
          transform: `translateX(-${current * 100}vw)`,
        }}
      >
        {React.Children.map(children, (child, idx) => {
          if (React.isValidElement(child)) {
            return (
              <div key={child.key || idx} style={{ width: '100vw' }}>
                {React.cloneElement(child, {
                  style: {
                    ...child.props.style,
                    width: '100vw',
                    objectFit: 'contain',
                    height: '100vh',
                  },
                } as any)}
              </div>
            );
          }
          return child;
        })}
      </div>
      <Indicator count={imgcount} current={current} />
    </>
  );
};

export default Carousel;
