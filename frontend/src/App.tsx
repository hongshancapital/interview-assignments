import Carousel from "./components/Carousel";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";
import "./App.css";

function App() {
  const banners: carousel.CarouselItem[] = [
    {
      title: ["xPhone"],
      desc: ["Lots to love.Less to spend.", "Starting at $399."],
      imgUrl: iphone,
      color: "#fff",
      bgColor: "#111",
    },
    {
      title: ["Tablet"],
      desc: ["Just the right amount of everything."],
      imgUrl: tablet,
      bgColor: "#fafafa",
    },
    {
      title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
      imgUrl: airpods,
      bgColor: "#f2f2f3",
    },
  ];

  
  return <Carousel className="carousel-root" list= {banners}/>
}

export default App;
