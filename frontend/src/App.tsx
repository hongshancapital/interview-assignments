import Carousel from "./components/Carousel";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Carousel data={[1, 2, 3]} customStyle={{ width: "200px" }}>
        {(item) => <div className="carousel-item">{item}</div>}
      </Carousel>
    </div>
  );
}

export default App;
