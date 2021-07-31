// the commodity slice props
interface CommoditySliceProps {
  // the title text (can be multi-line)
  title: string | string[];
  // the description text (can be multi-line)
  desc?: string | string[];
  // the commodity image
  pic?: string;
  // the class name of the slice (use to overwrite default styles)
  className?: string;
  // the class name of the slice indicator (use to overwrite default styles)
  indicatorClassName?: string;
}
