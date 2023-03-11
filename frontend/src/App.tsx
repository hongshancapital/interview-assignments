import React, { useEffect, useState } from "react";
import "./App.css";

export const data = [
  {
    title: "xPhone",
    color: "#fff",
    desc: "Lots to love. Less to spend.\nStarting at $399",
    image: require("./assets/iphone.png"),
  },
  {
    title: "Tablet",
    desc: "Just the right amount of everything.",
    image: require("./assets/tablet.png"),
  },
  {
    title: "Buy a Tablet or XPhone for college.\nGet arPods",
    image: require("./assets/airpods.png"),
  },
];

type CarouselItemType = { children: React.ReactNode };
export function CarouselItem(props: CarouselItemType) {
  const { children } = props;

  return <div className="carouselItem">{children}</div>;
}

type carouselPaginationItemType = {
  show: boolean;
  onFinished: () => void;
};
export function CarouselPaginationItem({
  show,
  onFinished,
}: carouselPaginationItemType) {
  const [display, setDisplay] = useState(false);

  useEffect(() => {
    setDisplay(show);
  }, [show]);

  return (
    <div className="carouselPaginationItem">
      <div
        className={
          `carouselPaginationItemAnimation` + (display ? " running" : "")
        }
        onTransitionEnd={onFinished}
      />
    </div>
  );
}

type CarouselPaginationType = {
  currentIndex: number;
  total: number;
  onFinished: () => void;
};
export function CarouselPagination(props: CarouselPaginationType) {
  const { currentIndex, total, onFinished } = props;

  return (
    <div className="carouselPagination">
      {new Array(total).fill(1).map((v, index) => {
        return (
          <CarouselPaginationItem
            key={index}
            show={currentIndex === index}
            onFinished={onFinished}
          />
        );
      })}
    </div>
  );
}

type CarouselType = {
  children: React.ReactNode;
};

export function Carousel({ children }: CarouselType) {
  const [index, setIndex] = useState(0);
  const total = React.Children.toArray(children).length;
  const onFinished = () => {
    setIndex(index === total - 1 ? 0 : index + 1);
  };

  return (
    <div className="carousel">
      <div
        className="carouselItemWrapper"
        style={{
          transform: `translateX(-${index * 100}%)`,
        }}
      >
        {children}
      </div>
      <CarouselPagination
        total={total}
        currentIndex={index}
        onFinished={onFinished}
      />
    </div>
  );
}
function App() {
  return (
    <div className="App">
      <Carousel>
        {data.map(({ title, desc, image, color }) => {
          return (
            <CarouselItem key={title}>
              <div
                className="carouselItemInternalWrapper"
                style={{
                  backgroundImage: `url(${image})`,
                  ...(color ? { color } : {}),
                }}
              >
                <div className="carouselPaginationItemTitle">{title}</div>
                {desc && <div>{desc}</div>}
              </div>
            </CarouselItem>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
