import CssBaseline from "@mui/material/CssBaseline";
import {StoryAppBar} from "../StoryAppBar";
import Container from "@mui/material/Container";
import {
    Button,
    Pagination,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import React, {useEffect, useState} from "react";
import {Bar} from "react-chartjs-2";
import {StoriesService} from "../../services/StoriesService";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Footer from "../Footer";

const makeChartData = (data) => {
    return {
        labels,
        datasets: [
            {
                data: data,
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
        ],
    }
}
const makeOptions = (title) => {
    return {
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            title: {
                display: true,
                text: title,
            },
        },
    };
}
const labels = ['View', 'Download'];

export default function AnalyticsPage() {
    const [chartData, setChartData] = useState(makeChartData([0, 0]));
    const [options, setOptions] = useState(makeOptions('Overall analytics'));
    const handlePagesChange = (event, value) => {
        let page = value;
        if (page) {
            setPage(page)
            StoriesService.fetchAllStories(page - 1, 4).then(payload => {
                setFetchStoriesDto(payload);
            });
        }
    };
    const [fetchStoriesDto, setFetchStoriesDto] = useState([]);
    const [page, setPage] = useState(1);
    const [chartModel, setChartModel] = useState({
        isOverallMode: true,
        storyId: 0,
        title: '',
    });
    const fetch = (chartModel) => {
        let tmpId = chartModel.isOverallMode ? 0 : chartModel.storyId;
        let title = chartModel.isOverallMode ? 'Overall analytics' : chartModel.title + "'s analysis";
        StoriesService.fetchHistoryCount(tmpId).then(payload => {
            setChartData(makeChartData([payload.view, payload.download]));
            setOptions(makeOptions(title));
        });
    };
    useEffect(() => {
        StoriesService.fetchAllStories(page - 1, 4).then(payload => {
            setFetchStoriesDto(payload);
        });
    }, []);

    useEffect(() => {
        fetch(chartModel);
        const interval = setInterval(() => {
            fetch(chartModel);
        }, 5000);
        return () => clearInterval(interval);
    }, [chartModel]);

    return (
        <>
            <CssBaseline/>
            <StoryAppBar/>
            <Box component={Box} sx={{pt: 2, position: "absolute", right: '10vw'}}>
                <Button variant="contained"
                        size="small" sx={{mx: 2}} onClick={() => {
                    setChartModel({storyId: 0, isOverallMode: true, title: ''});
                }}>Show
                    Overview Analytics</Button>
            </Box>
            <Container component="main" maxWidth="lg" sx={{mb: 4}}>
                <Container component="main" maxWidth="sm" sx={{mb: 4, mt: 3}}>
                    <Bar options={options} data={chartData}/>
                </Container>
                {fetchStoriesDto && fetchStoriesDto.stories && fetchStoriesDto.stories.length > 0 &&
                    <TableContainer component={Paper}>
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>ID</TableCell>
                                    <TableCell>Title</TableCell>
                                    <TableCell>Description</TableCell>
                                    <TableCell>Action</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {fetchStoriesDto.stories.map((row) => (
                                    <TableRow
                                        key={row.id}
                                        sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                    >
                                        <TableCell component="th" scope="row">
                                            {row.id}
                                        </TableCell>
                                        <TableCell>{row.title}</TableCell>
                                        <TableCell>{row.description}</TableCell>
                                        <TableCell align="center"><Button variant="contained" onClick={() => {
                                            setChartModel({storyId: row.id, isOverallMode: false, title: row.title})
                                        }}>Show
                                            Analytic</Button></TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                }
                {fetchStoriesDto && fetchStoriesDto.stories && fetchStoriesDto.stories.length > 0 &&
                    < Box sx={{display: 'flex', justifyContent: 'center', my: 3}}>
                        <Pagination count={fetchStoriesDto.totalPage} page={page} onChange={handlePagesChange}/>
                    </Box>
                }
                {fetchStoriesDto && fetchStoriesDto.stories && fetchStoriesDto.stories.length == 0 &&
                    <Typography variant="h5" align="center" color="text.secondary" paragraph>
                        No story existed
                    </Typography>
                }
            </Container>
            <Footer/>
        </>
    );
}