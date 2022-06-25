import React from "react";
import Carousel from './Carousel/Carousel';
import { AirpodsPage, PhonePage, TabletPage } from './Page';
import './App.css';

function App() {
  return (
    <div className='App'>
      <Carousel>
        <PhonePage />
        <TabletPage />
        <AirpodsPage />
      </Carousel>
    </div>
  );
}

export default App;
