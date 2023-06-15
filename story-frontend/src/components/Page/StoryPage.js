import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import CssBaseline from "@mui/material/CssBaseline";
import {StoriesService} from "../../services/StoriesService";
import {StoryAppBar} from "../StoryAppBar";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Footer from "../Footer";
import Button from "@mui/material/Button";
import ErrorPage from "./ErrorPage";

export default function StoryPage() {
    let {storyId} = useParams();
    const [story, setStory] = useState([]);
    useEffect(() => {
        StoriesService.fetchStory(storyId).then(payload => {
            setStory(payload);
        });
    }, []);
    return (
        <>
            {story &&
                <>
                    <CssBaseline/>
                    <StoryAppBar/>
                    <Container>
                        <Box
                            component="img"
                            sx={{
                                height: 500,
                                width: '100%',
                                maxHeight: {xs: 233, md: 167},
                                objectFit: "cover",
                            }}
                            src="https://source.unsplash.com/random?wallpapers"
                        />
                        <Box component={Box} sx={{display: "flex", justifyContent: "right"}}>
                            <Button href={'/api/stories/' + storyId + '/download'} variant="contained"
                                    size="small" sx={{mx: 2}}>Download</Button>
                        </Box>
                        <Typography
                            component="h1"
                            variant="h2"
                            align="center"
                            color="text.primary"
                            gutterBottom
                        >
                            {story.title}
                        </Typography>
                        <Typography variant="h5" align="left" color="text.secondary" paragraph>
                            Description:
                        </Typography>
                        <Typography variant="h5" align="left" color="text.secondary" paragraph>
                            {story.description}
                        </Typography>
                        <Typography variant="h5" align="left" color="text.secondary" paragraph>
                            Content:
                        </Typography>
                        <Typography variant="h5" align="left" color="text.secondary" paragraph
                                    style={{whiteSpace: 'pre-line'}}>
                            {story.content}
                        </Typography>
                        <Typography
                            component="h5"
                            variant="h5"
                            align="center"
                            color="text.primary"
                            gutterBottom
                        >
                            The end of the story
                        </Typography>
                    </Container>
                    <Footer/>
                </>
            }{!story && <ErrorPage/>}
        </>
    );
}