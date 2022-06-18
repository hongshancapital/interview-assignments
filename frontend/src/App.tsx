import React from "react";
import "./App.css";
import Carousel from './components/Carousel/Carousel'
import IphonePage from "./components/IphonePage/IphonePage";
import AirpodsPage from './components/AirpodsPage/AirpodsPage'
import TabletPage from './components/TabletPage/TabletPage'
function App() {
  return <div className="App">
    <Carousel >
      <IphonePage/>
      <TabletPage/>
      <AirpodsPage/>
    </Carousel>
  </div>;
}

export default App;
