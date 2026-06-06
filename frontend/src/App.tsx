import './App.css';
import FullScreenCarousel from './components/carousel/type/fullScreen';
import { CAROUSEL_LIST_DATA } from './mock/carousel'

export default function App() {
  return <div className='App'><FullScreenCarousel carouselList={CAROUSEL_LIST_DATA} /></div>;
}