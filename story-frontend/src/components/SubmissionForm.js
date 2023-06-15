import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import {TextField} from "@mui/material";

export function SubmissionForm(props) {

    return (
        <>
            <Typography variant="h4" gutterBottom>
                Story Form
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={12}>
                    <TextField
                        required
                        id="title"
                        name="title"
                        label="Title"
                        fullWidth
                        variant="standard"
                        value={props.form.title}
                        onChange={(e) => {
                            props.handleFormOnChange(e, 'title');
                        }}
                    />
                </Grid>
                <Grid item xs={12} sm={12}>
                    <TextField
                        required
                        id="description"
                        name="description"
                        label="Description"
                        fullWidth
                        autoComplete="family-name"
                        variant="standard"
                        multiline={true}
                        rows={3}
                        value={props.form.description}
                        onChange={(e) => {
                            props.handleFormOnChange(e, 'description');
                        }}
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        required
                        id="content"
                        name="content"
                        label="Content"
                        fullWidth
                        variant="standard"
                        multiline={true}
                        rows={10}
                        value={props.form.content}
                        onChange={(e) => {
                            props.handleFormOnChange(e, 'content');
                        }}
                    />
                </Grid>
            </Grid>
        </>
    );
}