import { assets } from "../assets";
import { SliceProps } from "../components/Carousel/Slice";

const CarouselData: SliceProps[] = [
  {
    title: "xPhone",
    titleStyle: { color: "#fff" },
    subtitle: "Lots to love. Less to spend.\nStarting at $399",
    subtitleStyle: { color: "#fff" },
    image: assets.iphone,
    backgroundStyle: { backgroundColor: "#000" },
  },
  {
    title: "Tablet",
    subtitle: "Just the right amount of everything.",
    image: assets.tablet,
    backgroundStyle: { backgroundColor: "#f0f0f0" },
  },
  {
    title: "Buy a Tablet or xPhone for college.\nGet arPods",
    subtitle: "",
    image: assets.airpods,
    backgroundStyle: { backgroundColor: "#fff" },
  },
];
export default CarouselData;
