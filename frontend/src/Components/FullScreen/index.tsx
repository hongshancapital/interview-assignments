import React from "react";
import "./index.css";

export interface IFullScreenProps {
  className?: string;
  title?: string;
  description?: string;
  imgUrl: string;
  style?: React.CSSProperties;
}
export const FullScreen: React.FC<IFullScreenProps> = (props) => {
  const { title, description, imgUrl, className, style } = props;

  return (
    <div
      style={{
        backgroundImage: `url(${imgUrl})`,
        ...style,
        backgroundColor: "#364d79"
      }}
      className={`full-screen ${className ? className : ""}`}
    >
      {title ? <div className="title">{title}</div> : null}
      {description ? <div className="text">{description}</div> : null}
    </div>
  );
};
