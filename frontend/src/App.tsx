import React from "react";
import "./App.scss";
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

import Rotation from './components/Rotation';

function App() {
  const imgs: string[] = [airpods, iphone, tablet]
  return <div className="App">
    <Rotation imgs={imgs} duration={2000} />
  </div>;
}

export default App;
