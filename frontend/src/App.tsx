import React from "react";
import Carousel from "./Carousel";
import cardsData from './App.data';

import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel speed={5}>
        {cardsData.map(item => (
          <div
            key={item.id}
            className={`product-card ${item.theme}`}
            style={{
              backgroundImage: `url(${item.image})`
            }}
          >
            <div className="product-title">
              {item.title.map(
                (title, index) => (<div key={index}>{title}</div>)
              )}
            </div>
            <div className="product-text">
              {item.text.map(
                (text, index) => (<div key={index}>{text}</div>)
              )}
            </div>
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
