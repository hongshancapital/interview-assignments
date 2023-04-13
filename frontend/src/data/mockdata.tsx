import styles from "./mockdata.module.css";
export const mockdata = [
  {
    url: require("../assets/iphone.png"),

    text: (
      <div className={styles.Content1}>
        <h1>xPhone</h1>
        <span>Lots to love. Less to spend.</span>
        <span>Starting at $399.</span>
      </div>
    ),
  },
  {
    url: require("../assets/tablet.png"),

    text: (
      <div className={styles.Content2}>
        <h1>Tablet</h1>
        <span>Just the right amount of everything.</span>
      </div>
    ),
  },
  {
    url: require("../assets/airpods.png"),

    text: (
      <div className={styles.Content3}>
        <h1>Buy a Tablet or xPhone for college.</h1>
        <h1>Get arPods.</h1>
      </div>
    ),
  },
];
