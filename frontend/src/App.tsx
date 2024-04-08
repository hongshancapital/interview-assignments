import './App.css';
import CarouselModule from './views/carousel';
import { CAROUSEL_MOCK_DATA } from './mock/data'

function App() {
  return <div className='App' data-testid="app"><CarouselModule carouselData={CAROUSEL_MOCK_DATA} /></div>;
}

export default App;
