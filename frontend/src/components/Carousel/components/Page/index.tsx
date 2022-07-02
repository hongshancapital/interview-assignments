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
    <div className={`${styles.page} ${styles[theme]}`} style={{ background }}>
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
      <img src={img} />
    </div>
  );
};
