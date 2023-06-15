import axios from 'axios';
import { StoriesService } from '../StoriesService';

jest.mock('axios');

describe('StoriesService', () => {
    afterEach(() => {
        jest.clearAllMocks();
    });

    test('fetchAllStories calls fetchAPI with correct parameters', async () => {
        const mockResponse = { code: 200, payload: 'mockedPayload' };
        axios.get.mockResolvedValueOnce({ data: mockResponse });

        const page = 1;
        const size = 10;
        const expectedUrl = '/api/stories/all';
        const expectedConfig = { params: { index: page, size: size } };

        const result = await StoriesService.fetchAllStories(page, size);

        expect(axios.get).toHaveBeenCalledTimes(1);
        expect(axios.get).toHaveBeenCalledWith(expectedUrl, expectedConfig);
        expect(result).toBe('mockedPayload');
    });

    test('fetchHistoryCount calls fetchAPI with correct parameters when storyId is greater than 0', async () => {
        const mockResponse = { code: 200, payload: 'mockedPayload' };
        axios.get.mockResolvedValueOnce({ data: mockResponse });

        const storyId = 123;
        const expectedUrl = '/api/history/' + storyId;
        const expectedConfig = {};

        const result = await StoriesService.fetchHistoryCount(storyId);

        expect(axios.get).toHaveBeenCalledTimes(1);
        expect(axios.get).toHaveBeenCalledWith(expectedUrl, expectedConfig);
        expect(result).toBe('mockedPayload');
    });

    test('fetchHistoryCount calls fetchAPI with correct parameters when storyId is 0', async () => {
        const mockResponse = { code: 200, payload: 'mockedPayload' };
        axios.get.mockResolvedValueOnce({ data: mockResponse });

        const storyId = 0;
        const expectedUrl = '/api/history';
        const expectedConfig = {};

        const result = await StoriesService.fetchHistoryCount(storyId);

        expect(axios.get).toHaveBeenCalledTimes(1);
        expect(axios.get).toHaveBeenCalledWith(expectedUrl, expectedConfig);
        expect(result).toBe('mockedPayload');
    });
});