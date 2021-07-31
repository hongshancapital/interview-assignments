import React from "react";
import classNames from "classnames";
import "./index.scss";

/**
 * The commodity carousel slice
 * @param {CommoditySliceProps} props {
 *   title: "",  // the title text (can be multi-line {string[]})
 *   desc: "",  // the description text (can be multi-line {string[]})
 *   pic: "",  // the commodity image
 *   className: "",  // the class name of the slice (use to overwrite default styles)
 * }
 */
function CommoditySlice(props: CommoditySliceProps): JSX.Element {
  const {
    title,
    desc,
    pic,
    className: customClassName,
  } = props;
  return (
    <div className={classNames("commodity", customClassName)}>
      <div className="commodity-blank-top" />
      <div className="commodity-content">
        {renderCommodityTitle(title)}
        {renderCommodityDesc(desc)}
      </div>
      <div className="commodity-detail">
        {renderCommodityDetail(title, pic)}
      </div>
      <div className="commodity-blank-bottom" />
    </div>
  );
}

/**
 * render the commodity title
 * @param {string | string[]} title  the title text
 * @returns {Element} the title elements
 */
function renderCommodityTitle(title: string | string[]): JSX.Element {
  const titles = Array.isArray(title) ? title : [title];
  return (
    <div className="commodity-titles">
      {titles.map((titleItem, titleItemIndex) => {
        if (!titleItem) {
          return null;
        }
        return (
          <div key={`commodityTitle${titleItemIndex}`}>{titleItem}</div>
        );
      })}
    </div>
  );
}

/**
 * render the commodity description
 * @param {string | string[]} desc  the description text
 * @returns {Element | null} the description elements
 */
function renderCommodityDesc(desc?: string | string[]): JSX.Element | null {
  if (!desc || !desc.length) {
    return null;
  }
  const descList = Array.isArray(desc) ? desc : [desc];
  return (
    <div className="commodity-descs">
      {descList.map((descItem, descItemIndex) => {
        if (!descItem) {
          return null;
        }
        return (
          <div className="commodity-desc" key={`commodityDesc${descItemIndex}`}>{descItem}</div>
        );
      })}
    </div>
  );
}

/**
 * render the commodity detail infomation
 * @param {string | string[]} label  the image label
 * @param {string} [pic]  the commodity image
 * @returns {Element | null} the detail elements
 */
function renderCommodityDetail(label: string | string[], pic?: string): JSX.Element | null {
  if (!pic) {
    return null;
  }
  return (
    <img
      className="commodity-pic"
      src={pic}
      alt={label.toString()}
    />
  );
}

export default CommoditySlice;
