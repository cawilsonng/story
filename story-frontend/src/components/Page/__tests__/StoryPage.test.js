import {render, screen} from '@testing-library/react';
import StoryPage from "../StoryPage";
import '@testing-library/jest-dom/extend-expect'
import MockTheme from "../../../__tests/MockTheme";
import MockRouter from "../../../__tests/MockRouter";

test('renders story page', () => {
    render(<MockTheme><MockRouter><StoryPage/></MockRouter></MockTheme>);
    const linkElement = screen.getByText(/The end of the story/i);
    expect(linkElement).toBeInTheDocument();
});
