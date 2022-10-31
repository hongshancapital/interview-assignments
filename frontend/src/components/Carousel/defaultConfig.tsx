import { CarouselProps } from "./interface";
import airpodsSrc from "../../assets/airpods.png";
import iphoneSrc from "../../assets/iphone.png";
import tabletSrc from "../../assets/tablet.png";

const defaultConfig: CarouselProps = {
  items: [
    {
      src: iphoneSrc,
      title: ["xPhone"],
      desc: ["Lots to love. Less to spend.", "Starting at $399."],
      wrapperStyle: {
        color: "#fff",
        backgroundColor: "#101010",
      },
    },
    {
      src: tabletSrc,
      title: ["Tablet"],
      desc: ["Just the right amount of everything."],
      wrapperStyle: {
        backgroundColor: "#fafafa",
      },
    },
    {
      src: airpodsSrc,
      title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
      wrapperStyle: {
        backgroundColor: "#f2f2f4",
      },
    },
  ],
};

export default defaultConfig;
