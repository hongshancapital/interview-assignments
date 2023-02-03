import './App.css';
import Carousel from "./components/Carousel";
import { IImage } from "./components/interface";

function App() {
  const imgLists: IImage[] = [
    { name: 'airpods', src: require('./assets/airpods.png') },
    { name: 'iphone', src:  require('./assets/iphone.png') },
    { name: 'tablet', src:  require('./assets/tablet.png') },
  ]

  return <div className='App'>
    <Carousel lists={imgLists} />
  </div>;
}

export default App;
