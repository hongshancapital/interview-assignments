import React from "react";
import "./index.css";

export interface BannerConfig {
  /**唯一标识项 */
  key: string;
  /**图片的url */
  img: string;
  /**标题 */
  title?: string;
  /**描述 */
  des?: string;
  /**字体颜色，默认black(黑色) */
  color?: string;
}

export default function Banner(props: BannerConfig) {
  const { color = "black", title = "", des = "" } = props;
  return (
    <div className="banner" style={{ color }}>
      <img className="banner__img" src={props.img} alt={props.des} />
      <div className="banner__container">
        <h1 className="banner__h1">
          {title.split("\n").map((item, index) => {
            return (
              <React.Fragment key={index}>
                {item}
                <br />
              </React.Fragment>
            );
          })}
        </h1>
        <p className="banner__p">
          {des.split("\n").map((item, index) => {
            return (
              <React.Fragment key={index}>
                {item}
                <br />
              </React.Fragment>
            );
          })}
        </p>
      </div>
    </div>
  );
}
