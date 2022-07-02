import React from "react";

type Props = {
  background: string;
  img: Promise<typeof import("*.png")>;
  title?: string[];
  comments?: string[];
};

export default ({ title = [], comments = [] }: Props) => {
  return (
    <div>
      {title.length > 0 &&
        title.map((title: string, idx: number) => (
          <div className="title" key={`title-${idx}`}>
            {title}
          </div>
        ))}
      {comments.length > 0 &&
        comments.map((comment: string, idx: number) => (
          <div className="text" key={`comment-${idx}`}>
            {comment}
          </div>
        ))}
    </div>
  );
};
