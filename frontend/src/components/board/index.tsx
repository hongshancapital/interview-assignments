import React, { FC } from "react";
import classnames from "classnames";

import styles from "./index.module.css";

interface Text {
  text: string;
  type: "title" | "text";
}

export interface BoardProps {
  src: string;
  contents: Text[];
  className?: string;
}

const Board: FC<BoardProps> = (props) => {
  const { src, contents, className } = props;

  const boardClassName = classnames({
    [styles.board]: true,
    [className || ""]: !!className,
  });

  const renderContents = () => {
    return contents.map((item, index) => {
      return (
        <span key={index} className={styles[item.type]}>
          {item.text}
        </span>
      );
    });
  };

  return (
    <div className={boardClassName}>
      <div className={styles.content}>{renderContents()}</div>
      <img src={src} className={styles.img} />
    </div>
  );
};

export default Board;
