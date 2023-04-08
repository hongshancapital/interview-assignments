import { useEffect, useState, useRef } from "react";

import imgIphone from "../assets/iphone.png";
import imgTablet from "../assets/tablet.png";
import imgAirpods from "../assets/airpods.png";
import {
  calcImgsRealSize,
  getImgSizeByUrl,
} from "../components/carousel/utils";
import {
  CarouselItemContentProps,
  CarouselItemContentRef,
} from "../components/carousel/carousel-item-content";
import { Size } from "../components/carousel/types/common-types";

const STATIC_DATA: CarouselItemContentProps[] = [
  {
    imgUrl: imgIphone,
    title: "xPhone",
    subTitle: "Lots to love.Less to spend.\nStarting at $399.",
    style: { color: "#FFF" },
  },
  {
    imgUrl: imgTablet,
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
  },
  {
    imgUrl: imgAirpods,
    title: "Buy a Tablet or xPhone for college.\nGet airPods",
  },
];

const useCarouselData = () => {
  const [data, setData] = useState<
    Array<CarouselItemContentProps & Partial<Size>>
  >([]);

  const viewRef = useRef<CarouselItemContentRef>(null);

  const updateDataByViewSize = () => {
    Promise.all(STATIC_DATA.map((item) => getImgSizeByUrl(item.imgUrl))).then(
      (imgsSize) => {
        if (imgsSize.length > 0 && viewRef.current) {
          const viewSize = viewRef.current.getContainerSize();
          setData(
            // 因素材图片宽高均不一致，故需根据实际视图宽高计算下图片显示实际需要缩放的尺寸
            calcImgsRealSize({ imgsSize, viewSize }).map((imgSize, idx) => ({
              ...STATIC_DATA[idx],
              imgSize,
            }))
          );
        }
      }
    );
  };

  useEffect(() => {
    setData(STATIC_DATA);
    updateDataByViewSize();
  }, []);

  return {
    data,
    viewRef,
  };
};

export default useCarouselData;
