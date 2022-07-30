import React, { FC, ReactElement } from "react";
import "./index.css";
import Indicator from "./indicator";

export interface IndicatorListProps_Inter {
  currentIndex?: number;
  total: number;
}

const IndicatorList: FC<IndicatorListProps_Inter> = ({
  currentIndex,
  total,
}) => {
  
  const list = [] as ReactElement[];

  for (let i = 0; i < total; i++) {
    list.push(<Indicator key={i} active={i === currentIndex} />);
  }

  return <div className="indiacator-list-control">{list}</div>;
};

export default IndicatorList;
