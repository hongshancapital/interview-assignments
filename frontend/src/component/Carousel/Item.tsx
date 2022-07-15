import React from 'react';
import styles from './Item.module.scss';

// slier内容接口
export interface IitemProps {
  id: string | number;
  title: string; // 标题
  img: string; // 背景图
  desc?: string; // 描述
  style?: any; // 样式
}
// 轮播内容
export const Item: React.FC<IitemProps> = (data: IitemProps) => {
  const { title, desc, img, style } = data;
  return (
    <div
      className={styles.carousel_item}
      style={{ backgroundImage: `url(${img})`, ...style }}
    >
      <h1 className={styles.carousel_slider}>{title}</h1>
      {desc && <p> {desc} </p>}
    </div>
  );
};
