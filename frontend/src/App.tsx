import './App.css';
import Carousel from './component/Carousel';
import { mockdata } from './data/mockdata';

function App() {


  return <div className='App'>  <Carousel images={mockdata} interval={3000} /></div>;
}

export default App;
