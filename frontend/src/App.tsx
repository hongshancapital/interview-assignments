import React from "react";
import "./App.css";

import { Carousel, Carouselitem } from './components';

import carouselSettings from './constants';

function App() {
  return (
    <div className="App">
      <Carousel>
        {carouselSettings.map(
          ({ title, desc, img, backgroundColor, color, key }) => {
            return (
              <Carouselitem
                title={title}
                desc={desc}
                img={img}
                backgroundColor={backgroundColor}
                color={color}
                key={key}
              ></Carouselitem>
            )
          }
        )}
      </Carousel>
    </div>
  );
}

export default App;
