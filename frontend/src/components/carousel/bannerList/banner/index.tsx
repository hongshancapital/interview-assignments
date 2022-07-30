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
  title: string;
  them?: Them_Enum;
  text?: string;
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
  return (
    <div className={`banner_control ${them === Them_Enum.dark && Them_Enum.dark}`} style={bannerStyle}>
      <h1 className="title">{title}</h1>
      {text && <p className="text">{text}</p>}
    </div>
  );
};

export default Banner;
