import React from "react";
import { slideItem } from "../defination";
import "./index.css";

type slideProps = {
  sildeData: slideItem;
}

function Slide(props: slideProps) {
  const { theme, title, description, picture } = props.sildeData;
  return <div className={"slide slide-" + theme}
    style={{
      backgroundImage: 'url(' + picture + ')',
      width: window.innerWidth + 'px',
      minWidth: window.innerWidth + 'px',
    }}>
    <div className="slide-title">{title}</div>
    <div className="slide-description text">{description}</div>
  </div>;
}

export default Slide;
