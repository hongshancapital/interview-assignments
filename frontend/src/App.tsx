import React, {useRef} from "react";

import Carousel from './components/index'
import phoneImg from '../src/assets/iphone.png'
import tabletImg from '../src/assets/tablet.png'
import airpodsImg from '../src/assets/airpods.png'
import "./App.css";

function App() {
  const imgList = [
    {
      title: <p>xPhone</p>,
      description: <p>Lots to love.Less to spendStarting at $399</p>,
      imgUrl: phoneImg,
      color: "#fff"
    },
    {
      title: <p>Tablet</p>,
      description: <p>Just the right amount of everything</p>,
      imgUrl: tabletImg,
      color: "#000"
    },
    {
      title: <><p>Buy a Tablet or xPhone for college.</p><p>Get arPods</p></>,
      description: "",
      imgUrl: airpodsImg,
      color: "#000"
    }
  ];

  const ref = useRef<HTMLDivElement>(null)

  return (
      <div className="App" ref={ref}>
        <Carousel>
          {
            imgList.map((item, index) => {
              return <Carousel.Item key={index}>
                <div className="carousel-content" style={{background: `url(${item.imgUrl})`, color: item.color}}>
                  <h3 className="title">
                    {item.title}
                  </h3>
                  <div className="text">
                    {item.description}
                  </div>
                </div>
              </Carousel.Item>
            })
          }
        </Carousel>
      </div>
  );
}

export default App;
