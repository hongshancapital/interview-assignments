import React from "react";
import "./App.css";
import { Carousel } from "./component/Carousel/Carousel";
import { MyBullets } from "./component/MyBullets";

const Info = ({children} : {children : string}) => {
  return <h3 style={{
    color : 'blue'
  }}>{children}</h3>
}
function App() {
  return (
    <div className="App">
      <Info>
        （与视频相同
        ）：滑到最右边，然后滑动到最左边
      </Info>
      <Carousel
        style={{
          height: "190px",
        }}
        duration={1500}
        wait={2000}
        dir="right"
        mode="one-side"
        addons = {<MyBullets />}
      >
        <div
          style={{
            backgroundColor: "black",
            color: "white",
          }}
        >
          <h1>XPhone</h1>
          <h2>Lots to love, less to spend</h2>
          <h2>Starting at $399</h2>
        </div>
        <div>
          <h1>Tablet</h1>
          <h2>Just the right amount of everything.</h2>
        </div>
        <div
          style={{
            backgroundColor: "palevioletred",
          }}
        >
          <h1>WonderFul</h1>
          <h2>This is a Test Page</h2>
        </div>
        <div>
          <h1>Buy a Tablet or xPhone for college.</h1>
          <h1>Get arPods</h1>
        </div>
      </Carousel>

      <Info>
        （与视频相同方向相反
        ）：滑到最右边，然后滑动到最左边
      </Info>
      <Carousel
        style={{
          height: "190px",
        }}
        duration={800}
        wait={2500}
        dir="left"
        mode="one-side"
        addons = {<MyBullets />}
      >
        <div
          style={{
            backgroundColor: "black",
            color: "white",
          }}
        >
          <h1>XPhone</h1>
          <h2>Lots to love, less to spend</h2>
          <h2>Starting at $399</h2>
        </div>
        <div>
          <h1>Tablet</h1>
          <h2>Just the right amount of everything.</h2>
        </div>
        <div
          style={{
            backgroundColor: "palevioletred",
          }}
        >
          <h1>WonderFul</h1>
          <h2>This is a Test Page</h2>
        </div>
        <div>
          <h1>Buy a Tablet or xPhone for college.</h1>
          <h1>Get arPods</h1>
        </div>
      </Carousel>

      <Info>单向无限循环（的轮播图）</Info>
      <Carousel
        mode="loop"
        duration={300}
        wait={1000}
        style={{
          height: "190px",
        }}
        addons = {<MyBullets />}
      >
        <div
          style={{
            backgroundColor: "black",
            color: "white",
          }}
        >
          <h1>XPhone</h1>
          <h2>Lots to love, less to spend</h2>
          <h2>Starting at $399</h2>
        </div>
        <div>
          <h1>Tablet</h1>
          <h2>Just the right amount of everything.</h2>
        </div>
        <div>
          <h1>Buy a Tablet or xPhone for college.</h1>
          <h1>Get arPods</h1>
        </div>
      </Carousel> 


      <Info>单向无限循环反方向（的轮播图）</Info>
      <Carousel
        mode="loop"
        duration={1000}
        wait={1000}
        style={{
          height: "190px",
        }}
        dir="right"
        addons = {<MyBullets />}
      >
        <div
          style={{
            backgroundColor: "black",
            color: "white",
          }}
        >
          <h1>XPhone</h1>
          <h2>Lots to love, less to spend</h2>
          <h2>Starting at $399</h2>
        </div>
        <div>
          <h1>Tablet</h1>
          <h2>Just the right amount of everything.</h2>
        </div>
        <div>
          <h1>Buy a Tablet or xPhone for college.</h1>
          <h1>Get arPods</h1>
        </div>
      </Carousel> 
    </div>
  )
}


export default App;
