import './App.css';
import { imageList, DURATION } from './data'
import Carousel from "./components/Carousel";

function App() {
  // return <div className='App'>{/* write your component here */}</div>;
  return <div className='App'> <Carousel sliderList={imageList} duration={ DURATION } /></div>;

}

export default App;
