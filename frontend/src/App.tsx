import React from "react";
import "./App.css";
import Carousel from './component/Carousel';
function App() {
  let elements = [
    (<div className="class1" key='class1'><h2>iphone6</h2></div>),
    (<div className="class2" key='class2'><h2>iphoneX</h2></div>),
    (<div className="class3" key='class3'><h2>iphone12</h2></div>),
  ]
  return <div className="App">
    <Carousel>{elements}</Carousel>
  </div>;
}

export default App;
