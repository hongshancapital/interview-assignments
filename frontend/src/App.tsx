import React, { FC, ReactElement } from "react";
import "./App.css";
import Banner from './components/banner/banner'
import {IBannerImage, ICustomConfig} from "./components/banner/typing/banner";

const list:IBannerImage[] = [
  {
    image: require('./assets/iphone.png').default,
    backgroundColor: '#090909',
    des: 'iphone',
    titles: {
      color: '#FFFFFF',
      big: 'xPhone',
      small: 'Lots to love.Less to spend.\nStarting at $399.'
    },
  },
  {
    image: require('./assets/tablet.png').default,
    backgroundColor: '#FAFAFA',
    des: 'tablet',
    titles: {
      color: '#000000',
      big: 'Tablet',
      small: 'Just the right amount of everything.'
    },
  },
  {
    image: require('./assets/airpods.png').default,
    backgroundColor: '#F1F1F3',
    des: 'airpods',
    titles: {
      color: '#000000',
      big: 'Buy a Tablet or xPhone for college. \nGet arPods.',
    },
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
