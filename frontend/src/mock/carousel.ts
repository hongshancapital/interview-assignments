import { IData } from "../interface";
import IPhone from "../assets/iphone.png";
import Tablet from "../assets/tablet.png";
import AirPods from "../assets/airpods.png";

// title换行用\n,js截取
export const CarouselData: IData[] = [
  {
    imgUrl: IPhone,
    color: "#FFFFFF",
    title: "XPhone",
    description: "Lots to love.Less to spend.",
    price: "Starting at $399.",
    bgColor: "#111"
  },
  {
    imgUrl: Tablet,
    color: "#000000",
    title: "Tablet",
    description: "Just the right amount of everything.",
    bgColor: "#FAFAFA"
  },
  {
    imgUrl: AirPods,
    color: "#000000",
    title: "Buy a Tablet or xPhone for college.\nGet airPods.",
    bgColor: "#F1F1F3"
  },
];
