import './App.css';
import React from 'react';
import MyCarousel from './component/Carousel/index'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

function App() {
  const arr1 = [
    { title: 'xPhone',desc:'Lots to love. Less to spend.',prices:'Starting at $399.',style:{ color:'white',backgroundColor:'#101010'}, url: `url(${iphone})` },
    { title: 'Tablet',desc:'Just the right amount of everything.',prices:'',style:{color:'black'}, url: `url(${tablet})` }, 
    { title: 'Buy a Tablet or xPhone for college.Get arPods.',desc:'',prices:'',style:{color:'black'}, url: `url(${airpods})` }
  ]
  return <div className='App'><MyCarousel arr={arr1} /></div>;
}

export default React.memo(App);
