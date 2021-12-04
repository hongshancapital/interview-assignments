import React from 'react';
import 'reset-css';
import "./App.css";
import Carousel from './components/Carousel';

const datasource = [{
  titles: ['xPhone'],
  texts: ['Lots of love. Less to spend.', 'Starting at $399'],
  image: require('./assets/iphone.png').default,
  theme: 'dark'
}, {
  titles: ['Tablet'],
  texts: ['Just the right amount of everything.'],
  image: require('./assets/tablet.png').default,
  theme: 'bright'
}, {
  titles: ['Buy a tablet or xPhone for college.', 'Get airePods.'],
  texts: [],
  image: require('./assets/airpods.png').default
}];

function App() {
  return <div className="App">
    <Carousel datasource={datasource} duration={2000}></Carousel>
  </div>;
}


export default App;
