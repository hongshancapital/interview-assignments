import React, { FC, useEffect, useState } from "react";
import './carousel.css';

interface IPanel {
  /**
   * 背景色
   */
  backgroundColor: string;
  /**
   * 文字颜色
   */
  color: string;
  /**
   * 背景图片
   */
  img: string;
  /**
   * 标题，多行时传入数组
   */
  title: string | string[];
  /**
   * 描述，多行时传入数组
   */
  desc?: string | string[];
}

interface IProps {
  /**
   * 每帧的内容
   */
  data: IPanel[];
  /**
   * 每帧展示的时间，单位毫秒，默认3000
   */
  delay?: number;
  /**
   * 宽度，默认100%
   */
  width?: number | string;
  /**
   * 高度，默认600
   */
  height?: number | string;
}

const Carousel: FC<IProps> = ({ data = [], delay = 3000, width = '100%', height = 600 }) => {

  const [index, setIndex] = useState<number>(0);

  useEffect(() => {
    if (!data.length) return;

    let timer = window.setTimeout(() => {
      setIndex(index >= data.length - 1 ? 0 : index + 1);
      timer = 0;
    }, delay);
    return () => {
      if (timer) {
        window.clearTimeout(timer);
      }
    }
  }, [data, index, delay]);

  return (
    <div className="carousel-container" style={{ width }}>
      <div className="panel-list" style={{ transform: `translate3d(-${index * 100}%, 0, 0)` }}>
        {
          data.map((panel, i) =>
            <div className="panel"
              key={i}
              style={{
                height: height,
                backgroundColor: panel.backgroundColor,
                backgroundImage: `url(${panel.img})`
              }}
            >
              <div className="words" style={{ color: panel.color }}>
                {
                  (panel.title instanceof Array ? panel.title : [panel.title]).map(title => (
                    <div className="title">{title}</div>
                  ))
                }
                {
                  (panel.desc instanceof Array ? panel.desc : [panel.desc]).map(desc => (
                    <div className="text">{desc}</div>
                  ))
                }
              </div>
            </div>
          )
        }
      </div>
      <div className="index-container">
        {
          data.map((panel, i) =>
            <div className={`index ${index === i ? 'current' : ''}`} key={i}
              onClick={() => setIndex(i)}
            >
              {
                index === i &&
                <div className="progress" style={{ animationDuration: delay / 1000 + 's' }}></div>
              }

            </div>
          )
        }
      </div>
    </div>
  );
}

export default Carousel;
