import airpods from "./assets/airpods.png";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";
import { IConfig } from "./typings";

export const DURING: number = 3000;

export const CONFIG: IConfig[] = [
  {
    title: ["xPhone"],
    fontColor: "#fff",
    backgroundImage: iphone,
    content: [
      "Lots to love.Less to spend.",
      "Starting at $399.",
    ]
  },
  {
    title: ["Tablet"],
    fontColor: "#000",
    backgroundImage: tablet,
    content: [
      "Just the right amount of everything.",
    ]
  },
  {
    title: [
      "Buy a Tablet or xPhone for college.",
      "Get AirPods."
    ],
    fontColor: "#000",
    backgroundImage: airpods,
    content: []
  }
]
