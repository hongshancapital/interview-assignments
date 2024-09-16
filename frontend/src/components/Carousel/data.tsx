import { CarouselItem } from "./interface";
import Phone from "../../assets/iphone.png";
import Tablet from "../../assets/tablet.png";
import Airpods from "../../assets/airpods.png";

export const data: CarouselItem[] = [
  {
    id: 1,
    bgSrc: Phone,
    title: <span style={{ color: "#fff" }}>xPhone</span>,
    description: (
      <span style={{ color: "#fff" }}>
        Lots to love.Less to spend.<br />Starting at $399.
      </span>
    ),
  },
  {
    id: 2,
    bgSrc: Tablet,
    title: "Tablet",
    description: `Just the right amount of everything.`,
  },
  {
    id: 3,
    bgSrc: Airpods,
    title: "Buy a Tablet or XPhone for college.\nGet arPods.",
  },
];
