import Iphone from "../assets/iphone.png";
import Tablet from "../assets/tablet.png";
import Airpods from "../assets/airpods.png";
import { ICarouselItem } from "../types/carousel";

export const mockData: ICarouselItem[] = [
  {
    id: 0,
    title: "xPhone",
    desc: "Lots to love.Less to spend. \n Starting at $399.",
    fontColor: "rgb(255, 255, 255)",
    bgColor: "rgb(17, 17, 17)",
    bgImg: Iphone,
  },
  {
    id: 1,
    title: "Tablet",
    desc: "Just the right amount of everything.",
    fontColor: "rgb(3, 3, 3)",
    bgColor: "rgb(250, 250, 250)",
    bgImg: Tablet,
  },
  {
    id: 2,
    title: "Buy a Tablet or a xPhone for college.\nGet arPods.",
    fontColor: "rgb(3, 3, 3)",
    bgColor: "rgb(241, 241, 243)",
    bgImg: Airpods,
  }
];
