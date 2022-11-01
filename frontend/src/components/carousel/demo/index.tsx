import Carousel from "../index";
const imgs = [
  {
    content: 1,
    background: "red",
  },
  {
    content: 2,
    background: "yellow",
  },
  {
    content: 3,
    background: "green",
  },
];

const Demo = () => {
  return (
    <Carousel>
      {imgs.map((img, index) => {
        return (
          <div
            style={{ background: img.background, height: "200px" }}
            key={index}
          >
            {img.content}
          </div>
        );
      })}
    </Carousel>
  );
};

export default Demo;
