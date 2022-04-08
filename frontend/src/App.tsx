import React, { useEffect, useState } from "react";
import "./App.css";
import SwiperView from "./views/swiper";
import { SwiperAdapter } from './adapter/swiper';
import get from 'lodash/get';

function App() {
  const [payload, setPayload] = useState({});
  useEffect(() => {
    console.log('##app mounted--->')
    setPayload(SwiperAdapter.getPayload());
  }, [])
  return <div className="App">
    {/* write your component here */}
    <SwiperView 
      autoPlay={get(payload, 'autoPlay', true)}
      sliders={get(payload, 'sliders', [])}
    ></SwiperView>
  </div>;
}

export default App;
