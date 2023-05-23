import "./App.css";
import Carousel from "./components/carousel/Carousel";

function App() {
  return (
    <div className="App">
      <Carousel interval={1600}>
        <div className="page">page-1</div>
        <div className="page">page-2</div>
        <div className="page">page-3</div>
        <div className="page">page-4</div>
      </Carousel>
    </div>
  );
}

export default App;
