import './App.css';
import {ThemeProvider} from "@mui/material";
import {createTheme} from "@mui/material/styles";
import {StoryRouter} from "./StoryRouter";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from "chart.js";

const defaultTheme = createTheme();

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

function App() {
    return (
        <div className="App">
            <ThemeProvider theme={defaultTheme}>
                <StoryRouter/>
            </ThemeProvider>
        </div>
    );
}

export default App;
