import React from "react";
import classnams from "classnames";

import styles from "./App.module.css";
import Carousel from "./components/carousel";
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

function App() {
  const iphoneClassName = classnams({
    [styles.board]: true,
    [styles.iphone]: true,
  });
  const tabletClassName = classnams({
    [styles.board]: true,
    [styles.tablet]: true,
  });
  const airpodsClassName = classnams({
    [styles.board]: true,
    [styles.airpods]: true,
  });

  return (
    <div className={styles.app}>
      <Carousel autoplay>
        <div className={iphoneClassName}>
          <div className={styles.content}>
            <span className={styles.title}>xPhone</span>
            <span className={styles.text}>Lost to love. Less to spend.</span>
            <span className={styles.text}>Starting at $399.</span>
          </div>
          <img src={iphoneImg} className={styles.img} />
        </div>
        <div className={tabletClassName}>
          <div className={styles.content}>
            <span className={styles.title}>Tablet</span>
            <span className={styles.text}>
              Just the right amount of everying.
            </span>
          </div>
          <img src={tabletImg} className={styles.img} />
        </div>
        <div className={airpodsClassName}>
          <div className={styles.content}>
            <span className={styles.title}>
              Bug a Tablet or xPhone for college.
            </span>
            <span className={styles.title}>Get airPods.</span>
          </div>
          <img src={airpodsImg} className={styles.img} />
        </div>
      </Carousel>
    </div>
  );
}

export default App;
