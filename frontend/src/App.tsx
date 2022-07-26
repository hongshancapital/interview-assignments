import React, { useState, useEffect } from "react";
import "./App.css";
import Carouse from "./component/Carousel"
function App() {
  const [imgdata, setImg] = useState<any>([])
  useEffect(() => {
    setImg([
      { title: "arPods", content: "Buy a Tablet or xPhone for college.Get", img: "/images/airpods.png" },
      { title: "xPhone", content: "Lots love.Less to spend Starting at $399", img: "/images/iphone.png" },
      { title: "Tablet", content: "Just the right amount of everything", img: "/images/tablet.png" },
    ])
   
    
  }, [])
 
  
  return <div className="App">
    <Carouse imgdata={imgdata} />
  </div>;
}

export default App;
