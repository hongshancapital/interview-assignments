import { FC } from "react";

const Loading: FC<{
  time?: number;
}> = (props) => {
  const { time = 0 } = props;

  return (
    <div
      className="load-bar"
      style={{ animationDuration: `${time / 1000}s` }}
    ></div>
  );
};

export default Loading;
