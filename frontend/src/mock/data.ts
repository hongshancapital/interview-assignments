import Iphone from "../assets/iphone.png";
import Tablet from "../assets/tablet.png";
import Airpods from "../assets/airpods.png";
import { ICarouselItem } from "../types/carousel";

export const CAROUSEL_MOCK_DATA: ICarouselItem[] = [
  {
    id: 0,
    title: "xPhone",
    desc: "Lots to love.Less to spend. \n Starting at $399.",
    fontColor: "#fff",
    bgColor: "#101010",
    bgImg: Iphone,
  },
  {
    id: 1,
    title: "Tablet",
    desc: "Just the right amount of everything.",
    fontColor: "#000",
    bgColor: "#fafafa",
    bgImg: Tablet,
  },
  {
    id: 2,
    title: "Buy a Tablet or a xPhone for college.\nGet arPods.",
    fontColor: "#000",
    bgColor: "#f2f2f4",
    bgImg: Airpods,
  }
];
