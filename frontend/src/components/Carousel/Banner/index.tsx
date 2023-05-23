import { CSSProperties, FC, useMemo } from "react";
import "./index.css";

/** 主题，用于控制文本颜色 */
export enum Them_Enum {
  dark = "dark", // 暗夜
  light = "light", // 明亮
}

/**
 * 轮播内容
 */
export interface BannerProp_Inter {
  /** 主图路径 */
  imgUrl: string;
  /** 背景色 */ 
  backgroundColor: string;
  /** 标题列表 */
  title: string[];
  /** 主色调(影响文本颜色,避免深色文本在深色背景上不易读) */
  them?: Them_Enum;
  /** 文本内容(小标题) */
  text?: string[];
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
  const bannerStyle: CSSProperties = useMemo(
    () => ({
      backgroundColor,
      backgroundImage: `url(${imgUrl})`,
      position: "absolute",
      top: 0,
      left: `${index}00%`,
    }),
    [index, imgUrl, backgroundColor]
  );

  const titleDOM = useMemo(
    () =>
      title.map((titleContent, index) => (
        <h1 className="title" key={index}>
          {titleContent}
        </h1>
      )),
    [title]
  );

  const textDOM = useMemo(
    () =>
      text?.map((textContent, index) => (
        <p className="text" key={index}>
          {textContent}
        </p>
      )),
    [text]
  );

  return (
    <div
      className={`banner_control ${them === Them_Enum.dark && Them_Enum.dark}`}
      style={bannerStyle}
    >
      {titleDOM}
      {textDOM}
    </div>
  );
};

export default Banner;
