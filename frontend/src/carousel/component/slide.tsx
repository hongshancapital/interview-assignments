import styles from "../style.module.scss";
import { SlideProps } from "../type";
import { memo } from "react";

export const Slide = memo(function Slide({
  icon,
  title,
  description,
  backgroundColor,
  textColor,
}: Omit<SlideProps, "id">) {
  return (
    <div
      className={styles["carousel-slide-container"]}
      style={{ backgroundColor }}
    >
      <div
        className={styles["carousel-slide-title"]}
        style={{ color: textColor }}
      >
        {title?.map((t, i) => (
          <h2 key={i}>{t}</h2>
        ))}
      </div>

      <div
        className={styles["carousel-slide-desc"]}
        style={{ color: textColor }}
      >
        {description?.map((d, i) => (
          <p key={i}>{d}</p>
        ))}
      </div>

      <div className={styles["carousel-slide-icon"]}>
        {icon && <img src={icon} alt="icon" />}
      </div>
    </div>
  );
});
