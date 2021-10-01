import React from "react";
import "./App.css";
import Carousel from './component/Carousel';
function App() {
  const data = [
    {
      clss:'iphoneX',
      text:'lots to love . Less  to spend .<br/> Starting  at $399.'
    },
    {
      clss:'tablelet',
      text:'Just the right amount of everthing.'
    },
    {
      clss:'airpods',
      text:'Buy a Table or xPhone for college.Get arPods.'
    },
  ]

   let html = data.map((item)=>{
   return  ( <div className={item.clss} key={item.clss}><h2>{item.clss}</h2>
    {item.text}
    </div>)
  })
  return <div className="App">
    <Carousel>{html}</Carousel>
  </div>;
}

export default App;
