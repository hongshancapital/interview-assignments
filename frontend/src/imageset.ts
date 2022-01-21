import img1 from "./assets/iphone.png";
import img2 from "./assets/tablet.png";
import img3 from "./assets/airpods.png";

export interface ImageSet {
  url: string;
  title?: string[];
  text?: string[];
  color?: string;
}

export function getImages(): ImageSet[] {
  return [{
    url: img1,
    title: ["xPhone"],
    text: ["Lots to love. Less to spend.", "Starting at $399."],
    color: "white"
  }, {
    url: img2,
    title: ["Tablet"],
    text: ["Just the right amount of everything."]
  }, {
    url: img3,
    title: ["Buy a Tablet or xPhone for college.", "Get arPods."]
  }];
}