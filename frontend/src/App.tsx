import './App.css';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import Carousel from './components/Carousel/Carousel';

function App() {
  const carouselList = [
      { title: ['xPhone'], description: ['Lots to love. Less to spend.', 'Starting at $399.'], imageURL: iphone },
      { title: ['Tablet'], description: ['Just the right amount of evething.'], imageURL: tablet },
      { title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'], description: [], imageURL: airpods }
  ];
  return <div className='App'><Carousel carouselList={ carouselList }></Carousel></div>;
}

export default App;
