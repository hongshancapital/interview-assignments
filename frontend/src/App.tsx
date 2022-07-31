import style from "./app.module.sass";
import CarouselPage from "./pages/CarouselPage";

function App() {
  return (
    <div className={style.app}>
      <CarouselPage />
    </div>
  );
}

export default App;
