import './App.css';
import Carousel from './components/Carousel/index';
import { sliders } from './mock/sliders';

function App() {
  return (
    <div className="App">
      {sliders?.length > 0 && <Carousel sliders={sliders} interval={3000} />}
    </div>
  );
}

export default App;
