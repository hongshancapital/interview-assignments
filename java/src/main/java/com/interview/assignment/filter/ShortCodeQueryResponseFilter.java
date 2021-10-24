package com.interview.assignment.filter;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.request.ShortCodeQueryRequest;
import javafx.util.Pair;

public interface ShortCodeQueryResponseFilter extends Filter<Pair<ShortCodeQueryRequest, ShortCode>> {
}
