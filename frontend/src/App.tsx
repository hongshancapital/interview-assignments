import "./App.css";
import Carousel from "./components/carousel";
import useCarouselData from "./hooks/use-carousel-data";

function App() {
  const { data, viewRef } = useCarouselData();
  return (
    <div className="App">
      <Carousel autoplay loop>
        {data.map((item, idx) => (
          <Carousel.Item key={idx}>
            {/* 可自定义使用其他容器渲染 */}
            <Carousel.CarouselItemContent
              {...item}
              ref={viewRef}
              style={{ ...item.style, height: 600 }}
            />
          </Carousel.Item>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
