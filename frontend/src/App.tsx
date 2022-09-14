import React from "react";
import "./App.css";
import Carousel from './components/carousel/Carousel';
import CarouselItem from "./components/carouselItem/CarouselItem";

const list = [
  {
    title: 'xPhone',
    subTitle: '',
    desc: 'Lots to love. Less to spend.',
    subDesc: 'Starting at $399.',
    image: require('./assets/iphone.png')
  },
  {
    title: 'Tablet',
    subTitle: '',
    desc: 'Just the right amount of everything',
    subDesc: '',
    image: require('./assets/tablet.png')
  },
  {
    title: 'Buy a Tablet or xPhone for college.',
    subTitle: 'Get airpods',
    desc: '',
    subDesc: '',
    image: require('./assets/airpods.png')
  },
]

function App() {

  return <div className="App">
    <Carousel interval={3000} >
      {
        list.map((item, index) => {
          return (
            <CarouselItem key={index}>
              <div
                className="infos"
                style={{
                  backgroundImage: `url(${item.image})`,
                  color: index === 0 ? 'white':'black'
                }}
              >
                <h4>{ item.title }</h4>
                <h4>{ item.subTitle }</h4>
                <p>{ item.desc }</p>
                <p>{ item.subDesc }</p>
              </div>
            </CarouselItem>
          )
        })
      }
    </Carousel>
  </div>;
}

export default App;
