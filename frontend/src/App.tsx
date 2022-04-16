import React from "react";
import Carousel,{DataProps} from './components/Carousel';
import "./App.css";

function App() {
  const list: DataProps['list'] = [
    {
      class: 'iphone',
      title: 'xPhone',
      text: <>
        <div>Lots to love.Less to spend.</div>
        <div>Starting at $399.</div>
      </>,
      // url:iphone,
    },
    {
      class: 'tablet',
      title: 'Tablet',
      text: 'Just the right amount of everything.',
      // url:table,
    },
    {
      class: 'airpods',
      title: <>
        <div>Buy a Tablet or xPhone for college.</div>
        <div>Get arPods.</div>
      </>,
      // url:airpods,
    },
  ]
  return <div className="App">
    <Carousel list={list} time={3000} />
  </div>;
}

export default App;