import "./App.css";

import Carousel from './components/Carousel';

const carouselItems = [
  {
    title: 'xPhone',
    describe: 'Lots to love. Less to spend.\n Starting at $399.',
    className: 'img carousel-iphone'
  },
  {
    title: 'Tablet',
    describe: 'Just the right amount of everything.',
    className: 'img carousel-tablet'
  },
  {
    title: 'Buy a tablet or xPhone for college.\n Get airPods',
    describe: '',
    className: 'img carousel-airpods'
  }
];

function App() {
  return (
    <div className="app">
      <Carousel
        items={carouselItems}
        duration={3000}
        speed={300}
     />
    </div>
  );
}

export default App;
