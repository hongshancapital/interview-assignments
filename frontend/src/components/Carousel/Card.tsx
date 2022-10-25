import React from 'react';

// 卡片数据
export interface ICardData {
  imgUrl: string; // 图片地址
  titles: string[]; // 标题
  des?: string[]; // 描述
  background?: string; // 背景色
  color?: string; // 字体颜色 默认黑色
}

/**
 * 幻灯片单个卡片组件
 */
export const Card: React.FC<{ data: ICardData }> = ({ data }) => {
  const { imgUrl, titles, des, background, color } = data;
  return (
    <div className="carousel-card" style={{ background, color }}>
      <h1 className="title">
        {titles.map((title) => (
          <p key={title}>{title}</p>
        ))}
      </h1>
      {des?.map((str, i) => (
        <p className="des" key={`des${i}`}>
          {str}
        </p>
      ))}
      <div
        className="carousel-img"
        style={{ backgroundImage: `url(${imgUrl})` }}
      />
    </div>
  );
};

export default Card;
