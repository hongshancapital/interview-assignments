import React, { useState, useEffect } from 'react';
import styles from './Carousel.module.scss';
import { Item, IitemProps } from './Item';

// 可配置属性接口
interface IpropsType {
  itemList: IitemProps[]; // 轮播子元素属性
  delaySeconds?: number; // 间隔秒数
  onChange?: Function; // 回调函数
}

// 默认属性
const defaultProps: IpropsType = {
  delaySeconds: 5,
  itemList: [],
};

export const Carousel: React.FC<IpropsType> = (userProps: IpropsType) => {
  // 配置属性
  const props: IpropsType = { ...defaultProps, ...userProps };
  const { delaySeconds, itemList, onChange } = props;
  // 轮播标记
  const [currentIndex, setcurrentIndex] = useState(0);

  // 轮播定时器
  useEffect(() => {
    // 轮播控制
    const autoPlay = (): void => {
      setcurrentIndex((index) => {
        return index === itemList.length - 1 ? 0 : index + 1;
      });
      typeof onChange === 'function' && (onChange as Function)(currentIndex);
    };
    let interval: NodeJS.Timer;
    itemList.length > 1 &&
      (interval = setInterval(autoPlay, (delaySeconds as number) * 1000));
    return () => clearInterval(interval);
  }, [currentIndex, delaySeconds, onChange, itemList]);

  return (
    <div className={styles.carousel}>
      <div
        className={styles.carousel_slider}
        style={{ transform: `translate3d(-${currentIndex * 100}%, 0, 0)` }}
      >
        {itemList.map((item: IitemProps) => (
          <Item {...item} key={item.id}></Item>
        ))}
      </div>
      {/* 导航进度条 */}
      <ul className={styles.carousel_navigation}>
        {itemList.length &&
          itemList.map((item, index) => (
            <li
              key={index}
              className={index === currentIndex ? styles.active : ''}
            >
              <span
                style={{
                  animationDuration: `${delaySeconds as number}s`,
                }}
              />
            </li>
          ))}
      </ul>
    </div>
  );
};
