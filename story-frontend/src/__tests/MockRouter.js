import React from 'react';
import {BrowserRouter} from "react-router-dom";

function MockRouter({children}) {
    return <BrowserRouter>{children}</BrowserRouter>;
}

export default MockRouter;