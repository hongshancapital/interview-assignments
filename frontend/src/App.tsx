import { useCallback } from "react";

import { Carousel } from "./components";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

import styles from "./App.module.scss";

const mockServerCarouselList = [
  {
    title: "xPhone",
    desc: "Lots to love. Less to spend.\nStarting at $399.",
    thumbnail: {
      url: iphone,
      width: 600,
      height: 400,
    },
    foreground: "#fff",
    background: "#101010",
  },
  {
    title: "Tablet",
    desc: "Just the right amount of everything.",
    thumbnail: {
      url: tablet,
      width: 1000,
      height: 400,
    },
    foreground: "#000",
    background: "#fafafa",
  },
  {
    title: "Buy a Tablet or xPhone for college.\nGet arPods.",
    desc: "",
    thumbnail: {
      url: airpods,
      width: 1400,
      height: 400,
    },
    foreground: "#000",
    background: "#f2f2f4",
  },
];

function App() {
  const handleCarouselChange = useCallback((currentIndex: number) => {
    console.log(`currentIndex: ${currentIndex}`);
  }, []);

  return (
    <div className={styles.carousel_container}>
      <Carousel autoplay onChange={handleCarouselChange}>
        {mockServerCarouselList.map(
          (
            {
              title,
              desc,
              thumbnail: { url, width, height },
              foreground,
              background,
            },
            index
          ) => (
            <div
              className={styles.carousel_item}
              style={{ color: foreground, backgroundColor: background }}
              key={title}
            >
              <h2 className={styles.carousel_item_title}>{title}</h2>
              {desc && <p className={styles.carousel_item_desc}>{desc}</p>}
              <div
                className={`${styles.carousel_item_background} ${
                  styles["carousel_item_background_" + index]
                }`}
                style={{ backgroundImage: `url(${url})`, width, height }}
              />
            </div>
          )
        )}
      </Carousel>
    </div>
  );
}

export default App;
