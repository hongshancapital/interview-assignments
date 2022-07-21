import { DataItem } from "../data";

type PageProps = DataItem;

function Page(props: PageProps) {
  return (
    <div className="pageContainer" style={props.style}>
      <div className="titleContainer">
        {props.title.map((title) => (
          <div key={title[0]} className="titleItem" style={title[1]}>
            {title[0]}
          </div>
        ))}
      </div>
      <div className="imageArea">
        <span className="img" style={{ backgroundImage: props.asset }}></span>
      </div>
    </div>
  );
}

export default Page;
