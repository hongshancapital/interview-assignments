import "./App.css";
import Carousel from "./Carousel";
import { sliderList } from "./slideData";

function App() {
  return (
    <div className="App" data-testid="app">
      <Carousel slides={sliderList} />
    </div>
  );
}

export default App;
