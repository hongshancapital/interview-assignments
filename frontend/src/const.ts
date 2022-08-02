import { CarouselProps } from "./components/Carousel";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import airpods from "./assets/airpods.png";

export const SLIDES: CarouselProps["items"] = [
  {
    title: "xPhone",
    subTitle: ["Lots to love. Less to spend.", "Starting at $399."],
    image: iphone,
    backColor: "#111111",
    foreColor: "white",
  },
  {
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    image: tablet,
    backColor: "#fafafa",
    foreColor: "black",
  },
  {
    title: ["Buy a Tablet or xPhone for college.", "Get arPods"],
    image: airpods,
    backColor: "#f2f2f3",
    foreColor: "black",
  },
];
