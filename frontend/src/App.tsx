import React from "react";
import Carousel from "./Carousel"

function App() {
  let arr = [
    {
      id: 1,
      title : 'xPhone',
      subTitleOne : "Lots to love.Less to spend.",
      subTitleTwo : "Starting at $399.",
      color : '#fff',

    },
    {
      id: 2,
      title : 'Tablet',
      subTitleOne : "Just the right amount of everything.",
      subTitleTwo : "",
      color : '#000',
    },
    {
      id: 3,
      title : 'Buy a Tablet or xPhone for college.',
      subTitleOne : "Get airPods.",
      subTitleTwo : "",
      color : '#000',
    }
  ];
  return (
      <div>
        <Carousel imgArrs={arr}/>
      </div>
  )
}

export default App;
