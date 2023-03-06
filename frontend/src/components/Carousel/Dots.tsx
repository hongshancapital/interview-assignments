import "./index.css";

interface DotsProps {
  count?: number;
  selectIndex?: number;
  deply?: number;
}

/**
 * 轮播dots组件
 * @param param0
 * @returns
 */
const Dots: React.FC<DotsProps> = ({
  count,
  selectIndex,
  deply,
}: DotsProps) => {
  return (
    <div className="dots">
      {new Array(count).fill(0).map((_value, _index) => {
        return (
          <div key={_index} className="dot">
            <div
              className={
                _index === selectIndex ||
                (selectIndex === count && _index === 0)
                  ? "active"
                  : ""
              }
              style={{
                animation:
                  _index === selectIndex ||
                  (selectIndex === count && _index === 0)
                    ? `progress ${deply}ms linear`
                    : "",
              }}
            ></div>
          </div>
        );
      })}
    </div>
  );
};

export default Dots;
