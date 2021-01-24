import React from "react";
import style from "./App.module.css";
import Carousel from "./components/Carousel";
import CarouselSlide from "./components/CarouselSlide";
import { filterClassName } from "./utils/filterClassName";

function App() {
  return (
    <div className={style.App}>
      <Carousel interval={3000}>
        <CarouselSlide>
          <div className={filterClassName(style.page, style.page1)}>
            <div className={style.title}>xPhone</div>
            <div className={style.text}>Lots to love. Less to spend.<br/>Starting at $399.</div>
          </div>
        </CarouselSlide>
        <CarouselSlide>
          <div className={filterClassName(style.page, style.page2)}>
            <div className={style.title}>Tablet</div>
            <div className={style.text}>Just the right amount of everything.</div>
          </div>
        </CarouselSlide>
        <CarouselSlide>
          <div className={filterClassName(style.page, style.page3)}>
            <div className={style.title}>Buy a Tablet or xPhone for college.<br/>Get arPods</div>
          </div>
        </CarouselSlide>
      </Carousel>
    </div>
  );
}

export default App;
