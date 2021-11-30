import React, { ReactElement, useEffect, useState } from "react";
import "./index.css";

const ANIMATION_TIME = 4;

export interface RemoteData {
  id: number;
  title: string;
  subTitle: string;
  backgroundColor?: string;
  color?: string;
  imgUrl: string;
}
export interface Part {
   /** 轮播父组件 props， 必须有key */
  data: any;
   /** 轮播组件*/
  component: ReactElement;
}
export interface Props {
  /** 轮播 块 list */
  list: Array<Part>;
   /** 轮播切换事件 */
  onChange?: (index: number) => void;
}

function Carousel({ list, onChange }: Props) {
  const [index, setIndex] = useState(0);

  useEffect(() => {
    setTimeout(() => {
      if (index + 1 < list.length) {
        setIndex(index + 1);
        onChange && onChange(index + 1);
      } else {
        setIndex(0);
        onChange && onChange(0);
      }
    }, ANIMATION_TIME * 1000);
  }, [index, list.length]);

  return (
    <div className="carousel">
      <div
        className="carousel-pages"
        style={{
          width: `${list.length * 100}vw`,
          transform: `translate(${-100 * index}vw,0)`,
        }}
      >
        {list.map((part) => {
          return (
            <div className="carousel-page" {...part.data}>
              {part.component}
            </div>
          );
        })}
      </div>
      <div className="carousel-dots-wrapper">
        <div className="carousel-dots">
          {list.map((part) => {
            return (
              <div
                className="carousel-dot"
                style={{ width: `${25 / list.length}vw` }}
                key={part.data.key}
              >
                <div
                  className="white-bar"
                  style={
                    list[index].data.key === part.data.key
                      ? {
                          width: "100%",
                          transition: `width ${ANIMATION_TIME}s ease`,
                        }
                      : {}
                  }
                ></div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

const renderPart = (json: RemoteData) => {
  return (
    <div className="part">
      <div className="container">
        <h1 className="title">{json.title}</h1>
        <h2 className="subtitle">{json.subTitle}</h2>
      </div>
      <img className="img" src={json.imgUrl} alt={json.title}></img>
    </div>
  );
};

Carousel.buildPartSample = (list: Array<RemoteData>) => {
  return list.map((item: RemoteData) => {
    return {
      data: {
        key: item.id,
        style: {
          backgroundColor: item.backgroundColor || "none",
          color: item.color || "none",
        },
      },
      component: renderPart(item),
    } as Part;
  });
};
export default Carousel;
