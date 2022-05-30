import React from 'react';
import Carousel from './components/carousel';

import './App.css';

function App() {
  const data = [
    {
      className: 'iphone',
      title: 'xPhone',
      text: <>Lots to love. less to spend.<br/>Starting at $399.</>
    },
    {
      className: 'tablet',
      title: 'Tablet',
      text: 'Just the right amount of everything.'
    },
    {
      className: 'airpods',
      title: <>Buy a Tablet or xPhone for college.<br/>Get arPods.</>
    }
  ];

  return <div className="App">
    <Carousel>
      {
        data.map((item, index) => <div key={index} className={item.className}>
          <div className="title">{item.title}</div>
          { item.text && <div className="text">{item.text}</div> }
        </div>)
      }
    </Carousel>
  </div>;
}

export default App;
