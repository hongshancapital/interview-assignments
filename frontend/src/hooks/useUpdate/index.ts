import { useState } from "react";

const useUpdate = () => {
  const [_, setState] = useState({});
  const forceUpdate = () => setState({});
  return [forceUpdate];
};

export default useUpdate;
