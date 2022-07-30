import React, { CSSProperties, FC } from "react";
import "./index.css";

/** 主题，用于控制文本颜色 */
export enum Them_Enum {
  dark = "dark", // 暗夜
  light = "light", // 明亮
}

export interface BannerProp_Inter {
  imgUrl: string;
  backgroundColor: string;
  title: string | string[];
  them?: Them_Enum;
  text?: string | string[];
}

type BannerProp = BannerProp_Inter & {
  index: number;
};

const Banner: FC<BannerProp> = ({
  imgUrl,
  title,
  text,
  index,
  them = Them_Enum.light,
  backgroundColor,
}) => {
  const bannerStyle: CSSProperties = {
    backgroundColor: backgroundColor,
    backgroundImage: `url(${imgUrl})`,
    position: "absolute",
    top: 0,
    left: `${index}00%`,
  };

  if (!Array.isArray(title)) {
    title = [title];
  }
  const titleDOM = title.map((titleContent, index) => (
    <h1 className="title" key={index}>
      {titleContent}
    </h1>
  ));

  if (text && !Array.isArray(text)) {
    text = [text];
  }
  const textDOM =
    Array.isArray(text) &&
    text.map((textContent, index) => (
      <p role="row" className="text" key={index}>
        {textContent}
      </p>
    ));

  return (
    <div
      role="listitem" 
      className={`banner_control ${them === Them_Enum.dark && Them_Enum.dark}`}
      style={bannerStyle}
    >
      {titleDOM}
      {textDOM}
    </div>
  );
};

export default Banner;
