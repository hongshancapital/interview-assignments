import React from "react";
import "./App.css";
import img_1 from "./assets/iphone.png"
import img_2 from "./assets/tablet.png"
import img_3 from "./assets/airpods.png"

import Carousel from "./component/Carousel";

const data = [{
  id: 1,
  title: ['xPhone'],
  fontColor: '#fff',
  backgroundColor: '#111111',
  message: ['lots to love.Less to spedd.', 'Starting at $399.'],
  img: img_1
},{
  id: 2,
  title: ['Tablet'],
  fontColor: '#000',
  backgroundColor: '#fafafa',
  message: ['Just the right amount of everything'],
  img: img_2
},{
  id: 3,
  title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
  fontColor: '#000',
  backgroundColor: '#f1f1f3',
  message: [],
  img: img_3
}]

function App() {
  const slideList:any = data.map((item): JSX.Element => {
    return <div key={item.id} className='slide-tripe' style={{backgroundColor: item.backgroundColor}}>
      <div>
        <div className="slide-top" style={{ color: item.fontColor }}>
          {
            item.title.map((item, idx) => <div key={idx} className='title'>{item}</div>)
          }
          {
            item.message.map((item, idx) => <p key={idx}>{item}</p>)
          }
        </div>
        <img src={item.img}></img>
      </div>
    </div>
  })

  return <div className="App">
    <Carousel>
      {slideList}
    </Carousel>
  </div>;
}

export default App;
