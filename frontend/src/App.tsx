import React from "react";
import "./App.css";
import Casrousel from "./Components/Carousel";
import Tablet from "./assets/tablet.png";
import Iphone from "./assets/iphone.png";
import AirPods from "./assets/airpods.png";
function App() {
  const casrouselList = [
    {
      id: 1,
      title: <div>xPhone</div>,
      titleStyles: {
        color: '#fff',
        fontSize: '42px',
        fontWeight: 'bold'
      },
      subText: (
        <>
          <div>Lots to love.Less to spend</div>
          <div>Starting at $399</div>
        </>
      ),
      subTextStyles: {
        color: '#fff',
        fontSize: '36px',
      },
      casrouselImage: Iphone,
    },
    {
      id: 2,
      titleStyles: {
        color: '#000',
        fontSize: '42px',
        fontWeight: 'bold'
      },
      title: <div>Tablet</div>,
      subTextStyles: {
        color: '#000',
        fontSize: '36px',
      },
      subText: <span>Just the right amount of everything.</span>,
      casrouselImage: Tablet,
    },
    {
      id: 3,
      title: (
        <>
          <div>Buy a Tablet or xPhone for college.</div>
          <div>Get airPods</div>
        </>
      ),
      titleStyles: {
        color: '#000',
        fontSize: '42px',
        fontWeight: 'bold'
      },
      casrouselImage: AirPods,
    },
  ];
  return (
    <div className="App">
      <Casrousel casrouselList={casrouselList} />
    </div>
  );
}

export default App;
