import React from "react";
import Carousel from "./components/carousel";
import "./App.css";

function App() {
  return <div className="App" style={{ width: 1200 }}>
    <Carousel data={[
      { img: 'https://t7.baidu.com/it/u=3435942975,1552946865&fm=193&f=GIF' },
      { img: 'https://t7.baidu.com/it/u=727460147,2222092211&fm=193&f=GIF' },
      { img: 'https://t7.baidu.com/it/u=2511982910,2454873241&fm=193&f=GIF' },
    ]} delay={3000} />
  </div>;
}

export default App;
