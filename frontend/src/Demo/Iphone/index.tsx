import IphoneImg from "../../assets/iphone.png";
import classes from "./index.module.scss";

const Iphone = () => {
  return (
    <div className={classes.iphone}>
      <div>
        <div className={classes["iphone-title"]}>xPhone</div>
        <div className={classes["iphone-content"]}>
          Lots to love. Less to spend.
        </div>
        <div className={classes["iphone-content"]}>Starting at $399.</div>
      </div>
      <img src={IphoneImg} alt="iphone" />
    </div>
  );
};

export default Iphone;
