import { useState } from "react";
import "./index.scss";

const Carousel: React.FC<{
  items?: {
    titleList: string[];
    descList?: string[];
    img: string;
    bgColor?: string;
    color?: string;
  }[];
  option?: {
    duration?: number;
    transition?: number;
  };
}> = (props) => {
  const option = { duration: 3000, transition: 500, ...props.option };
  const itemList = props.items || [];
  const [activeIndex, setActiveIndex] = useState(0);

  const onAnimationEnd: React.AnimationEventHandler = () => {
    if (activeIndex >= itemList.length - 1) {
      setActiveIndex(0);
    } else {
      setActiveIndex(activeIndex + 1);
    }
  };

  const jumpTo = (i: number) => {
    if (i !== activeIndex && i <= itemList.length - 1 && i >= 0) {
      setActiveIndex(i);
    }
  };

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel-container"
        style={{
          transform: `translateX(-${activeIndex * 100}%)`,
          transition: `transform ${option.transition}ms`,
        }}
      >
        {itemList.map((item, i) => (
          <div
            className="carousel-item"
            key={i}
            style={{
              backgroundColor: item.bgColor || "#111111",
              color: item.color || "#ffffff",
            }}
          >
            <div className="carousel-item-title">
              {item.titleList.map((str, i) => (
                <p key={i}>{str}</p>
              ))}
            </div>
            <div className="carousel-item-desc">
              {item.descList?.map((str, i) => (
                <p key={i}>{str}</p>
              ))}
            </div>
            <img className="carousel-item-img" src={item.img} alt="carousel" />
          </div>
        ))}
      </div>

      <div className="carousel-process">
        {itemList.map((_item, i) => (
          <div
            className="carousel-process-item"
            key={i}
            onClick={() => {
              jumpTo(i);
            }}
          >
            <div className="carousel-process-duration">
              {activeIndex === i ? (
                <div
                  className="carousel-process-duration-inner"
                  style={{
                    animationDuration: `${option.duration}ms`,
                  }}
                  onAnimationEnd={onAnimationEnd}
                ></div>
              ) : null}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
