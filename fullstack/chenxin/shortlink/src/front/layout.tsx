import React from "react";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Container from "@mui/material/Container";
import theme from './theme/blue';

function LayoutProvider(props) {
  // const theme = createTheme();
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="mb" sx={{ mb: 12 }}>
        {props.children}
      </Container>
    </ThemeProvider>
  );
}

export default LayoutProvider;
