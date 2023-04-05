
import { useState } from "react";

import { CarouselItem, Carousel } from "./components/Carousel";
import Content from './components/Content'
import './App.css';

const DefaultData = [
  {
    className: 'phoneContent',
    title: 'xPhone',
    secondryTitle:'Lots to Love. Less to spend. Starting at $399.',
  },
  {
    className: 'tabletContent',
    title: 'Tablet',
    secondryTitle:'Just the right amount of everything.',
  },
  {
    className: 'tabletContent',
    title: 'Buy a Tablet or xPhone for college. Get arPods.',
  },
]

function App() {
  const [contentItems,setContentItems] = useState(DefaultData);
  return <Carousel>
    {
      contentItems.map((item: any)=>{
        return (
          <CarouselItem><Content {...item} /></CarouselItem>
        )
      })
    }
  </Carousel>

}

export default App;
