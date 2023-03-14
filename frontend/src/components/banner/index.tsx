import React, { CSSProperties } from "react";
import "./index.scss";

export interface BannerProps {
  id?: number;
  backgroundColor?: string;
  title?: string[];
  subTitle?: string[];
  fontColor?: string;
  imgSrc?: string;
}

const Banner: React.FC<BannerProps> = ({
  id = 0,
  backgroundColor = "rgba(255, 255, 255, 1)",
  title = [],
  subTitle = [],
  fontColor = "rgba(0, 0, 0, 1)",
  imgSrc = "",
}) => {
  const titleStr = title.join("");
  const subTitleStr = subTitle.join("");
  const imgAlt = `${titleStr}${subTitleStr}`;

  // 设置背景色和字体颜色
  const setBannerStyle = (): CSSProperties => {
    return {
      color: fontColor,
      backgroundColor,
    };
  };

  return (
    <div className="banner" style={setBannerStyle()}>
      {titleStr &&
        title.map((ele: string, index: number) => (
          <h1 className="banner-title" key={`title-${index}`}>
            {ele}
          </h1>
        ))}
      {subTitleStr &&
        subTitle.map((ele: string, index: number) => (
          <h3 className="banner-sub-title" key={`sub-title-${index}`}>
            {ele}
          </h3>
        ))}
      {imgSrc && <img className="banner-img" src={imgSrc} alt={imgAlt} />}
    </div>
  );
};

export default Banner;
