import React from "react";
import "./App.css";
import Carousel from './component/Carousel'

import phone from './assets/iphone.png';
import table from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {
  const initData = [
    { title: 'Xphone', 
      text: 'Lots to love. Less to spend. Starting at $399',
      url: phone,
      id: 1,
      color: '#fff'
    },
    { title: 'Table', 
      text: 'Just the right amount of everything',
      url: table,
      id: 2,
      color: '#000'
    },
    { title: 'Buy a Table or xPhone for college. Get arPods.', 
      text: '',
      url: airpods,
      id: 3,
      color: '#000'
    },
  ]
  return <div className="App">
  {
  /* write your component here */
    <Carousel initData={initData}/>
  }</div>;
}


export default App;
