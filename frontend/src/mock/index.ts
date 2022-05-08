import { DataItem } from "../types";
import iphone from "../assets/iphone.png";
import airpods from "../assets/airpods.png";
import tablet from "../assets/tablet.png";

/**
 * Crousel列表数据
 */
export const list: DataItem[] = [
  {
    id: 1,
    title: ["xPhone"],
    subTitle: ["Lots to love.Less to spend.", "Starting at$399."],
    color: "#ffffff",
    backgroundColor: "#000000",
    img: iphone,
  },
  {
    id: 2,
    title: ["Tablet"],
    subTitle: ["Just the right amount of everything."],
    color: "#000000",
    backgroundColor: "#ffffff",
    img: tablet,
  },
  {
    id: 3,
    title: ["Buy a Tablet or xPhone for college.", "Get airPods."],
    color: "#000000",
    backgroundColor: "#f2f2f4",
    img: airpods,
  },
];
