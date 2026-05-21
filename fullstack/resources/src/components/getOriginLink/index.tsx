import React, { useState } from 'react';
import { Form, Input, Button, Alert, Row, Col, message } from 'antd';
import { useRequest } from 'ahooks';
import { http } from '@utils';

import './index.css';

function fetchData(link: string): Promise<any> {
  return http.get(`/api/getOriginLink?key=${link}`);
}

const GetOriginLink = () => {
  const [originLink, setOriginLink] = useState('');
  const { loading, run } = useRequest(fetchData, {
    manual: true,
    throttleWait: 1000,
    onSuccess: (res, params) => {
      if (res.status === 200 && res.data.code === 0) {
        setOriginLink(res.data.data);
      } else {
        message.error(`${res.data.message}(${res.data.code})`);
      }
    },
    onError: (error: Error) => {
      message.error(error.message);
      console.log('server error:', error.stack);
    }
  });

  const onFinish = async (values: any) => {
    run(values.shortLink);
  };

  return (
    <div className="component-wrap">
      <Form
        name="getShort"
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 10 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        autoComplete="off"
      >
        <Form.Item
          label="shortLink"
          name="shortLink"
          validateTrigger="submit"
          rules={[{
            required: true,
            message: 'Please input your short link!'
          }, {
            pattern: /(http|https):\/\/[\w\-_]+(.[\w\-_]+)+([\w\-.,@?^=%&:/~+#]*[\w\-@?^=%&/~+#])?/,
            message: 'Url invalid! You should input full path including protocal.'
          }]}
        >
          <Input />
        </Form.Item>
        <Form.Item wrapperCol={{ offset: 4, span: 10 }}>
          <Button loading={loading} type="primary" htmlType="submit">
            Search
          </Button>
        </Form.Item>
      </Form>
      {
        !!originLink && (
          <Row>
            <Col offset={4} span={10}>
              <Alert
                message={
                  <>
                    Your origin url is <b>{originLink}</b>.
                  </>
                }
                type="success" />
            </Col>
          </Row>
        )
      }
    </div>
  );
}

export default GetOriginLink;
