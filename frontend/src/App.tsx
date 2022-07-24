import React from "react";
import Carousel from "./components/carousel";
import PageLayout from './components/page-layout';

import iphoneIcon from './assets/iphone.png';
import ipadIcon from './assets/tablet.png';
import airpodIcon from './assets/airpods.png';
import "./App.css";

function App() {
  const height = '100vh';
  return <div className="App">
    <Carousel defaultActiveIndex={0} height={height}>
      <PageLayout imageUrl={iphoneIcon} backgroundColor="#111111">
        <h1>xPhone</h1>
        <h2>Lots to love. Less to spend.</h2>
        <h2>Starting at $399.</h2>
      </PageLayout>
      <PageLayout imageUrl={ipadIcon} backgroundColor="#fafafa">
        <h1>Tablet</h1>
        <h2>Just the right amount of everything.</h2>
      </PageLayout>
      <PageLayout imageUrl={airpodIcon} backgroundColor="#f1f1f3">
        <h1>Buy a Tablet or xPhone for college.</h1>
        <h1>Get arPods.</h1>
      </PageLayout>
    </Carousel>
  </div>;
}

export default App;
