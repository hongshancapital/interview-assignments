import React from "react";
import "./App.css";
import Carousel from "./components/Carousel";

const Sliders = [{
  key: 'phone',
  className: 'phone',
  title: 'xPhone',
  textClass: 'white',
  description: 'Lots to love. Less to spend.\nStarting at $399.'
},
{
  key: 'tablet',
  className: 'tablet',
  title: 'Tablet',
  textClass: 'black',
  description: 'Just the right amount of everything'
},
{
  key: 'airpods',
  className: 'airpods',
  title: ' Buy a Tablet or xPhone for college. \nGet arPods',
  textClass: 'black',
}]

function App() {
  return <div className="App">
    <Carousel duration={300} delay={3000} showPagination className='carousel' >
        {Sliders.map(item => {
          return  <div key={item.key} className={`wrapper ${item.className}`}>
          <div className="title-wrapper">
            <h1 className={`title ${item.textClass}`}>
              {item.title}
            </h1>
            {item.description ? <p className={`text ${item.textClass} mrt20`}>
             {item.description}
            </p>:null }
          </div>
        </div>
        })}
        
    </Carousel>
  </div>;
}

export default App;
