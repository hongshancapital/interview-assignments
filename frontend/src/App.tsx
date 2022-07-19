import React from "react";
import "./App.css";
import Carousel from './components/carousel';

const slides = [{
  class: 'xphone',
  title: 'xPhone',
  text: <>Lots to love. Less to spend.<br />Starting at $399.</>
}, {
  class: 'tablet',
  title: 'Tablet',
  text: 'Just the right amount of everything.'
}, {
  class: 'arpods',
  title: <>Buy a Tablet or xPhone for college.<br />Get arPods.</>
}];

function App() {
  return <div className="App">{<Carousel slides={slides} duration={2000}></Carousel>}</div>;
}

export default App;
