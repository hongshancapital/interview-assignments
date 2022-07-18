import React from "react";
import "./App.css";
import Carousel,{ CarouselItem } from './components/Carousel'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

const data = [
{
  id:1,
  title:'xPhont',
  tips:'Lots to love.Less to spend.Starting at $399',
  img:iphone
},
{
  id:2,
  title:'Tablet',
  tips:'Just the right amount of everything',
  img:tablet
},
{
  id:3,
  title:'Buy a Tabelt or xPhone for college.',
  tips:'Get arPods.',
  img:airpods
}
]

function App() {
  return <Carousel tim={4000}>
     {
       data ?.map((item,index) => {
         return (
           <CarouselItem 
             id={index}
             key={item.id}
             title={item.title}
             tips={item.tips}
             img={item.img}
             ></CarouselItem>
         )
       })
     }
  </Carousel>;
}

export default App;
