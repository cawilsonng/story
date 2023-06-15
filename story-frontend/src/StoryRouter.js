import {BrowserRouter, Route, Routes} from "react-router-dom";
import OverviewPage from "./components/Page/OverviewPage";
import StoryPage from "./components/Page/StoryPage";
import ErrorPage from "./components/Page/ErrorPage";
import SubmissionPage from "./components/Page/SubmissionPage";
import AnalyticsPage from "./components/Page/AnalyticsPage";

export function StoryRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<OverviewPage/>}/>
                <Route exact path="/story/submit" element={<SubmissionPage/>}/>
                <Route exact path="/story/analytics" element={<AnalyticsPage/>}/>
                <Route exact path="/story/:storyId" element={<StoryPage/>}/>
                <Route path="*" element={<ErrorPage/>}/>
            </Routes>
        </BrowserRouter>
    );
}