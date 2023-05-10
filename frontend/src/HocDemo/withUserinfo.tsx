import { Component, ComponentType, FC, useEffect, useState } from 'react';

type Userinfo = {
  id: number;
  name: string;
  gender: 0 | 1;
};

const getUserinfo: (id: number) => Promise<Userinfo | null> = (id) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        id,
        name: 'mark',
        gender: 1,
      });

      // resolve(null);
    }, 1000);
  });
};

function withUserinfo<T extends { userinfo: Userinfo }>(
  Comp: ComponentType<T>,
): FC<Omit<T, 'userinfo'> & { userId: number }> {
  return (props) => {
    const { userId, ...rest } = props;
    const [userinfo, setUserinfo] = useState<Userinfo | undefined | null>(
      undefined,
    );

    useEffect(() => {
      setUserinfo(undefined);

      getUserinfo(userId).then((data) => {
        setUserinfo(data);
      });
    }, [userId]);

    if (userinfo === undefined) {
      return <div>loading</div>;
    }

    if (userinfo === null) {
      return <div>未登录</div>;
    }

    const newProps = { ...rest, userinfo };
    return <Comp {...(newProps as any as T)} />;
  };
}

type BaseProps = {
  slogan: string;
  userinfo: Userinfo;
};

const BaseFC = ({ slogan, userinfo }: BaseProps) => {
  return (
    <div>
      <div>our slogan is: {slogan}</div>
      <div>welcome {userinfo.name}</div>
    </div>
  );
};

class BaseClass extends Component<BaseProps> {
  render() {
    return (
      <div>
        <div>our slogan is: {this.props.slogan}</div>
        <div>welcome {this.props.userinfo.name}</div>
      </div>
    );
  }
}
const Enhanced1 = withUserinfo(BaseFC);
const Enhanced2 = withUserinfo(BaseClass);

const Demo = () => {
  return (
    <>
      <Enhanced1 userId={1} slogan="must be happy" />
      <Enhanced2 userId={1} slogan="must be happy" />
    </>
  );
};

export default Demo;
