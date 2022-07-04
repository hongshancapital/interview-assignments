import React from "react";
import styles from "./index.module.sass";

type Props = {
  img: string;
  theme: string;
  background: string;

  title?: string[];
  comments?: string[];
};

export default ({
  img,
  theme,
  background,
  title = [],
  comments = [],
}: Props) => {
  return (
    <div
      style={{ background }}
      className={`flex direction-column align-center ${styles.page} ${styles[theme]}`}
    >
      {title.length > 0 &&
        title.map((title: string, idx: number) => (
          <div className={`title ${styles.title}`} key={`title-${idx}`}>
            {title}
          </div>
        ))}
      {comments.length > 0 &&
        comments.map((comment: string, idx: number) => (
          <div className={`text ${styles.text}`} key={`comment-${idx}`}>
            {comment}
          </div>
        ))}
      <div className="flex-1"></div>
      <img src={img} />
    </div>
  );
};
