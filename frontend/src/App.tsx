import React from "react";
import Carousel from './components/Carousel'
import "./App.css";
import xphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpod from "./assets/airpods.png";

function App() {
  const data = [
    {
      key: 0,
      title: ['xPhone'],
      subTitle: ['Lots to love.Less to speed.', 'Starting at $399'],
      image: xphone,
      backgroundColor: '#111111',
      color: '#fff',
    },
    {
      key: 1,
      title: ['Tablet'],
      subTitle: ['Just the right amount of everything'],
      image: tablet,
      backgroundColor: '#fafafa',
    },
    {
      key: 2,
      title: ['Buy a tablet or xPhone for college.', 'Get arPods'],
      subTitle: [],
      image: airpod,
      backgroundColor: '#f1f1f3',
    },
  ]

  return (
    <div className="App">
      <Carousel autoPlay>
        {data.map(item => {
          return (
            <div className="item" key={item.key} style={{ backgroundColor: item.backgroundColor, color: item.color }}>
              <div className="item-wrap">
                {
                  item.title?.map((title: String, i) => (
                    <h1 key={i}>{title}</h1>
                  ))
                }
                {
                  item.subTitle?.map((title: String, i) => (
                    <h3 key={i}>{title}</h3>
                  ))
                }
                <img className="img" src={item.image} alt="image" />
              </div>
            </div>
          )
        })}
      </Carousel>
    </div>
  )
}
export default App;
