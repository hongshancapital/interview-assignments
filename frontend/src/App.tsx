import React, { useEffect, useRef } from "react";

import { Carousel, CarouselRefValue } from "./components/carousel";
import { Page } from "./components/page";

import "./App.css";

const options = [
  {
    title: "xPhone",
    content: (
      <>
        Lots to love. Less to spend.
        <br />
        Starting at $399.
      </>
    ),
    image: "https://tva1.sinaimg.cn/large/94d1eb77gy1h0rwyfgtooj20dy0h8gn2.jpg",
  },
  {
    theme: "light" as const,
    title: "Tablet",
    content: "Just the right amount of everything.",
    image: "https://tva1.sinaimg.cn/large/94d1eb77gy1h0rwyfgtooj20dy0h8gn2.jpg",
  },
  {
    title: "Buy a Tablet or xPhone for collage.",
    content: "Get arPods.",
    image: "https://tva1.sinaimg.cn/large/94d1eb77gy1h0rwyfgtooj20dy0h8gn2.jpg",
  },
];

export default function App() {
  const ref = useRef<CarouselRefValue | null>(null);

  // 因为目前场景是全屏展示，所以可以增加一个键盘控制
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      // 实际情况远比判断 input 要复杂
      // 这里的目的是 当页面焦点在input等内容中时不应该触发切换
      if (document.activeElement?.tagName === "INPUT") {
        return;
      }

      if (e.key === "ArrowLeft") {
        ref.current?.prev();
      } else if (e.key === "ArrowRight") {
        ref.current?.next();
      }
    };
    document.addEventListener("keydown", handleKeyDown);

    return () => document.removeEventListener("keydown", handleKeyDown);
  }, []);

  return (
    <div className="App">
      <Carousel
        ref={ref}
        autoplay
        direction="horizontal"
        defaultActiveIndex={0}
        interval={3000}
        indicatorPlacement="bottom"
        pauseOnHover={false}
        showDefaultIndicators
        style={{ height: "100vh" }}
      >
        {options.map((el, i) => (
          <Page className="slidePage" key={i} theme={el.theme}>
            <div className="title">{el.title}</div>
            <div className="text">{el.content}</div>
            <img className="image" src={el.image} width="120" alt="" />
          </Page>
        ))}
      </Carousel>
    </div>
  );
}
