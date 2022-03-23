import React, { useState } from "react";
import Carousel from "../common/Carousel";
import styles from "./appleIntroduce.module.scss";

export default function AppleIntroduce() {
  const [pageWidth, setPageWidth] = useState<number>(document.body.clientWidth);
  return (
    <Carousel
      timeInterval={3000}
      pageWidth={pageWidth}
      pages={[
        <div className={styles["page-base"]}>
          <div className={styles["text-info"]}>
            <div className={styles["title"] + " fz64"}>xPhone</div>
            <div className={styles["detaile"] + " fz32"}>
              XXXXXXXXXXXXXXXXXxxdfhajddfa时间仓促
            </div>
          </div>
        </div>,
        <div className={styles["page-base"] + " " + styles["tablet"]}>
          <div className={styles["text-info"]}>
            <div className={styles["title"] + " fz64"}>Tablet</div>
            <div className={styles["detaile"] + " fz32"}>
              hajddfadbfadfabdsssff999
            </div>
          </div>
        </div>,
        <div className={styles["page-base"] + " " + styles["airpods"]}>
          <div className={styles["text-info"]}>
            <div className={styles["title"] + " fz64"}>
              hajddfadbfadfabdsssffasdfnadf adfjnasd
            </div>
          </div>
        </div>,
      ]}
    ></Carousel>
  );
}
