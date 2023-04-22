import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";
import { SlideItem } from "./src/Carousel/Carousel";

export const sliderList: Array<SlideItem> = [
  {
    id: 1,
    title: "xPhone",
    desc: "Lots to love.Less to spend.\nStarting at $399.",
    image: iphoneImg,
    bgColor: "rgb(17, 17, 17)",
    color: "rgb(255, 255, 255)",
  },
  {
    id: 2,
    title: "Tablet",
    desc: "Just the right amount of everything.",
    image: tabletImg,
    bgColor: "rgb(250, 250, 250)",
    color: "rgb(3, 3, 3)",
  },
  {
    id: 3,
    title: "Buy a Tablet or a xPhone for college.",
    subTitle: "Get arPods.",
    image: airpodsImg,
    bgColor: "rgb(241, 241, 243)",
    color: "rgb(3, 3, 3)",
  },
];