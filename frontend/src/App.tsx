import Img1 from "./assets/airpods.png";
import Img2 from "./assets/iphone.png";
import Img3 from "./assets/tablet.png";
import Carousel from "./Carousel";
import "./App.css";

const App = () => {
  return (
      <div style={{width:500,height:500,margin: '100px auto'}}>
        <Carousel
          list={[
           { url:Img1,width:0},
            {url:Img2,width:0},
            {url:Img3,width:0},
            ]}
          intervalTime={2000}
        ></Carousel>
      </div>
  )
}

export default App;

