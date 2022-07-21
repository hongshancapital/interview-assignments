type TitleType = [
  string,
  {
    [x: string]: string | number;
  }
];

export interface DataItem {
  title: TitleType[];
  style: {
    backgroundColor: string;
  };
  asset: string;
  id: string;
}

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
      backgroundColor: "#111",
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
      backgroundColor: "#fff",
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
      backgroundColor: "#fff",
    },
    asset: require("../assets/airpods.png"),
    id: "airpods",
  },
];

export default data;
