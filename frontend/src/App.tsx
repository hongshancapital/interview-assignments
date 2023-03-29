import "./App.css";
import { Carousel, data } from "./components/Carousel";

function App() {
  return (
    <div className="App">
      <Carousel data={data} />
    </div>
  );
}

export default App;
