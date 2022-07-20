import React from "react";
import "./App.scss";
import {Carousel} from "./Carousel"
const MockList = [
  {
    title: 'xPhone',
    des: 'Lots to love. Less to spend.\nStarting at $399',
    className: 'dark'
  },
  {
    title: 'Tablet',
    des: 'Just the right amount of everything',
    className: 'light'
  },
  {
    title: 'Buy a Tablet or xPhone for college.\nGet arPods',
    className: 'middle'
  }
]
function App() {
  return (<div className="App">
    <Carousel >
    {MockList.map(({title, des, className}, index) => {
          return (
            <>
             <div className={`carousel-item-${index} ${className}`}>
               <div className="carousel-item-content">
                 <div className="carousel-item-title">
                   {title}
                 </div>
                 <p className="carousel-item-des">
                     {des || ''}
                   </p>
               </div>
             </div>
            </>
          )
        })}
    </Carousel>
    </div>);
}

export default App;
