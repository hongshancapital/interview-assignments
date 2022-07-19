import React from "react";
import "./App.css";
import Carousel from "./components/Carousel/index";

function App() {
  const phone = require('../src/assets/iphone.png');
  const tablet = require('../src/assets/tablet.png');
  const airpods = require('../src/assets/airpods.png');
  // return <div className="App">{/* write your component here */}</div>;
  const datas = [
    {title: 'xPhone', subTitle: 'Lots to love.Less to spend.Starting at $399.', color: '#fff', bg: phone},
    {title: 'Tablet', subTitle: 'Just the right amount of everything.', bg: tablet},
    {title: 'Buy a Tablet or xPhone for college.Get arPods.', subTitle: '', bg: airpods}
  ];
  return (<div className="App" 
  style={{
    height: '500px'
  }}
  >
    {<Carousel datas={datas} />}
  </div>);
}

export default App;
