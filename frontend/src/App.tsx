import React from "react";
import { Carousel } from './components/Carousel'
import { Content } from './components/Content'
import "./App.css";

const items = [
  {
    color: '#fff',
    backgroundColor: '#111',
    img: require('./assets/iphone.png'),
    title: 'xPhone',
    subtitle: ['Lots to love.Less to spend.', 'Starting as $399.']
  },
  {
    color: '#000',
    backgroundColor: '#fafafa',
    img: require('./assets/tablet.png'),
    title: 'Tablet',
    subtitle: ['Just the right amount of everything.']
  },
  {
    color: '#000',
    backgroundColor: '#f1f1f3',
    img: require('./assets/airpods.png'),
    title: ['Buy a Tablet Or xPhone for college.', 'Get airPods.'],
    subtitle: []
  }
]

function App() {
  return <div className="App">
    <Carousel auto indicator width={window.document.body.clientWidth} height={window.innerHeight}>
      {items.map((item, index) => <Content key={index} {...item} />)}
    </Carousel>
  </div>;
}

export default App;
