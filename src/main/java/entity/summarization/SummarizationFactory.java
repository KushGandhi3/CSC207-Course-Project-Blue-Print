package entity.summarization;

import exception.ApiCallException;

/**
 * The factory for summarization entity.
 */
public interface SummarizationFactory {

    /**
     * Create a new summarization object.
     * @param summary the weather summary of the summarization.
     * @param outfit the outfit suggestion of the summarization.
     * @param travel the travel advice of the summarization.
     * @return the new summarization object.
     * @throws ApiCallException if the request fails.
     */
    Summarization createSummarization(String summary, String outfit, String travel) throws ApiCallException;
}
