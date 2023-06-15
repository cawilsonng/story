import Toolbar from "@mui/material/Toolbar";
import AutoStoriesIcon from "@mui/icons-material/AutoStories";
import Typography from "@mui/material/Typography";
import {Link} from "react-router-dom";
import AppBar from "@mui/material/AppBar";

export function StoryAppBar() {
    return (
        <AppBar position="relative">
            <Toolbar>
                <Link to={'/'}>
                    <AutoStoriesIcon sx={{mr: 2, color: 'white'}}/>
                </Link>
                <Typography variant="h6" color="inherit" noWrap component={Link} to={'/'}>
                    John's Story Collection
                </Typography>
            </Toolbar>
        </AppBar>
    );
}