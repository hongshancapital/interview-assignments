import React, { memo, useMemo, CSSProperties, useEffect, useState, Children } from "react";
import "./App.css";
import { DURING, CONFIG } from "./constant";
import { IConfig, ICarousel } from "./typings";

const { setInterval, clearInterval } = window; 

export const Card = memo(({
  title = [],
  content = [],
  fontColor = "#000",
  backgroundImage = "",
}: IConfig) => {
  const style: CSSProperties = {
    color: fontColor,
    backgroundImage: `url(${backgroundImage})`,
  }

  return (
    <section style={style} className="card">
      <div className="title">
        {
          title.map((text, index) => <h1 key={`${index}-${text}`}>{text}</h1>)
        }
      </div>
      <div className="content text">
        {
          content.map((text, index) => <p key={`${index}-${text}`}>{text}</p>)
        }
      </div>
    </section>
  );
});

export const Carousel = memo(({
  interval = 3000,
  children,
}: ICarousel) =>{
  let timer: number = 0;
  const [currentIndex, setCurrentIndex] = useState<number>(-1);
  const count = Children.count(children);

  useEffect(() =>{
    setCurrentIndex(0);
  }, []);

  useEffect(() =>{
    timer = setInterval(() => {
      setCurrentIndex(prev => prev < count - 1 ? prev + 1 : 0);
    }, interval);

    return () => {
      clearInterval(timer);
    }
  }, []);

  const scrollerStyle = useMemo<CSSProperties>(() => ({
    width: `${100 * count}%`,
    transform: `translateX(${Math.max(currentIndex, 0) * -100}vw)`,
  }), [count, currentIndex]);

  const getProcessStyle = (index: number): CSSProperties =>
    index === currentIndex ?
      { transitionDuration: `${interval / 1000}s` } :
      {};

  return (
    <main className="App">
      <section className="scroller" style={scrollerStyle}>
        { children }
      </section>
      <aside className="slick">
        <ul className="slick-list">
          {
            new Array(count).fill(0).map((_, index) => (
              <li
                key={`${index}-slick-dot`}
                className={`slick-dot ${index === currentIndex ? 'active' : '' }`}
              >
                <div className="process" style={getProcessStyle(index)} />
              </li>
            ))
          }
        </ul>
      </aside>
    </main>
  );
});

function App() {
 return (
  <Carousel interval={DURING}>
    {
      CONFIG.map((item, index) => {
        const { title, content, fontColor, backgroundImage} = item;
        return <Card
          key={`${index}-${title}`}
          title={title}
          content={content}
          fontColor={fontColor}
          backgroundImage={backgroundImage}
        />
      })
    }
  </Carousel>
 )
}

export default App;
