import style from "./app.module.sass";
import Carousel from "./pages/carousel";

function App() {
  return (
    <div className={style.app}>
      <Carousel />
    </div>
  );
}

export default App;
