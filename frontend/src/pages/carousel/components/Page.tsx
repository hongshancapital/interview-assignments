import { DataItem } from "@/assets/data/data";
import style from "./page.module.sass";
type PageProps = DataItem;

function Page(props: PageProps) {
  return (
    <div className={style.pageContainer} style={props.bgStyle}>
      <div className={style.bgImage} style={props.imgage}></div>
      <div className={style.titleContainer}>
        {props.title.map((title) => (
          <div key={title[0]} className="titleItem" style={title[1]}>
            {title[0]}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Page;
