import './App.css';

import Carousel from "./components/carousel";

const App = () => {
  return <div className='App'>
    <Carousel
      timerData={3000}
      PhotosSrcData={null}
    />
  </div>;
}

export default App;
