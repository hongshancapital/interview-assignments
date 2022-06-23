import React from 'react';
import { CarouselContentPropsType } from './interface';
import styles from './index.module.css';

const TitleDomMemo = React.memo(
  ({ title, fontColor = '#000' }: { title?: string; fontColor?: string }) => {
    return title ? (
      <div className={styles.title} style={{ color: fontColor }}>
        {title}
      </div>
    ) : null;
  }
);

const DescriptionDomMemo = React.memo(
  ({
    description,
    fontColor = '#000',
  }: {
    description?: string;
    fontColor?: string;
  }) => {
    return description ? (
      <div className={styles.description} style={{ color: fontColor }}>
        {description}
      </div>
    ) : null;
  }
);

/**
 * @description: 每个轮播图子项的可业务定制化内容
 * @param {string} title 大标题
 * @param {string} description 描述
 * @param {string} imgUrl 图片地址
 * @param {string} fontColor 文字颜色，默认为#000黑色
 * @author: Zhao Min 曌敏
 */
const CarouselContent = (props: CarouselContentPropsType) => {
  const { title, description, imgUrl, fontColor } = props;
  return (
    <div
      className={styles.body}
      style={{
        backgroundImage: `url(${imgUrl})`,
        backgroundPosition: 'center -10vh',
        backgroundSize: 'auto 100%',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <TitleDomMemo {...{ title, fontColor }} />
      <DescriptionDomMemo {...{ description, fontColor }} />
    </div>
  );
};

export default React.memo(CarouselContent);
