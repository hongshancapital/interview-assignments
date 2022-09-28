import React, { useState } from "react";
import { isEmpty } from "./tool";

const Controls = (props: any) => {
  const [isprev] = useState<boolean>(props.isprev || isEmpty(props.isprev));
  const action = (): void => {
    props.action && props.action();
  };

  return (
    <span
      className={`_direct_btn ${isprev ? "_prev" : "_next"}`}
      title={`${isprev ? "previous" : "next"}`}
      onClick={action}
    >
      <svg
        viewBox="0 0 1024 1024"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
        width="28"
        height="28"
      >
        {isprev ? (
          <path
            d="M641.28 278.613333l-45.226667-45.226666-278.634666 278.762666 278.613333 278.485334 45.248-45.269334-233.365333-233.237333z"
            p-id="5907"
            fill="#ffffff"
          ></path>
        ) : (
          <path
            d="M593.450667 512.128L360.064 278.613333l45.290667-45.226666 278.613333 278.762666L405.333333 790.613333l-45.226666-45.269333z"
            p-id="6046"
            fill="#ffffff"
          ></path>
        )}
      </svg>
    </span>
  );
};

export default Controls;
