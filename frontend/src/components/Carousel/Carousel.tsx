import React, {
  FC,
  useEffect,
  useState,
  ReactElement,
  CSSProperties,
} from "react";
import styles from "./index.module.css";

export interface ProgressColor {
  outerColor: string;
  innerColor: string;
}

export interface IProps {
  children: ReactElement[];
  timeout?: number;
}

export interface ItemProps {
  children: ReactElement;
  style?: CSSProperties;
}

export interface InfoProps {
  title: string;
  description: string;
  imageUrl: string;
  color: string;
  backgroundColor: string;
}

export const Item: FC<ItemProps> = (props) => {
  const { children, style } = props;
  return (
    <div className={styles.carousel_item} style={{ ...(style || {}) }}>
      {children}
    </div>
  );
};

export const Info: FC<InfoProps> = (props) => {
  const { title, description, imageUrl, backgroundColor, color } = props;
  return (
    <div
      className={styles.info_container}
      style={{
        backgroundColor,
        color,
      }}
    >
      <div className={styles.info_text}>
        <h1>{title}</h1>
        <h4>{description}</h4>
      </div>
      <img className={styles.info_img} src={imageUrl} alt="image" />
    </div>
  );
};

const Carousel: FC<IProps> = (props) => {
  const { children, timeout = 3 } = props;
  const [currentIndex, setCurrentIndex] = useState<number>(0);

  const updateIndex = (nextIndex: number) => {
    if (nextIndex >= React.Children.count(children)) {
      nextIndex = 0;
    }
    setCurrentIndex(nextIndex);
  };

  const clickDot = (index: number) => () => {
    updateIndex(index);
    document.getAnimations().forEach((animation) => {
      animation.cancel();
      animation.play();
    });
  };

  useEffect(() => {
    const timerId = setTimeout(() => {
      updateIndex(currentIndex + 1);
    }, timeout * 1000);
    return () => {
      clearTimeout(timerId);
    };
  }, [timeout, currentIndex]);

  return (
    <div className={styles.container}>
      <div
        className={styles.inner}
        style={{
          transform: `translateX(-${currentIndex * 100}%)`,
        }}
      >
        {React.Children.map(children, (item) => React.cloneElement(item))}
      </div>
      <div className={styles.dots_container}>
        {React.Children.map(children, (item, index) => {
          return (
            <div
              key={index}
              className={styles.dots_outer}
              onClick={clickDot(index)}
            >
              <div
                className={styles.dots_inner}
                style={{
                  backgroundColor: index === currentIndex ? "#fff" : "",
                  animationDuration:
                    index === currentIndex ? `${timeout}s` : "0s",
                }}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
