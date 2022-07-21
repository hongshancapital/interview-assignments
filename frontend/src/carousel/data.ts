type TitleType = [
  string,
  {
    [x: string]: string | number;
  }
];

export interface DataItem {
  title: TitleType[];
  style: {
    background: string;
  };
  asset: string;
  id: string;
}

const phoneImg = require("../assets/iphone.png");
const tableletImg = require("../assets/tablet.png");
const airpodsImg = require("../assets/airpods.png");

const data: DataItem[] = [
  {
    title: [
      [
        "xPhone",
        {
          color: "#fff",
          fontSize: "32px",
        },
      ],
      [
        "Lots to love. Less to spend.\nStarting at $399.",
        {
          color: "#fff",
          fontSize: "16px",
        },
      ],
    ],
    style: {
      background: `url(${phoneImg}) center center / cover no-repeat`,
    },
    asset: require("../assets/iphone.png"),
    id: "iphone",
  },
  {
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
    style: {
      background: `url(${tableletImg}) center center / cover no-repeat`,
    },
    asset: require("../assets/tablet.png"),
    id: "tablet",
  },
  {
    title: [
      [
        "Buy a Tablet or xPhone for college.\nGet arPods.",
        {
          color: "#000",
          fontSize: "32px",
        },
      ],
    ],
    style: {
      background: `url(${airpodsImg}) center center / cover no-repeat`,
    },
    asset: require("../assets/airpods.png"),
    id: "airpods",
  },
];

export default data;
