import { IItemProps } from "../components/common/PageItem/interfaces"
import airpodsImg from "../assets/airpods.png"
import iphoneImg from "../assets/iphone.png"
import tabletImg from "../assets/tablet.png"

const list: IItemProps[] = [
  {
    title: "xPhone",
    bgImgUrl: iphoneImg,
    descList: ["Lots to love. Less to spend.", "Starting at $399."],
    className: "phone",
  },
  {
    title: "Tablet",
    bgImgUrl: tabletImg,
    descList: ["Just the right amount of everything."],
    className: "tablet",
  },
  {
    title: "Buy a Tablet or xPhone for college.",
    bgImgUrl: airpodsImg,
    descList: ["Get arPods."],
    className: "airpods",
  }
]
export {
  list
}