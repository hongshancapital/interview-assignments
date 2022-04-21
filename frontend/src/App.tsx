import React from "react";
import Carousel,{DataProps} from './component/Carousel';
import "./App.css";

function App() {
  const list: DataProps['list'] = [
    {
      class: 'iphone',
      title: ['xPhone'],
      text: ['Lots to love.Less to spend.','Starting at $399.']
    },
    {
      class: 'tablet',
      title: ['Tablet'],
      text: ['Just the right amount of everything.'],
    },
    {
      class: 'airpods',
      title: ['Buy a Tablet or xPhone for college.','Get arPods.'],
    }
  ]
  return <div className="App">
    <Carousel list={list} time={3000} />
  </div>;
}
export default App;