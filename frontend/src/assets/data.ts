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

const phoneImg = require("./iphone.png");
const tableletImg = require("./tablet.png");
const airpodsImg = require("./airpods.png");

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
          fontSize: "72px",
          fontWeight: "bold",
        },
      ],
      [
        "Lots to love. Less to spend.",
        {
          position: "relative",
          top: "12px",
          color: "#fff",
          fontSize: "32px",
        },
      ],
      [
        "Starting at $399.",
        {
          position: "relative",
          top: "16px",
          color: "#fff",
          fontSize: "32px",
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
      background: "#fafafa",
    },
    title: [
      [
        "Tablet",
        {
          color: "#000",
          fontSize: "60px",
          fontWeight: "bold",
        },
      ],
      [
        "Just the right amount of everything.",
        {
          position: "relative",
          top: "12px",
          color: "#000",
          fontSize: "24px",
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
      background: "#f1f1f3",
    },
    title: [
      [
        "Buy a Tablet or xPhone for college.",
        {
          color: "#000",
          fontSize: "60px",
          fontWeight: "bold",
        },
      ],
      [
        "Get airPods.",
        {
          color: "#000",
          fontSize: "60px",
          fontWeight: "bold",
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
