import React, { FC, ReactElement } from "react";
import "./App.css";
import Banner from './components/banner/banner'
import {IBannerImage, ICustomConfig} from "./components/banner/typing/banner";

const list:IBannerImage[] = [
  {
    image: require('./assets/iphone.png').default,
    backgroundColor: '#000000',
    des: 'iphone'
  },
  {
    image: require('./assets/tablet.png').default,
    backgroundColor: '#F9F9F9',
    des: 'tablet'
  },
  {
    image: require('./assets/airpods.png').default,
    backgroundColor: '#EDEDF0',
    des: 'airpods'
  },
]
const config:ICustomConfig = {
  stayTime: 3,
  rollTime: 1
}

const App:FC = ():ReactElement => {
  return <div className="App">
    <Banner data={list} config={config}/>
  </div>;
}



export default App;
