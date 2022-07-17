import React from "react";
import {Carousel} from "./components/carousel";
import IphoneIcon from './assets/iphone.png'
import TabletIcon from './assets/tablet.png'
import AirpodsIcon from './assets/airpods.png'
import "./App.css";

const MockData = [
  {
    title: 'xPhone',
    text: 'Lots to love. Less to spend.\nStarting at $399',
    icon: IphoneIcon,
    className: 'dark'
  },
  {
    title: 'Tablet',
    text: 'Just the right amount of everything',
    icon: TabletIcon,
    className: 'light'
  },
  {
    title: 'Buy a Tablet or xPhone for college.\nGet arPods',
    icon: AirpodsIcon,
    className: 'middle'
  }
]

function App() {
  return (
    <div className="App">
      <Carousel duration={3000}>
        {MockData.map(({title,text, icon, className},index) => {
          return (
            <div className={`item ${className}`} key={index}>
              <div className={'description'}>
                <div className={'title'}>{title}</div>
                {text && <div className={'text'}>{text}</div>}
              </div>
              <img src={icon} alt={'icon'} className={'icon'}/>
            </div>
          )
        })}
      </Carousel>
    </div>
  )
}

export default App;
