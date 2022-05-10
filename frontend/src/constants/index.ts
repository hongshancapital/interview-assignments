import { CarouselIProps } from "../components/carousel/index";

import iphone from "../assets/iphone.png";
import tablet from "../assets/tablet.png";
import airpods from "../assets/airpods.png";

export const carousels: CarouselIProps[] = [
  {
    id: "carousel-001",
    src: iphone,
    title: ["xPhone"],
    desc: ["Lots to love. Less to spend.", "Starting at $399."],
    color: "#fff",
  },
  {
    id: "carousel-002",
    src: tablet,
    title: ["Tablet"],
    desc: ["Just the right amount of everything."],
  },
  {
    id: "carousel-003",
    src: airpods,
    title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
  },
];
