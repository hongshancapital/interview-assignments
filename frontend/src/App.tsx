import React from "react"
import "./App.css"
import Carousel from "./components/Carousel"

function App() {
  const list = [
    {
      titles: ['xPhone'],
      subTitles: ['Lots to love. Less to spend.', 'Starting at $399.'], 
      img: require('./assets/airpods.png')
    },
    {
      titles: ['Tablet'],
      subTitles: ['Just the right amount of everything.'], 
      color: '#fff',
      img: require('./assets/iphone.png')
    },
    {
      titles: ['Buy a Tablet or xPhone for College.', 'Get arPods'], 
      img: require('./assets/tablet.png')
    },
  ]
  return <div className="App">
    <Carousel>
      {
        list.map((item, index) => 
          <div className="c-item" style={{color: item.color}} key={index}>
            <img className="c-img" src={item.img} alt={item.titles.join(',')} />
            {item.titles.map(i => <div className="c-title" key={i}>{i}</div>)}
            {item.subTitles && item.subTitles.map(i => <div className="c-desc" key={i}>{i}</div>)}
          </div>
        )
      }
    </Carousel>
  </div>
}

export default App;
