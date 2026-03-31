import "./App.css";
import Carousel from "./components/carousel";

interface msgObj {
  title: string;
  msg: string;
  color: string;
  imgUrl: string;
}

const textList: msgObj[] = [
  {
    title: "xPhone",
    msg: "Lots to love.Less to spend.\n Starting at $399.",
    color: "white",
    imgUrl: require("./assets/iphone.png"),
  },
  {
    title: "Tablet",
    msg: "Just the right amount of everything.",
    color: "black",
    imgUrl: require("./assets/tablet.png"),
  },
  {
    title: "Buy a Table or xPhone for college. \n Get arPods",
    msg: "",
    color: "black",
    imgUrl: require("./assets/airpods.png"),
  },
];

function App() {
  return (
    <div className="App">
      <div style={{ width: 1200, height: 600, margin: "5% auto" }}>
        <Carousel textList={textList} />
      </div>
    </div>
  );
}

export default App;
