import * as React from 'react';
import {useState} from 'react';
import CssBaseline from "@mui/material/CssBaseline";
import {StoryAppBar} from "../StoryAppBar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import {Paper} from "@mui/material";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import {SubmissionForm} from "../SubmissionForm";
import {StoriesService} from "../../services/StoriesService";
import Footer from "../Footer";

function getStepContent(step, [form, handleFormOnChange]) {
    switch (step) {
        case 0:
            return <SubmissionForm form={form} handleFormOnChange={handleFormOnChange}/>;
        case 1:
            return <>
                <Typography variant="h5" gutterBottom sx={{pt: 3}}>
                    Thank you for submission.
                </Typography>
            </>;
        default:
            throw new Error('Unknown step');
    }
}

export default function SubmissionPage() {
    const [activeStep, setActiveStep] = React.useState(0);
    const [form, setForm] = useState({title: '', description: '', content: ''});

    const handleSubmit = () => {
        StoriesService.postStory(form).then(response => {
            setActiveStep(activeStep + 1)
        });
    };

    const handleFormOnChange = (event, fieldName) => {
        setForm({...form, [fieldName]: event.target.value,});
    }

    return (
        <>
            <CssBaseline/>
            <StoryAppBar/>
            <Container component="main" maxWidth="sm" sx={{mb: 4}}>
                <Paper variant="outlined" sx={{my: {xs: 3, md: 6}, p: {xs: 2, md: 3}}}>
                    <React.Fragment>
                        {getStepContent(activeStep, [form, handleFormOnChange])}
                        <Box sx={{display: 'flex', justifyContent: 'flex-end'}}>
                            {activeStep === 0 && (
                                <Button
                                    variant="contained"
                                    onClick={handleSubmit}
                                    sx={{mt: 3, ml: 1}}
                                >
                                    Submit
                                </Button>
                            )}
                        </Box>
                    </React.Fragment>
                </Paper>
            </Container>
            <Footer/>
        </>
    );
}