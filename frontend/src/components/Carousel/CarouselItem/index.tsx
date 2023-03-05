import React, { Fragment } from "react";
import { ICarouseItemProps } from "../types";
import styles from "./index.module.css";

const CarouselItem: React.FC<ICarouseItemProps> = ({ data }) => {
  return (
    <Fragment>
      <img className={styles["img-content"]} src={data.img} alt="" />
      <div
        className={styles["img-info"]}
        style={{ color: data.textColor || "#333" }}
      >
        {data.title && (
          <div className={`title ${styles["img-name"]}`}>{data.title}</div>
        )}
        {data.title && <div className="text">{data.description}</div>}
      </div>
    </Fragment>
  );
};

export default CarouselItem;
