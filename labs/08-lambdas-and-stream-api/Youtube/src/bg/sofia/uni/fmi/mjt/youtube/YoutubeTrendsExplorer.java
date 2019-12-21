package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YoutubeTrendsExplorer {
    private Map<String, List<TrendingVideo>> trendingVideos;

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     */
    public YoutubeTrendsExplorer(InputStream dataInput) {
        trendingVideos = new HashMap<>();
        buildTrendingVideos(dataInput);
    }

    /**
     * Returns all videos loaded from the dataset.
     */
    public Collection<TrendingVideo> getTrendingVideos() {
        return trendingVideos.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Returns the id of the least lied video.
     */
    public String findIdOfLeastLikedVideo() {
        return getTrendingVideos()
                .stream()
                .min(Comparator.comparing(TrendingVideo::getLikes))
                .map(TrendingVideo::getId).orElse(null);
    }

    /**
     * Returns the id of the most likable video.
     */
    public String findIdOfMostLikedLeastDislikedVideo() {
        return getTrendingVideos()
                .stream()
                .max((Comparator.comparing(TrendingVideo::getLikeRatio)))
                .map(TrendingVideo::getId).orElse(null);
    }

    /**
     * Returns list of titles of the most watched trending videos.
     */
    public List<String> findDistinctTitlesOfTop3VideosByViews() {
        final int limitOfTopVideosByViews = 3;

        return getTrendingVideos()
                .stream()
                .sorted(Comparator.comparing(TrendingVideo::getViews).reversed())
                .map(TrendingVideo::getTitle)
                .limit(limitOfTopVideosByViews)
                .collect(Collectors.toList());
    }

    /**
     * Returns the id of the most tagged video.
     */
    public String findIdOfMostTaggedVideo() {
        return getTrendingVideos()
                .stream()
                .max(Comparator.comparingInt(o -> o.getTags().size()))
                .map(TrendingVideo::getId).orElse(null);
    }

    /**
     * Returns the first trending video before it got 100K views.
     */
    public String findTitleOfFirstVideoTrendingBefore100KViews() {
        final int maxViews = 100_000;
        return getTrendingVideos()
                .stream()
                .filter(trendingVideo -> trendingVideo.getViews() < maxViews)
                .min(Comparator.comparing(TrendingVideo::getPublishDate))
                .map(TrendingVideo::getTitle).orElse(null);
    }

    /**
     * Returns the id of the most trending video.
     */
    public String findIdOfMostTrendingVideo() {
        return trendingVideos.values()
                .stream()
                .max(Comparator.comparing(List::size))
                .map(videos -> videos.get(0).getId()).orElse(null);
    }

    private void buildTrendingVideos(InputStream dataInput) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataInput))) {
            reader.readLine(); // skip the first line

            String currentLine;
            TrendingVideo currentTrendingVideo;
            while ((currentLine = reader.readLine()) != null) {
                currentTrendingVideo = TrendingVideo.createTrendingVideo(currentLine);
                addTrendingVideo(currentTrendingVideo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTrendingVideo(TrendingVideo trendingVideo) {
        final String videoId = trendingVideo.getId();
        if (!trendingVideos.containsKey(videoId)) {
            trendingVideos.put(videoId, new ArrayList<>());
        }

        trendingVideos.get(videoId).add(trendingVideo);
    }
}