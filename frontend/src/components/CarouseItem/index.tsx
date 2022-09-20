import React from "react";
import { ICarouselItem } from "../../type/carousel/index";
import "./index.css";

const DEFAULT_IMG_HEIGHT = "50%";
const Index = ({ item }: { item: ICarouselItem }) => {
  return (
    <div className="carousel_item" style={item.style}>
      <div className="title">
        {item.title.map((c, idx) => (
          <div key={`title_${idx}`}>{c}</div>
        ))}
      </div>
      {item.desc && item.desc.length > 0 && (
        <div className="desc">
          {item.desc.map((c, idx) => (
            <div key={`desc_${idx}`}>{c}</div>
          ))}
        </div>
      )}
      <img src={item.src} alt={"alt"} style={{ height: DEFAULT_IMG_HEIGHT }} />
    </div>
  );
};

export default Index;
