interface CssType {
  [x: string]: string | number;
}

type ItemType = [string, CssType];

export interface DataItem {
  bgStyle: CssType;
  title: ItemType[];
  imgage: CssType;
  id: string;
}

const phoneImg = require("@/assets/images/iphone.png");
const tableletImg = require("@/assets/images/tablet.png");
const airpodsImg = require("@/assets/images/airpods.png");

const data: DataItem[] = [
  {
    bgStyle: {
      background: "#111",
    },
    title: [
      [
        "xPhone",
        {
          color: "#fff",
          fontSize: "60px",
        },
      ],
      [
        "Lots to love. Less to spend.",
        {
          position: "relative",
          top: "12px",
          color: "#fff",
          fontSize: "24px",
        },
      ],
      [
        "Starting at $399.",
        {
          position: "relative",
          top: "16px",
          color: "#fff",
          fontSize: "24px",
        },
      ],
    ],
    imgage: {
      width: "912px",
      height: "562px",
      bottom: "140px",
      background: `url(${phoneImg}) center center / cover no-repeat`,
    },
    id: "iphone",
  },
  {
    bgStyle: {
      background: "#fff",
    },
    title: [
      [
        "Tablet",
        {
          color: "#fff",
          fontSize: "32px",
        },
      ],
      [
        "Just the right amount of everything",
        {
          color: "#000",
          fontSize: "32px",
        },
      ],
    ],
    imgage: {
      width: "912px",
      height: "562px",
      bottom: "140px",
      background: `url(${tableletImg}) center center / cover no-repeat`,
    },
    id: "tablet",
  },
  {
    bgStyle: {
      background: "#fff",
    },
    title: [
      [
        "Buy a Tablet or xPhone for college.\nGet arPods.",
        {
          color: "#000",
          fontSize: "32px",
        },
      ],
    ],
    imgage: {
      width: "912px",
      height: "562px",
      bottom: "140px",
      background: `url(${airpodsImg}) center center / cover no-repeat`,
    },
    id: "airpods",
  },
];

export default data;
