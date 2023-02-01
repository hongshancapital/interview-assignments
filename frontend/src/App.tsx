import './App.css';
import CarouselModule from './modules/carousel';
import { mockData } from './mock/data'

function App() {
  return <div className='App' data-testid="app"><CarouselModule carouselData={mockData} /></div>;
}

export default App;
