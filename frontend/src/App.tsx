import React, { useEffect, useState } from "react";
import "./App.scss";
import { getImages } from "./imageset";

function App() {
  let images = getImages();
  let [current, setCurrent] = useState(0);
  useEffect(() => {
    let timer = setInterval(() => {
      setCurrent((current + 1) % images.length);
    }, 3000);
    return () => clearInterval(timer);
  });

  return <div className="App">
    <div className="scroll" style={{
      transform: `translateX(${-current * 100}vw)`
    }}>
      {
        images.map((img, index) => {
          return <div className="page bg" style={{
            backgroundImage: `url(${img.url})`,
            color: img.color,
            transform: `translateX(${index * 100}vw)`
          }} key={index}>
            <div className="tip">
              <div className="title">{img.title?.reduce((p, c) => {
                if (p.length !== 0) {
                  p.push(<br/>);
                }
                p.push(c);
                return p;
              }, [] as (JSX.Element | string)[])}</div>
              <div className="text">{img.text?.reduce((p, c) => {
                if (p.length !== 0) {
                  p.push(<br/>);
                }
                p.push(c);
                return p;
              }, [] as (JSX.Element | string)[])}</div>
            </div>
          </div>;
        })
      }
    </div>
  </div>;
}

export default App;
