import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import {Link} from "@mui/material";

function Copyright() {
    return (
        <Typography variant="body2" color="text.secondary" align="center" sx={{pt:1}}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

export default function Footer() {
    return (
        <Box sx={{bgcolor: 'background.paper', pt: 2,pb: 2}} component="footer">
            <Typography
                variant="subtitle1"
                align="center"
                color="text.secondary"
                component="p"
            >
                Welcome to our website dedicated to preserving and sharing the captivating stories written by John.
            </Typography>
            <Typography
                variant="subtitle1"
                align="center"
                color="text.secondary"
                component="p"
            >
                We believe that stories have the power to inspire, entertain, and transport us to extraordinary worlds.
            </Typography>
            <Copyright/>
        </Box>
    );
}