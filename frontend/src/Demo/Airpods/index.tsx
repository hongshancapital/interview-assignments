import AirpodsImg from "../../assets/airpods.png";
import classes from "./index.module.scss";

const Airpods = () => {
  return (
    <div className={classes.airpods}>
      <div>
        <div>Buy a Tablet or xPhone for college.</div>
        <div>Get arPods</div>
      </div>
      <img src={AirpodsImg} alt="airpods" />
    </div>
  );
};

export default Airpods;
