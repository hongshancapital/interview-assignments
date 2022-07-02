import React from "react";
import { configs } from "./configs";
import Page from "./components/Page";
import styles from "./index.module.sass";

export default ({ current }: { current: number }): React.ReactElement => {
  const len = configs.length;

  return (
    <div
      className={`flex direction-row align-items ${styles.list}`}
      style={{
        width: `${len * 100}%`,
        transform: `translateX(${100 / len - (current * 100) / len}%)`,
      }}
    >
      {configs.map((config, idx: number) => (
        <div key={`page-${idx}`} style={{ width: `${100 / len}%` }}>
          <Page {...config} />
        </div>
      ))}
    </div>
  );
};
