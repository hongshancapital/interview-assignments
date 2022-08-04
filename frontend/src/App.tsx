import React, { memo, useMemo, CSSProperties, useEffect, useState } from "react";
import "./App.css";
import { DURING, CONFIG } from "./constant";
import { IConfig, ICarousel } from "./typings";

const { setInterval, clearInterval } = window; 

const Card = memo(({
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

const Carousel = memo(({
  interval = 3000,
  config = [],
}: ICarousel) =>{
  let timer: number = 0;
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  useEffect(() =>{
    timer = setInterval(() => {
      setCurrentIndex(prev => prev < config.length - 1 ?
        prev + 1 :
        0
      );
    }, interval);

    return () => {
      clearInterval(timer);
    }
  }, []);

  const scrollerStyle = useMemo<CSSProperties>(() => {
    return {
      width: `${100 * config.length}%`,
      transform: `translateX(${currentIndex * -100}vw)`,
    }
  }, [config.length, currentIndex]);

  return (
    <main className="App">
      <section className="scroller" style={scrollerStyle}>
        {
          config.map((item, index) => {
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
      </section>
      <aside className="slick">
        <ul className="slick-list">
          {
            config.map((_, index) => {
              return (
                <li className={`slick-dot ${index === currentIndex ? 'active' : '' }`}>
                  <div className="process" />
                </li>
              )
            })
          }
        </ul>
      </aside>
    </main>
  );
});

function App() {
 return <Carousel interval={DURING} config={CONFIG}/>
}

export default App;
