import React from "react";
import "./App.css";
import Carousel from './component/Carousel';
function App() {
  return <div className="App">
    <Carousel>{[
    <div className="iphoneX" key='iphoneX'><h2>xPhone</h2>
      lots to love . Less  to spend .<br/> Starting  at $399.
    </div>,
    <div className="tablelet" key='tablelet'><h2>Tablelet</h2>
    Just the right amount of everthing.
    </div>,
    <div className="airpods" key='airpods'><h2> Buy a Table or xPhone for college.<br/>
    Get arPods.</h2>
    </div>,
  ]}</Carousel>
  </div>;
}

export default App;
