import { ICarouselProps } from "./index";
import airpodsSrc from "../../assets/airpods.png";
import iphoneSrc from "../../assets/iphone.png";
import tabletSrc from "../../assets/tablet.png";

// 用于校准 assets 中的三张图片位置
const IMG_BASE_STYLE = {
  height: 312,
  width: 500,
};
const WRAPPER_STYLE = {
  height: 600,
  width: 1000,
};
const calcImgStyle = (widthMultiples: number = 1) => {
  return {
    ...IMG_BASE_STYLE,
    width: widthMultiples * IMG_BASE_STYLE.width,
  };
};
// 轮播图配置
const demoConfig: ICarouselProps = {
  items: [
    {
      imgProps: {
        src: iphoneSrc,
        style: calcImgStyle(),
      },
      titleProps: {
        content: ["xPhone"],
      },
      descProps: {
        content: ["Lots to love. Less to spend.", "Starting at $399."],
      },
      wrapperStyle: {
        color: "#fff",
        backgroundColor: "#101010",
      },
    },
    {
      imgProps: {
        src: tabletSrc,
        style: calcImgStyle(2),
      },
      titleProps: {
        content: ["Tablet"],
      },
      descProps: {
        content: ["Just the right amount of everything."],
      },
      wrapperStyle: {
        backgroundColor: "#fafafa",
      },
    },
    {
      imgProps: {
        src: airpodsSrc,
        style: calcImgStyle(3),
      },
      titleProps: {
        content: ["Buy a Tablet or xPhone for college.", "Get arPods."],
      },
      wrapperStyle: {
        backgroundColor: "#f2f2f4",
      },
    },
  ],
  wrapperStyle: WRAPPER_STYLE,
};

export default demoConfig;
