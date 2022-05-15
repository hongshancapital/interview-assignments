import airpods from "./assets/airpods.png"
import iphone from "./assets/iphone.png"
import tablet from "./assets/tablet.png"
import { carousel } from "./type"
export const carouselList: carousel[] = [
  {
    title: "xPhone",
    textDes: "Lots to love. Less to spend. Starting at $399",
    imgSrc: iphone,
    bgColor: "#121212",
    fontColor: "#fff",
    id: "1",
  },
  {
    title: "Tablet",
    textDes: "Just the right amount of everything",
    imgSrc: tablet,
    bgColor: "#f9f9f9",
    fontColor: "#000",
    id: "2",
  },
  {
    title: "Buy a Tablet or xPhone for college. Get airPods.",
    textDes: "",
    imgSrc: airpods,
    bgColor: "#efeff1",
    fontColor: "#000",
    id: "3",
  },
]
