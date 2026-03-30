import TabletImg from "../../assets/tablet.png";
import classes from "./index.module.scss";

const Tablet = () => {
  return (
    <div className={classes.tablet}>
      <div>
        <div className={classes["tablet-title"]}>Tablet</div>
        <div className={classes["tablet-content"]}>
          Just the right amount of everything.
        </div>
      </div>
      <img src={TabletImg} alt="tablet" />
    </div>
  );
};

export default Tablet;
