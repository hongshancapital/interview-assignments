import React, { useRef, useState, ReactNode, CSSProperties } from "react";
import Bar from "./Bar";

import "./style.css";

export interface Props {
  toolbarStyle?: CSSProperties;
  style?: CSSProperties;
  data: Array<{
    title: string;
    icon?: ReactNode;
    description?: ReactNode;
    theme?: string;
    style?: CSSProperties;
  }>;
}

function Index({ toolbarStyle, data, style }: Props) {
  const el = useRef(null);
  const [current, setCurrent] = useState(0);
  const [rolling, setRolling] = useState(false);
  const onFinish = (needIndex?: number) => {
    setRolling(true);
    setCurrent((prevCurrent) => {
      const nid = needIndex !== undefined ? needIndex : prevCurrent;
      const index = (nid + 1) % data.length;
      const currentEl = el.current.children[index];
      if (currentEl) currentEl.scrollIntoView();
      return index;
    });
  };

  let timer: number;
  const onScroll = () => {
    clearTimeout(timer);
    timer = Number(setTimeout(() => setRolling(false), 100));
  };

  return (
    <div
      role="carousel"
      className="carousel"
      style={style}
      ref={el}
      onScroll={onScroll}
    >
      {data.map((item) => (
        <div
          className="carousel-item"
          data-theme={item.theme}
          key={item.title}
          style={item.style}
        >
          <h2>{item.title}</h2>
          <p>{item.description}</p>
          <i>{item.icon}</i>
        </div>
      ))}
      <div className="carousel-bar" style={toolbarStyle}>
        {data.map((item, index) => (
          <Bar
            // 滚动完成后再设置计时属性
            active={!rolling && current === index}
            onFinish={onFinish}
            key={item.title}
            attr={{
              title: item.title,
              onMouseEnter: () => {
                // 样式设置了paused，只在非当前索引猝发
                if (current !== index) onFinish(index - 1);
              },
            }}
          />
        ))}
      </div>
    </div>
  );
}

export default Index;
