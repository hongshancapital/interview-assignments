import React from "react";

import styles from "./App.module.css";
import { Carousel, Board, BoardProps } from "./components";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

const borads: BoardProps[] = [
  {
    src: iphoneImg,
    contents: [
      { text: "xPhone", type: "title" },
      { text: "Lost to love. Less to spend.", type: "text" },
      { text: "Starting at $399.", type: "text" },
    ],
    className: styles.iphone,
  },
  {
    src: tabletImg,
    contents: [
      { text: "Tablet", type: "title" },
      { text: "Just the right amount of everying.", type: "text" },
    ],
    className: styles.tablet,
  },
  {
    src: airpodsImg,
    contents: [
      { text: "Bug a Tablet or xPhone for college.", type: "title" },
      { text: "Get airPods.", type: "title" },
    ],
    className: styles.airpods,
  },
];

function App() {
  const renderBoards = () => {
    return borads.map((item, index) => {
      return <Board key={index} {...item} />;
    });
  };

  return (
    <div className={styles.app}>
      <Carousel autoplay>{renderBoards()}</Carousel>
    </div>
  );
}

export default App;
