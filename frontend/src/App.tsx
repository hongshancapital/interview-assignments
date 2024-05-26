import './App.css';
import Carousel, { CarouselItem, CarouselCard,CardInfo } from "./Carousel";
import rabbit1 from "./assets/rabbit1.jpg"
import rabbit2 from "./assets/rabbit2.jpeg"
import rabbit3 from "./assets/rabbit3.jpeg"
const cardList:Array<CardInfo>= [
  {
    title:"Xphopne",
    content:"Lots to love. Let's to spend. Staring at $399",
    img:rabbit1
  },
   {
    title:"Tablet",
    content:"Just the right amount of everything.",
    img:rabbit2
  },
   {
    title:"",
    content:"Buy a Tablet or Xphone for college Get Airpods",
    img:rabbit3
  }
]
function App() {
  return (
  <div className='App'>
    <Carousel>
      {cardList.map((info,index)=>{
        return <CarouselItem key={index}>
          <CarouselCard {...info}/>
        </CarouselItem>
      })}
    </Carousel>
    </div>);
}

export default App;
