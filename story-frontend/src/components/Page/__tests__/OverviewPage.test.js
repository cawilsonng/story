import {render, screen} from '@testing-library/react';
import OverviewPage from "../OverviewPage";
import '@testing-library/jest-dom/extend-expect'
import MockTheme from "../../../__tests/MockTheme";
import MockRouter from "../../../__tests/MockRouter";

test('renders overview story page', () => {
    render(<MockTheme><MockRouter><OverviewPage/></MockRouter></MockTheme>);
    const linkElement = screen.getByText(/John's Story Collection/i);
    expect(linkElement).toBeInTheDocument();
});
