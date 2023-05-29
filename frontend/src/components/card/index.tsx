import React, { FC, memo } from 'react';
import classnames from 'classnames/bind';
import styles from './index.module.css';

export const cn = classnames.bind(styles);

export enum CardTheme {
  Dark = 'dark',
  Gray = 'gray',
  Light = 'light',
}

/**
 * Card 组件的属性
 */
export interface CardProps {
  /**
   * 卡片标题，可以是字符串或 React 组件
   */
  title: string | React.ReactNode;
  /**
   * 卡片副标题，可以是字符串或 React 组件
   */
  subTitle?: string | React.ReactNode;
  /**
   * 卡片图片的 URL
   */
  imgUrl: string;
  /**
   * 卡片的主题，可选值为 'dark'、'gray' 和 'light'， 默认为 'light'
   */
  theme?: CardTheme; // 这里也可以优化为提取图片主题色
}
  
/**
 * Card 组件
 * @param {CardProps} props - 组件属性
 * @returns {JSX.Element}
 */

const Card: FC<CardProps> = ({ title, subTitle, imgUrl, theme = 'light' }) => {
  return (
    <div className={cn('card', theme)}>
      <div className={cn('main')}>
        <div className={cn('title')}>{title}</div>
        {
          subTitle && (
            <div className={cn('subtitle')}>{subTitle}</div>
          )
        }
      </div>
      <div className={cn('img-wrap')}>
        <img src={imgUrl} alt="图片" className={cn('img')} />
      </div>
    </div>
  )
};
export default memo(Card);
