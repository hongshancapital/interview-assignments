import React, {
  FC,
  useState,
  useCallback,
  ChangeEventHandler,
  ChangeEvent,
  useRef,
} from "react";

export const Test: FC = () => {
  const [gender, setGender] = useState("man");
  const onChange: ChangeEventHandler<HTMLInputElement> = useCallback((e) => {
    setGender(e.target.value);
  }, []);

  const [name, setName] = useState<string>("");
  const onInputChange: ChangeEventHandler<HTMLInputElement> = useCallback(
    (e) => {
      console.log(e.target.value);
      setName(e.target.value);
    },
    []
  );

  const radioRef = useRef<HTMLDivElement>(null);
  const nameRef = useRef<HTMLDivElement>(null);

  const verifyAll = useCallback(() => {
    let res = true;
    let first: HTMLDivElement | null = null;

    const setFirst = (v: HTMLDivElement | null) => {
      if (!first) {
        first = v;
      }
      return first;
    };

    if (!gender) {
      res = false;
      first = setFirst(radioRef.current);
    }
    if (!name) {
      res = false;

      first = setFirst(nameRef.current);
    }

    first?.scrollIntoView();

    return res;
  }, [gender, name]);

  const handleSubmit = useCallback(() => {
    if (!verifyAll()) return;
    console.log("提交");
  }, [verifyAll]);

  return (
    <div>
      <div>
        <div ref={radioRef}>
          <input
            id="man"
            type="radio"
            name="gender"
            value="man"
            checked={gender === "man"}
            onChange={onChange}
          />
          <label htmlFor="man">男</label>
        </div>

        <div>
          <input
            id="female"
            type="radio"
            name="gender"
            checked={gender === "female"}
            value={"female"}
            onChange={onChange}
          />
          <label htmlFor="female">女</label>
        </div>
      </div>

      <div style={{ height: 800 }}></div>

      <div ref={nameRef}>
        <input value={name} onChange={onInputChange}></input>
      </div>

      <div style={{ height: 800 }}></div>

      <button onClick={handleSubmit}>提交</button>
    </div>
  );
};

