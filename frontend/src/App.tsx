import React from "react";
import "./App.css";
import Carousel from './component/Carousel';
function App() {
  return (
      <Carousel autoplay>
        <Carousel.Slider className="slider-iphone-container">
          <div className='slider-common slider-iphone'>
              <div className='slider-inner-common slider-iphone-inner'>
                <div>xPhone</div>
                <div>Lots to love. Less to spend.</div>
                <div>Starting at $399.</div>
              </div>
          </div>
        </Carousel.Slider>
        <Carousel.Slider className="slider-tablet-container">
          <div className='slider-common slider-tablet'>
              <div className='slider-inner-common slider-tablet-inner'>
                <div>Tablet</div>
                <div>Just the right amount of everything.</div>
              </div>
          </div>
        </Carousel.Slider>
        <Carousel.Slider className="slider-airpods-container">
          <div className='slider-common slider-airpods'>
              <div className='slider-inner-common slider-airpods-inner'>
                <div>Buy a Tablet or xPhone for college.</div>
                <div>Get arPods.</div>
              </div>
          </div>
        </Carousel.Slider>
        <Carousel.Pagination />
      </Carousel>
  )
     

}

export default App;
