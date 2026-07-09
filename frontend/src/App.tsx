import './App.css';
import Carousel from './components/carousel';
import bannerList  from  './constants/index';


function App() {
  return <div className='App'><Carousel list={bannerList}  time={3} /></div>;
}

export default App;
