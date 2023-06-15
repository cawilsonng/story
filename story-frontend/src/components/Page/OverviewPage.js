import * as React from 'react';
import {useEffect, useState} from 'react';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Footer from "../Footer";
import {Link} from "react-router-dom";
import {StoryAppBar} from "../StoryAppBar";
import {StoriesService} from "../../services/StoriesService";
import {Pagination} from "@mui/material";

export default function OverviewPage() {
    const [fetchStoriesDto, setFetchStoriesDto] = useState([]);
    const [page, setPage] = useState(1);
    useEffect(() => {
        StoriesService.fetchAllStories(page - 1, 4).then(payload => {
            setFetchStoriesDto(payload);
        });
    }, []);

    const handlePagesChange = (event, value) => {
        let page = value;
        if (page) {
            console.log(page);
            setPage(page)
            StoriesService.fetchAllStories(page - 1, 4).then(payload => {
                setFetchStoriesDto(payload);
            });
        }
    }

    return (<>
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
                            Welcome
                        </Typography>
                        <Typography variant="h5" align="center" color="text.secondary" paragraph>
                            My story world is a rich and imaginative realm filled with captivating characters,
                            breathtaking landscapes, and thrilling adventures.
                            Each story transports readers to a different corner of this world,
                            allowing them to explore its wonders and unravel its mysteries.
                        </Typography>
                        <Stack
                            sx={{pt: 4}}
                            direction="row"
                            spacing={2}
                            justifyContent="center"
                        >
                            <Button component={Link} to={'/story/submit'} variant="contained">Submit story</Button>
                            <Button component={Link} to={'/story/analytics'} variant="outlined">Story Analytics</Button>
                        </Stack>
                    </Container>
                </Box>
                <Container sx={{py: 1}} maxWidth="md">
                    <Grid container spacing={4}>
                        {fetchStoriesDto && fetchStoriesDto.stories && fetchStoriesDto.stories.length > 0
                            && fetchStoriesDto.stories.map((story) => (
                                <Grid item key={story.id} xs={12} sm={6} md={6}>
                                    <Card
                                        sx={{height: '100%', display: 'flex', flexDirection: 'column'}}
                                    >
                                        <CardMedia
                                            component="div"
                                            sx={{
                                                width: '100%',
                                                pt: '56.25%',
                                            }}
                                            image="https://source.unsplash.com/random?wallpapers"
                                        />
                                        <CardContent sx={{flexGrow: 1}}>
                                            <Typography gutterBottom variant="h5" component="h2">
                                                {story.title}
                                            </Typography>
                                            <Typography align="left">
                                                Description: {story.description}
                                            </Typography>
                                            <Typography align="left" sx={{marginTop: 2}}>
                                                Content: {story.content}
                                            </Typography>
                                        </CardContent>
                                        <CardActions sx={{justifyContent: 'flex-end'}}>
                                            <Button component={Link} to={'/story/' + story.id}
                                                    size="small">Read</Button>
                                        </CardActions>
                                    </Card>
                                </Grid>
                            ))}
                    </Grid>
                    {fetchStoriesDto && fetchStoriesDto.stories && fetchStoriesDto.stories.length > 0 &&
                        < Box sx={{display: 'flex', justifyContent: 'center', my: 3}}>
                            <Pagination count={fetchStoriesDto.totalPage} page={page} onChange={handlePagesChange}/>
                        </Box>
                    }
                    {fetchStoriesDto && fetchStoriesDto.stories && fetchStoriesDto.stories.length == 0 &&
                        <Typography variant="h5" align="center" color="text.secondary" paragraph>
                            Submit your first story.
                        </Typography>
                    }
                </Container>
            </main>
            <Footer/>
        </>
    );
}