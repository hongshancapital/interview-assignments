import React from 'react';
import './index.css';

/** 业务需要轮播图参数信息 */
export interface IMyCarouselItem {
  /** 主标题 */
  title?: string | string[];
  /** 副标题 */
  subTitle?: string | string[];
  /** 图片地址 */
  image?: string;
  /** 图片倍率 */
  imageRate?: number;
  /** 样式 */
  style?: {
    [k in string]: string;
  };
  /** 点击事件 */
  onClick?: (item: IMyCarouselItem) => void;
}

/**
 * 根据业务需要定制轮播组件
 */
export function MyCarouselItem(props: IMyCarouselItem) {
  const { title, subTitle, image, imageRate = 1, style, onClick } = props;

  /** 处理标题，判断是否数组 */
  const renderTitle = (target: string | string[], cls: string) => {
    if (toString.apply(target) === '[object Array]') {
      return (target as string[]).map((item: string, index: number) => (
        <div key={index} className={cls}>
          {item}
        </div>
      ));
    }
    return <div className={cls}>{target}</div>;
  };

  return (
    <div
      className='my_carousel_item'
      style={style}
      onClick={() => onClick && typeof onClick === 'function' && onClick(props)}
    >
      {image && (
        <img
          className={`my_carousel_item__image rate_${imageRate}`}
          src={image}
          alt='text'
        ></img>
      )}
      <div className='my_carousel_item__text'>
        {title && renderTitle(title, 'my_carousel_item__title')}
        {subTitle && renderTitle(subTitle, 'my_carousel_item__subtitle')}
      </div>
    </div>
  );
}
