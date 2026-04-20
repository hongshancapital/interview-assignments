import React, { CSSProperties, useCallback, useEffect, useRef } from 'react';
import styles from './index.module.css';

interface IImage {
  styles?: CSSProperties;
  title: { text: string; extra?: string };
  description?: { text: string; extra?: string };
  img: string;
}

const SCREEN_WIDTH = window.innerWidth;

function Carousel({ resources }: { resources: IImage[] }) {
  const activeRef = useRef(0);
  const container = useRef<HTMLDivElement | null>(null);
  const pointer = useRef<HTMLDivElement | null>(null);

  // 计算需要移动的距离并进行修改
  const setTransition = useCallback((active: number) => {
    const distance = -active * SCREEN_WIDTH;
    if (container.current) {
      const containerStyle = container.current.style;
      containerStyle.transform = `translate3d(${distance}px, 0, 0)`;
    }
  }, []);

  // 图片切换
  const switchImage = useCallback(() => {
    const nextActive =
      activeRef.current < resources.length - 1 ? activeRef.current + 1 : 0;
    const l = parseInt(pointer.current!.style.left);

    if (pointer.current) {
      // 图标样式重置/递增
      const pointerStyle = pointer.current!.style;
      pointerStyle.width = 0 + 'px';
      if (nextActive === 0) {
        pointerStyle.left = 0 + 'px';
      } else {
        // notice: 此处当修改为动态
        pointerStyle.left = l + 22 + 'px';
      }
    }
    setTransition(nextActive);
    activeRef.current = nextActive;
  }, []);

  // 图标切换
  const switchPointer = () => {
    if (pointer.current) {
      const pointerStyle = pointer.current.style;
      const w = parseInt(pointerStyle.width);
      pointerStyle.width = w + 1 + 'px';
    }
  };

  useEffect(() => {
    const timer = setInterval(switchImage, 2000);
    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    const timer = setInterval(switchPointer, 100);
    return () => clearInterval(timer);
  }, []);

  return (
    <div className={styles.carousel}>
      <div
        ref={container}
        className={styles.container}
        style={{ width: resources.length * SCREEN_WIDTH }}
      >
        {resources.map((resource, index) => (
          <div
            key={index}
            style={{ left: index * SCREEN_WIDTH }}
            className={styles.items}
          >
            <div
              className={styles.content}
              style={{
                backgroundImage: `url(${resource.img})`,
                backgroundPosition: '0 0',
                backgroundSize: '100%',
                backgroundRepeat: 'no-repeat',
                ...(resource.styles ?? {}),
              }}
            >
              <div className={styles.title}>
                <div>{resource.title?.text}</div>
                <div>{resource.title?.extra}</div>
              </div>
              <div className={styles.description}>
                <div>{resource.description?.text}</div>
                <div>{resource.description?.extra}</div>
              </div>
            </div>
          </div>
        ))}
      </div>
      <div className={styles['bullet-container']}>
        <div
          className={styles['bullet-pointer']}
          ref={pointer}
          style={{ left: 0, width: 0 }}
        ></div>
        <div className={styles['bullet-content']}>
          {resources.map((resource, index) => (
            <div className={styles['bullet-item']} key={index} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default Carousel;
