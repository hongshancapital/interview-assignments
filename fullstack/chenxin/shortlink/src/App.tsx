import React, { useRef, useState } from "react";
import { set } from "./apis/lambda/shortlink";
import LayoutProvider from "./front/layout";
import {
  Grid,
  Paper,
  Typography,
  TextField,
  Button,
  Alert,
} from "@mui/material";
import ShortLinkCard from "./front/components/shortLinkCard";

export default function PaymentForm() {
  const linkRef: any = useRef(null);
  const [url, setUrl] = useState("");
  const [linkProps, setLinkProps] = useState({});
  const [saveResult, setSaveResult] = useState(null);

  const createShortLink = () => {
    const isRight = /^http(s)?/.test(url);
    setLinkProps({
      ...linkProps,
      error: !isRight,
    });

    isRight &&
      set({ url }).then((res) => {
        if (res.code == 0) {
          setSaveResult(res.data);
        }
      });
  };

  return (
    <LayoutProvider>
      <Paper
        variant="outlined"
        sx={{ my: { xs: 3, md: 9 }, p: { xs: 2, md: 6 } }}
      >
        <Typography variant="h6" gutterBottom>
          简单易用的短链接生成工具
        </Typography>
        <Alert severity="info">
          解决长网址传播受限的问题，缩短网址字符，易于传播；提供专业、全面的数据分析，帮助您更了解您的用户，挖掘潜在用户，提高转化。
        </Alert>
        <Grid
          container
          padding={6}
          spacing={{ xs: 4, md: 3 }}
          columns={{ xs: 1, sm: 8, md: 12 }}
        >
          <Grid item xs={10}>
            <TextField
              ref={linkRef}
              required
              id="url"
              label="请输入您的链接地址"
              fullWidth
              autoComplete="cc-name"
              variant="standard"
              onChange={(evt: any) => {
                setUrl(evt?.target.value);
              }}
              {...linkProps}
            />
          </Grid>
          <Grid item xs={2}>
            <Button variant="contained" size="large" onClick={createShortLink}>
              生成短链接
            </Button>
          </Grid>
        </Grid>
        {saveResult && <ShortLinkCard dataSource={saveResult} />}
      </Paper>
    </LayoutProvider>
  );
}
