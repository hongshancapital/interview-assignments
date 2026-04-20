import './App.css';

import { Carousel } from 'components/carousel';
import list from 'components/carousel/mock-list';

function App() {
  return (
    <div className="App">
      <Carousel list={list} />
    </div>
  );
}

export default App;
