import React from "react"
import "./App.css"

import Carousel from './components/Carousel'

import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'


const list  = [
  {
    title: <h1>xPhone</h1>,
    subTitle: (
      <>
        <h3>Lots to love. less to spend</h3>
        <h3>Starting at $399</h3>
      </>
    ),
    color: "#fff",
    backgroundColor: '#111111',
    backgroundImage: iphone,
  },
  {
    title: <h1>Tablet</h1>,
    subTitle: (
      <h3>Just the right amount of everything.</h3>
    ),
    color: "#000",
    backgroundColor: '#fafafa',
    backgroundImage: tablet,
  },
  {
    title: (
      <>
        <h1>Buy a Tablet or xPhone for college.</h1>
        <h1>Get airPods.</h1>
      </>
    ),
    color: "#000",
    backgroundColor: '#f1f1f3',
    backgroundImage: airpods,
  },
]

function App() {
  return <div className="App">
    <Carousel>
      {
        list.map((item, index) => {
          return (
            <div 
              className="slider"
              key={index} 
              style={{
                backgroundColor: item.backgroundColor,
                backgroundImage: `url(${item.backgroundImage})`,
                color: item.color,
              }}>
              { item.title }
              { item.subTitle }
            </div>
          )
        })
      }
    </Carousel>
  </div>;
}

export default App;
