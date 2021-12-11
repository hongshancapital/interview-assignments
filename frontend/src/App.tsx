import React from "react";
import "./App.css";
import Carousel from './component/Carousel';
const PREFIX_MAP: {[index: string]: string} = {
  0: 'iphone',
  1: 'tablet',
  2: 'airpods'
};

const datasource = [{
  title: 'xPhone',
  subTitle: 'Lots to love. Less to spend.',
  restInfo: 'Starting at $399.'
},{
  title: 'Tablet',
  subTitle: 'Just the right amount of everything.',
},{
  title: 'Buy a Tablet or xPhone for college.',
  subTitle: 'Get arPods.',
}];

function App() {
  return (
      <Carousel autoplay>
        {
          datasource.map((item,index) => 
            <Carousel.Slider key={item.title} className={`slider-${PREFIX_MAP[index]}-container`}>
              <div className={`slider-common slider-${PREFIX_MAP[index]}`}>
                  <div className={`slider-inner-common slider-${PREFIX_MAP[index]}-inner`}>
                    <div>{item.title}</div>
                    <div>{item.subTitle}</div>
                    {item.restInfo && <div>{item.restInfo}</div>}
                  </div>
              </div>
            </Carousel.Slider>
          )
        }
      </Carousel>
  )
     

}

export default App;
