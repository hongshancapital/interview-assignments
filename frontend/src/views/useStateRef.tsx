import React, { useEffect, useRef, useState } from "react";

export default function useStateRef(initialValue: any): Array<any> {
  const [value, setValue] = useState(initialValue);
  const ref = useRef(value);
  useEffect(() => {
    ref.current = value;
  }, [value]);
  return [value, setValue, ref];
}
