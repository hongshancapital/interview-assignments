import { Slide } from "../types";
import iphoneImage from "../assets/iphone.png";
import airpodsImage from "../assets/airpods.png";
import tabletImage from "../assets/tablet.png";

export default function getSlideList() {
  const result: Slide[] = [
    {
      id: "xPhone",
      title: ["xPhone"],
      content: ["Lots to love. Less to spend.", "Starting at $399."],
      image: iphoneImage,
      color: "#fff",
      backgroundColor: "#111",
    },
    {
      id: "Tablet",
      title: ["Tablet"],
      content: ["Just the right amount of everything."],
      image: tabletImage,
      color: "#000",
      backgroundColor: "#fafafa",
    },
    {
      id: "airpods",
      title: ["Buy a Tablet or xPhone for college.", "Get airPods"], // demo.mov 中 airPods文案为arPods
      content: [],
      image: airpodsImage,
      color: "#000",
      backgroundColor: "#f1f1f3",
    },
  ];
  return result;
}
