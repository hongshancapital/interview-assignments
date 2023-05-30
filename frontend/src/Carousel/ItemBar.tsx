interface Props {
  active: boolean;
}

export default function Index({ active }: Props) {
  return (
    <span className={"item-bar"}>
      {<div className={active ? "item-bar-inner" : ""}></div>}
    </span>
  );
}
