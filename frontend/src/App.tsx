
import { useState } from "react";

import { CarouselItem, Carousel } from "./components/Carousel";
import Content from './components/Content'
import './App.css';

const DefaultData = [
  {
    className: 'phoneContent',
    bigtitle1: 'xPhone',
    secondryTitle1:'Lots to Love. Less to spend.',
    secondryTitle2:'Starting at $399.',
  },
  {
    className: 'tabletContent',
    bigtitle1: 'Tablet',
    secondryTitle1:'Just the right amount of everything.',
  },
  {
    className: 'tabletContent',
    bigtitle1: 'Buy a Tablet or xPhone for college.',
    bigtitle2: 'Get arPods.'
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
