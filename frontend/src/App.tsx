import './App.css';

import { Carousel } from './component';
import tablet from './assets/tablet.png';
import iphone from './assets/iphone.png';
import airpods from './assets/airpods.png';
import { CarouselItemProps } from './component/Carousel/interface';

const items: CarouselItemProps[] = [
  {
    title: 'xPhone',
    description: 'Lots to love. Less to spend \nStarting at $399.',
    backColor: 'rgb(250, 250, 250)',
    backImage: tablet,
    backImageStyle: {
      position: 'center bottom 90px'
    },
  },
  {
    title: 'Tablet',
    description: 'Just the right amount of everything.',
    backColor: '#111',
    backImage: iphone,
    backImageStyle: {
      size: '50%',
      position: 'center bottom 90px'
    },
  },
  {
    title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
    backColor: '#F1F1F3',
    backImage: airpods,
    backImageStyle: {
      position: 'center bottom 100px'
    },
  }
];

function App() {
  return (
    <div className='App'>
      {/* write your component here */}
      <Carousel 
        items={items} 
        // interval={4000}
        // width="100%"
        // height={600}
      />
    </div>
  );
}

export default App;
