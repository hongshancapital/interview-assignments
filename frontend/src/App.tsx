import React from "react";
import "./App.css";
import useCarousel from "./hooks/useCarousel";

import pic0 from './assets/iphone.png';
import pic1 from './assets/tablet.png';
import pic2 from './assets/airpods.png';

const items = [
  {
    bgCover: pic0,
    content: (
      <div style={ { color: 'white' } }>
        <p className="title">xPhone</p>
        <p className="text">Lots to love. Less to spend.<br/>Starting at $399.</p>
      </div>
    ),
  },
  {
    bgCover: pic1,
    content: (
      <div>
        <p className="title">Tablet</p>
        <p className="text">Just the right amount of everything</p>
      </div>
    )
  },
  {
    bgCover: pic2,
    content: (
      <div>
        <p className="title">Buy a Tablet or xPhone for college.</p>
        <p className="title">Get arPods.</p>
      </div>
    )
  }
]

function App() {
  const {
    activeIndex,
    duration,
    pass,
    resume,
    pause,
    select,
  } = useCarousel(items, 3000);

  return <div className="App">
    <div className="carousel">
      <div className="carousel-items-wrapper" style={ { transform: `translate(${- activeIndex * 100}%, 0px)` }}>
        {
          items?.map((item, index) => (
            <div
              className="carousel-item"
              key={`c_i_${index}`}
              style={ { backgroundImage: `url(${item.bgCover})` }}
            >{ item.content }</div>
          ))
        }
      </div>
      <div className="carousel-anchors">
        {
          items?.map((item, index) => (
            <div className="carousel-item-anchor" key={`c_i_a_${index}`}
              onMouseOver={ () => { pause(index) } }
              onMouseOut={ () => { resume(index) } }
              onClick={ () => { select(index) } }
            >
              <div
                className="carousel-item-anchor-body"
                style={ { width: activeIndex === index ? `${ (pass / duration * 100).toFixed(2) }%` : '0%' } }
              ></div>
            </div>
          ))
        }
      </div>
    </div>
  </div>;
}

export default App;
