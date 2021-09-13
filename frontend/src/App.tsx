import React from "react";
import ReactDOM from 'react-dom';
import "./App.css";
import TurnPageBtn from './component/turnPageBtn';
function App() {
  let elements=[
    (<div className="class1"><h2>iphone6</h2></div>),
    (<div className="class2"><h2>iphoneX</h2></div>),
    (<div className="class3"><h2>iphone12</h2></div>),
  ]
  return <div className="App">
      <TurnPageBtn >{elements}</TurnPageBtn>
    </div>;
}

export default App;
