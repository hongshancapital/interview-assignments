import { ICarouselItem } from "../type/carousel";
import airpodsSrc from "../assets/airpods.png";
import iphoneSrc from "../assets/iphone.png";
import tabletSrc from "../assets/tablet.png";

// 轮播图配置
export const mockData: ICarouselItem[] = [
  {
    id: 0,
    src: iphoneSrc,
    title: ["xPhone"],
    desc: ["Lots to love. Less to spend.", "Starting at $399."],
    style: {
      color: "#fff",
      backgroundColor: "#101010",
    },
  },
  {
    id:1,
    src: tabletSrc,
    title: ["Tablet"],
    desc: ["Just the right amount of everything."],
    style: {
      backgroundColor: "#fafafa",
    },
  },
  {
    id:2,
    src: airpodsSrc,
    title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
    style: {
      backgroundColor: "#f2f2f4",
    },
  },
];
