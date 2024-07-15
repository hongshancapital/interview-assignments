import './App.css';
import { data } from './data';
import Carousel from './components/Carousel';

function App() {
  return (
    <div className="App">
      <Carousel data={data} duration={3000} />
    </div>
  );
}

export default App;
