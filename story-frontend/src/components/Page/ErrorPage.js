import CssBaseline from "@mui/material/CssBaseline";
import {StoryAppBar} from "../StoryAppBar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Footer from "../Footer";

export default function ErrorPage() {
    return (
        <>
            <CssBaseline/>
            <StoryAppBar/>
            <main>
                <Box sx={{
                    bgcolor: 'background.paper',
                    pt: 8,
                    pb: 6,
                }}>
                    <Container maxWidth="sm">
                        <Typography
                            component="h1"
                            variant="h2"
                            align="center"
                            color="text.primary"
                            gutterBottom
                        >
                            Error Occurred
                        </Typography>
                        <Typography variant="h5" align="center" color="text.secondary" paragraph>
                            The page you are looking does not exist.
                        </Typography>
                        <Typography variant="h5" align="center" color="text.secondary" paragraph>
                            If the error keeps occurring, please contact the administrator.
                        </Typography>
                    </Container>
                </Box>
            </main>
            <Footer/>
        </>
    );
}