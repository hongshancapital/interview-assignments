import { useEffect, useState } from "react";
import "../App.css";
interface IMyProps {
  textList: {
    title: string;
    msg: string;
    color: string;
    imgUrl: string;
  }[];
}

const Carousel: React.FC<IMyProps> = (props: IMyProps) => {
  const [selectNum, setSelectNum] = useState(0);

  useEffect(() => {
    setTimeout(() => {
      setSelectNum(selectNum + 1);
    }, 2000);
  }, [selectNum]);

  function traverseContent_box(list: IMyProps["textList"]) {
    let res = [];
    for (let index = 0; index < list.length; index++) {
      const element = list[index];
      let offsetDistance = -((selectNum % list.length) - index) * 100;
      res.push(
        <div
          key={index}
          className="box-item"
          style={{
            color: element.color,
            left: offsetDistance + "%",
          }}
        >
          <div className="title">{element.title}</div>
          <div className="text">{element.msg}</div>
          <img src={element.imgUrl} alt="1" style={{}} />
        </div>
      );
    }
    return res;
  }

  function traverseContent_line(list: IMyProps["textList"]) {
    let res = [];
    for (let index = 0; index < list.length; index++) {
      let offsetDistance = index * 20;
      res.push(
        <div key={index}>
          <div className="line-item" style={{ left: offsetDistance + "px" }}></div>
          <div
            className={selectNum % list.length === index ? "selected" : "selected-0"}
            style={{ left: offsetDistance + "px" }}
          ></div>
        </div>
      );
    }
    return res;
  }

  return (
    <div className="box">
      {traverseContent_box(props.textList)}
      <div className="line">{traverseContent_line(props.textList)}</div>
    </div>
  );
};

export default Carousel;
