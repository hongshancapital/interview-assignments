import React, { CSSProperties } from "react";
import "./index.css";

export interface SliceProps {
  title: string;
  titleStyle?: CSSProperties;
  subtitle?: string;
  subtitleStyle?: CSSProperties;
  image: string;
  backgroundStyle?: CSSProperties;
}
const Slice: React.FC<SliceProps> = ({
  title,
  subtitle,
  titleStyle,
  subtitleStyle,
  image,
}) => {
  return (
    <div className="slice-container">
      <img src={image} alt={title} />
      <div className="slice-content">
        <div className="title" style={titleStyle}>
          {title}
        </div>
        <div className="text" style={subtitleStyle}>
          {subtitle}
        </div>
      </div>
    </div>
  );
};
export default Slice;
