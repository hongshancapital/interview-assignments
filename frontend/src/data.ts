import { SingleImageInfo } from './type'
import iphoneImg from "./assets/iphone.png";
import tabletImg from "./assets/tablet.png";
import airpodsImg from "./assets/airpods.png";

export const imageList: SingleImageInfo[] = [
    {
        id: 1,
        title: "xPhone",
        desc: "Lots to love.Less to spend.\nStarting at $399.",
        descWidth: "400",
        src: iphoneImg,
        color: "rgb(255, 255, 255)",
      },
      {
        id: 2,
        title: "Tablet",
        desc: "Just the right amount of everything.",
        descWidth: "500",
        src: tabletImg,
        color: "rgb(3, 3, 3)",
      },
      {
        id: 3,
        title: "Buy a Tablet or a xPhone for college.",
        subTitle: "Get arPods.",
        src: airpodsImg,
        color: "rgb(3, 3, 3)",
      }
]

export const DURATION = 300;