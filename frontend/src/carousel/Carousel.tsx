import Page from "./components/Page";
import data from "./data";
import styles from "./carousel.sass";

function Carousel() {
  return (
    <div className={styles.container}>
      {data.map((d) => (
        <Page key={d.id} {...d} />
      ))}
    </div>
  );
}

export default Carousel;
