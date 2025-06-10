import React from "react";
import './App.css';
import Carousel from './components/Carousel';

import img1 from './assets/iphone.png';
import img2 from './assets/tablet.png';
import img3 from './assets/airpods.png';

function App() {
  const data = [{
    imgSrc: img1,
    title: ['xPhone'],
    text: ['Lots to love. Less to spend', 'Starting at $399']
  }, {
    imgSrc: img2,
    title: ['Tablet'],
    text: ['Just the right acount of everything.']
  }, {
    imgSrc: img3,
    title: ['Buy a Tablet or xPhone for college', 'Get airpods.'],
    text: []
  }];

  return (
    <div className='App'>
      <Carousel dots={true}>
          {
            data.map((item, index) => {
              return (
                <React.Fragment key={item.imgSrc}>
                    <div className={`desc desc_${index}`}>
                      {
                        item.title.map(titleItem => {
                          return <p className='title' key={titleItem}>{titleItem}</p>
                        })
                      }
                      {
                        item.text.map(textItem => {
                          return <p className='text' key={textItem}>{textItem}</p>
                        })
                      }
                    </div>
                    <img src={item.imgSrc} alt="" className='imgStyle' />
                </React.Fragment>
              )
            })
          }
      </Carousel>
    </div>
  );
}

export default App;
