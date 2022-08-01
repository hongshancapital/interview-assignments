import React, { memo } from "react";
import "./bannerItem.scss";
export interface BannerProps {
  title: string; //大标题
  subTitle?: string; // 副标题
  img: string; //图片
  color: string;
}

const BannerItem: React.FC<BannerProps> = memo((props) => {
  const { title, subTitle, img, color } = props;
  let style: React.CSSProperties = {
    backgroundImage: `url(${img})`,
    color,
  };
  return (
    <div className="banner-item" style={style}>
      <div className="title">{title}</div>
      {subTitle ? <div className="sub-title">{subTitle}</div> : null}
    </div>
  );
});

export default BannerItem;
