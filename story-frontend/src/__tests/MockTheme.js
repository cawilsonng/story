import React from 'react';
import {createTheme, ThemeProvider} from "@mui/material/styles";

function MockTheme({ children }) {
    const theme = createTheme({});
    return <ThemeProvider theme={theme}>{children}</ThemeProvider>;
}

export default MockTheme;