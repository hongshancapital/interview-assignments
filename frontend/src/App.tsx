import "./App.css";
import Carousel from "./components/Carousel";

function App() {
  return (
    <div className="App">
      <Carousel>
        <div className="Slider Slider1">1</div>
        <div className="Slider">2</div>
        <div className="Slider">3</div>
      </Carousel>
    </div>
  );
}

export default App;
