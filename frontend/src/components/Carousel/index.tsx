import React from "react";
import View from "./view";
import Footer from "./components/Footer";
import styles from "./index.module.sass";

export default () => {
  const [current, setCurrent] = React.useState<number>(0);

  return (
    <div className={`flex direction-column align-center ${styles.window}`}>
      <View current={current} />
      <Footer current={current} setCurrent={setCurrent} />
    </div>
  );
};
