import axios from "axios";

const fetchAPI = (url, config) => {
    return axios.get(url, config).then(response => {
        if (response.data && response.data.code === 200) {
            return response.data.payload;
        }
    }).catch(error => {
        console.error('Error fetching resource:', error);
    });
}

const postAPI = (url, config) => {
    return axios.post(url, config).then(response => {
        if (response.data && response.data.code === 200) {
            return response.data.payload;
        }
    }).catch(error => {
        console.error('Error posting resource:', error);
    });
}

export const StoriesService = {
    fetchAllStories: (page, size) => {
        return fetchAPI('/api/stories/all', {
            params: {
                index: page,
                size: size,
            }
        });
    },
    fetchStory: (storyId) => {
        return fetchAPI('/api/stories/' + storyId, {});
    },
    postStory: (story) => {
        return postAPI('/api/stories', {title: story.title, description: story.description, content: story.content});
    },
    fetchHistoryCount: (storyId) => {
        if (storyId > 0) {
            return fetchAPI('/api/history/' + storyId, {});
        } else {
            return fetchAPI('/api/history', {});
        }
    }
}