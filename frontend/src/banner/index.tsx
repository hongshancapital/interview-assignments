import React from "react";
import "./index.css";

/**banner配置选项 */
export interface BannerConfig {
  /**唯一标识项 */
  key: string;
  /**图片的url */
  img: string;
  /**标题，可以接受一个字符串，一个LabelConfig对象，或者LabelConfig对象数组 */
  title?: string | LabelConfig | (string | LabelConfig)[];
  /**字体颜色，默认black(黑色) */
  color?: string;
}

/**文字配置选项 */
interface LabelConfig extends React.CSSProperties {
  /**文字，默认是空字符串 */
  label?: string;
}

export default function Banner(props: BannerConfig) {
  let { color = "black", title = [] } = props;

  //将title字段处理为labelConfig[]格式，以便处理多行title的情况
  if (!(title instanceof Array)) {
    title = [title];
  }
  const newTitle = title.map((item) => {
    if (typeof item === "string") return { label: item };
    return item;
  });

  return (
    <div className="banner" style={{ color }}>
      <img
        className="banner__img"
        src={props.img}
        alt={newTitle.map((i) => i.label).join(" ")}
      />
      <div className="banner__container">
        {/* banner标题部分 */}
        <div className="banner__title">
          {newTitle.map((item, index) => {
            let { label, ...cssProps } = item;
            return (
              <div key={index} style={cssProps}>
                {label}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}
