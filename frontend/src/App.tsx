import React from "react";
import Carousel, { CarouselItem } from "./components/carousel";
import "./App.css";

const imgList = [
  {
    title:'xPhone',
    content:"Lost to Love.Less to spend. Staring at $399",
    image: "./assets/iphone.png",
    backgroundColor:"#111111",
  },
  {
    title:'Tablet',
    content:"Just the right amount of everything",
    image: "./assets/tablet.png",
    backgroundColor:"#fafafa",
  },
  {
    title:'Buy a Tablet of Xphone for colleage.Get airPods.',
    image: "./assets/airpods.png",
    backgroundColor:"#f1f1f3",
  },
];

function App() {
  return (
    <div className="App">
       {/* <img src={require("./assets/airpods.png")}></img> */}
      {/* write your component here */}
      <Carousel 
        duringTime={3000}
        width="100vw"
        height="100vh"
      >
        {
          imgList.map((item,index)=>{
            console.log(item);
            
            return (
              <CarouselItem 
                key={index}
              >
                {/* TODO:可以根据实际需求将这里封装成一个类似CarouselInfo组件，但是这里考虑到轮播内容可以定制故未将其封装 */}
                <div className="info" style={{backgroundColor:`${item.backgroundColor}`}}>
                  <p className={`info_title ${index==0?"title_first":""}`}>{item.title}</p>
                  <p className={`info_content ${index==0?"content_first":""}`}>{item.content}</p>
                  <img src={require(`${item.image}`)} alt="" className={`info_img ${index==0?"img_first":""}`} />

                </div>

                {/* <img src={require("./assets/airpods.png")}></img> */}
              </CarouselItem>
            )
          })
        }
      </Carousel>
    </div>
  );
}

export default App;
