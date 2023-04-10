import './App.css';
import Carousel from "./components/Carousel"
import images1 from "./assets/airpods.png"
import images2 from "./assets/iphone.png"
import images3 from "./assets/tablet.png"
// import Images from "./components/Images"
const App = ()=> {
  const images = [images1,images2,images3]
  console.log(images,"jjjj")
  return <div>
      {/* 手写 */}

      <Carousel images={images} width={100} height={100} interval={1700}></Carousel>


      {/* 插件 */}
      
      {/* <Images images={images}></Images> */}
  </div>
}

export default App;
