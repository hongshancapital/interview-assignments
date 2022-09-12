import React from 'react';
import Carousel from './Carousel';
import Card, { CardProps } from './Card';
import './App.css';
import './Card/index.css'

const cardsData : CardProps[] = [
  {
    theKey: 'iphone',
    imgUrl: require('./assets/iphone.png'),
    titles:[
        'xPhone',
    ],
    details:[
      'Lots to love. Less to spend.',
      'Starting at $399'
    ],
    styles:{
      fontColor: '#FFFFFF',
      bgColor: '#101010'
    } 
  },
  {
    theKey: 'tablet',
    imgUrl: require('./assets/tablet.png'),
    titles:[
      'Tablet',
    ],
    details:[
      'Just the right amount of everything.'
    ],
    styles:{
        fontColor: '#000000',
        bgColor: '#FAFAFA'
      }
  },
  {
    theKey: 'airPods',
    imgUrl: require('./assets/airpods.png'),
    titles:[
      'Buy a Tablet or xPhone for college.',
      'Get arPods.'
    ],
    details:[
    ],
    styles:{
      fontColor:'#000000',
      bgColor: '#F2F2F4'
    }
  }
]

function App() {
  return <div className="App">
    <Carousel stayDuration={2000} switchDuration={300}>
      {cardsData.map((v)=>{
        return <Card key={v.theKey} theKey={v.theKey} imgUrl={v.imgUrl} titles={v.titles} details={v.details} styles={v.styles}/>
      })}
    </Carousel>
  </div>;
}

export default App;
