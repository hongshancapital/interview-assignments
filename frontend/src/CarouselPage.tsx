import Carousel from "./carousel/Carousel";
import data from "./assets/data";
import { useEffect, useState } from "react";
import { CAROUSEL_TIME } from "./constants/const";

export default function CarouselPage() {
  function resizeWidth() {
    setWidth(document.body.clientWidth);
  }

  const [width, setWidth] = useState(document.body.clientWidth);

  useEffect(() => {
    window.addEventListener("resize", resizeWidth);
    return () => {
      window.removeEventListener("resize", resizeWidth);
    };
  }, []);

  return <Carousel datasource={data} width={width} interval={CAROUSEL_TIME} />;
}
