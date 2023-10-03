import React from "react";
import {
  Grid,
  Card,
  Icon,
  Link,
  List,
  ListItem,
  Snackbar,
  Tooltip,
  IconButton,
} from "@mui/material";
import { QRCodeSVG } from "qrcode.react";
import { CopyToClipboard } from "react-copy-to-clipboard";
import { CopyAllOutlined } from "@mui/icons-material";
import "./index.css";

interface IShortLinkObject {
  dataSource: {
    shortUrl: string;
    [propName: string]: any;
  };
  [propName: string]: any;
}

function ShortLinkCard(props: IShortLinkObject) {
  const { dataSource } = props;
  const { url, shortUrl } = dataSource;

  const showCopyTip = () => {};

  return (
    <Grid
      container
      paddingLeft={{ xs: 0, lg: 6 }}
      paddingRight={{ xs: 0, lg: 6 }}
      spacing={{ xs: 4, md: 2, lg: 2 }}
      columns={{ xs: 1, md: 2, lg: 16 }}
    >
      <Grid
        item
        xs={16}
        lg={4}
        alignItems={"center"}
        style={{
          textAlign: "center",
        }}
      >
        <QRCodeSVG value={shortUrl} />
      </Grid>
      <Grid item xs={16} lg={12}>
        <List className={"url-list"}>
          <ListItem>
            <span className={"item-label"}>短链接：</span>
            <Link href={shortUrl} target="_blank">
              {shortUrl}
            </Link>
            <CopyToClipboard text={shortUrl} onCopy={showCopyTip}>
              <Tooltip title={"复制"}>
                <IconButton
                  style={{
                    margin: "0 6px",
                  }}
                >
                  <CopyAllOutlined style={{ cursor: "pointer" }} />
                </IconButton>
              </Tooltip>
            </CopyToClipboard>
          </ListItem>
          <ListItem>
            <span className={"item-label"}>原始链接：</span>
            <Link href={url} target="_blank">
              {url}
            </Link>
            <CopyToClipboard text={url} onCopy={showCopyTip}>
              <Tooltip title={"复制"}>
                <IconButton
                  style={{
                    margin: "0 6px",
                  }}
                >
                  <CopyAllOutlined style={{ cursor: "pointer" }} />
                </IconButton>
              </Tooltip>
            </CopyToClipboard>
          </ListItem>
        </List>
      </Grid>
    </Grid>
  );
}

export default ShortLinkCard;
