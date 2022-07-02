import React from "react";
import View from "./view";
import { configs } from "./configs";
import Footer from "./components/Footer";

export default () => {
  const [current, setCurrent] = React.useState<number>(0);

  return (
    <div className="flex direction-column align-center">
      <View />
      <Footer
        current={current}
        length={configs.length}
        setCurrent={setCurrent}
      />
    </div>
  );
};
