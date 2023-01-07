import "./App.css";
import Carousel from "./Carousel";
import { itemList } from "./mock";

function App() {
  return (
    <div className="App">
      <Carousel items={itemList} />
    </div>
  );
}

export default App;
