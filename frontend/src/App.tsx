import "./App.css";
import Carousel from "./components/Carousel";
import Airpods from "./Demo/Airpods";
import Iphone from "./Demo/Iphone";
import Tablet from "./Demo/Tablet";

function App() {
  return (
    <div className="App">
      <Carousel>
        {/** 使用ReactNode作为轮播每一页，保证扩展性与灵活性 */}
        <Iphone />
        <Tablet />
        <Airpods />
      </Carousel>
    </div>
  );
}

export default App;
