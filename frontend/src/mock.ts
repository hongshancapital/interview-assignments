import type Carousel from "./Carousel";
import iphoneUrl from "./assets/iphone.png";
import tabletUrl from "./assets/tablet.png";
import airpodsUrl from "./assets/airpods.png";

export const itemList: React.ComponentProps<typeof Carousel>["items"] = [
  {
    titleList: ["xPhone"],
    descList: ["Lots to love. Less to spend.", "Starting at $399."],
    img: iphoneUrl,
  },
  {
    titleList: ["Tablet"],
    descList: ["Just the right amount of everything."],
    img: tabletUrl,
    bgColor: "#f9f9f9",
    color: "#000000",
  },
  {
    titleList: ["Buy a Tablet or xPhone for college.", "Get arPods."],
    img: airpodsUrl,
    bgColor: "#f0f0f2",
    color: "#000000",
  },
];
