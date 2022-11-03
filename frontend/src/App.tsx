import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";



const App = (props:any):JSX.Element => {
  const items:Array<any> = [
    {title: 'xPhone', describe: 'Lots to love,Less to spend.<br/>Starting at $399.'},
    {title: 'Tablet', describe: 'Just the right amount of everything.'},
    {title: 'Buy a Tablet or xPhone for college.<br/>Get arPods.', describe: '', bgImage:'./static/images/airpods.png'}
  ]
  return <Carousel 
          role={props.role} 
          items={items} 
          // autoPlay={false} 
          // showIndicators={false}
        />
}

export default App;
