import './App.css';
import Carousel from './components/Carousel';
import { SLIDES } from './constants/carousel';

function App() {
  return (
    <div className="App">
      {/* write your component here */}
      <Carousel slides={SLIDES} interval={3000} />
    </div>
  );
}

export default App;
