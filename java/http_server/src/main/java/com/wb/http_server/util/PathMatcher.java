package com.wb.http_server.util;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatcher {
    public static final String DEFAULT_PATH_SEPARATOR = "/";
    private static final int CACHE_TURNOFF_THRESHOLD = 65536;
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{[^/]+?\\}");
    private static final char[] WILDCARD_CHARS = new char[]{'*', '?', '{'};
    private String pathSeparator;
    private PathMatcher.PathSeparatorPatternCache pathSeparatorPatternCache;
    private boolean caseSensitive = true;
    private boolean trimTokens = false;
    private volatile Boolean cachePatterns;
    private final Map<String, String[]> tokenizedPatternCache = new ConcurrentHashMap(256);
    final Map<String, PathMatcher.AntPathStringMatcher> stringMatcherCache = new ConcurrentHashMap(256);

    public PathMatcher() {
        this.pathSeparator = "/";
        this.pathSeparatorPatternCache = new PathMatcher.PathSeparatorPatternCache("/");
    }

    private void deactivatePatternCache() {
        this.cachePatterns = false;
        this.tokenizedPatternCache.clear();
        this.stringMatcherCache.clear();
    }


    public boolean match(String pattern, String path) {
        return this.doMatch(pattern, path, true, (Map)null);
    }


    protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
        if (path.startsWith(this.pathSeparator) != pattern.startsWith(this.pathSeparator)) {
            return false;
        } else {
            String[] pattDirs = this.tokenizePattern(pattern);
            if (fullMatch && this.caseSensitive && !this.isPotentialMatch(path, pattDirs)) {
                return false;
            } else {
                String[] pathDirs = this.tokenizePath(path);
                int pattIdxStart = 0;
                int pattIdxEnd = pattDirs.length - 1;
                int pathIdxStart = 0;

                int pathIdxEnd;
                String pattDir;
                for(pathIdxEnd = pathDirs.length - 1; pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd; ++pathIdxStart) {
                    pattDir = pattDirs[pattIdxStart];
                    if ("**".equals(pattDir)) {
                        break;
                    }

                    if (!this.matchStrings(pattDir, pathDirs[pathIdxStart], uriTemplateVariables)) {
                        return false;
                    }

                    ++pattIdxStart;
                }

                int patIdxTmp;
                if (pathIdxStart > pathIdxEnd) {
                    if (pattIdxStart > pattIdxEnd) {
                        return pattern.endsWith(this.pathSeparator) == path.endsWith(this.pathSeparator);
                    } else if (!fullMatch) {
                        return true;
                    } else if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(this.pathSeparator)) {
                        return true;
                    } else {
                        for(patIdxTmp = pattIdxStart; patIdxTmp <= pattIdxEnd; ++patIdxTmp) {
                            if (!pattDirs[patIdxTmp].equals("**")) {
                                return false;
                            }
                        }

                        return true;
                    }
                } else if (pattIdxStart > pattIdxEnd) {
                    return false;
                } else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
                    return true;
                } else {
                    while(pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
                        pattDir = pattDirs[pattIdxEnd];
                        if (pattDir.equals("**")) {
                            break;
                        }

                        if (!this.matchStrings(pattDir, pathDirs[pathIdxEnd], uriTemplateVariables)) {
                            return false;
                        }

                        --pattIdxEnd;
                        --pathIdxEnd;
                    }

                    if (pathIdxStart > pathIdxEnd) {
                        for(patIdxTmp = pattIdxStart; patIdxTmp <= pattIdxEnd; ++patIdxTmp) {
                            if (!pattDirs[patIdxTmp].equals("**")) {
                                return false;
                            }
                        }

                        return true;
                    } else {
                        while(pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
                            patIdxTmp = -1;

                            int patLength;
                            for(patLength = pattIdxStart + 1; patLength <= pattIdxEnd; ++patLength) {
                                if (pattDirs[patLength].equals("**")) {
                                    patIdxTmp = patLength;
                                    break;
                                }
                            }

                            if (patIdxTmp == pattIdxStart + 1) {
                                ++pattIdxStart;
                            } else {
                                patLength = patIdxTmp - pattIdxStart - 1;
                                int strLength = pathIdxEnd - pathIdxStart + 1;
                                int foundIdx = -1;
                                int i = 0;

                                label145:
                                while(i <= strLength - patLength) {
                                    for(int j = 0; j < patLength; ++j) {
                                        String subPat = pattDirs[pattIdxStart + j + 1];
                                        String subStr = pathDirs[pathIdxStart + i + j];
                                        if (!this.matchStrings(subPat, subStr, uriTemplateVariables)) {
                                            ++i;
                                            continue label145;
                                        }
                                    }

                                    foundIdx = pathIdxStart + i;
                                    break;
                                }

                                if (foundIdx == -1) {
                                    return false;
                                }

                                pattIdxStart = patIdxTmp;
                                pathIdxStart = foundIdx + patLength;
                            }
                        }

                        for(patIdxTmp = pattIdxStart; patIdxTmp <= pattIdxEnd; ++patIdxTmp) {
                            if (!pattDirs[patIdxTmp].equals("**")) {
                                return false;
                            }
                        }

                        return true;
                    }
                }
            }
        }
    }

    private boolean isPotentialMatch(String path, String[] pattDirs) {
        if (!this.trimTokens) {
            int pos = 0;
            String[] var4 = pattDirs;
            int var5 = pattDirs.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String pattDir = var4[var6];
                int skipped = this.skipSeparator(path, pos, this.pathSeparator);
                pos += skipped;
                skipped = this.skipSegment(path, pos, pattDir);
                if (skipped < pattDir.length()) {
                    return skipped > 0 || pattDir.length() > 0 && this.isWildcardChar(pattDir.charAt(0));
                }

                pos += skipped;
            }
        }

        return true;
    }

    private int skipSegment(String path, int pos, String prefix) {
        int skipped = 0;

        for(int i = 0; i < prefix.length(); ++i) {
            char c = prefix.charAt(i);
            if (this.isWildcardChar(c)) {
                return skipped;
            }

            int currPos = pos + skipped;
            if (currPos >= path.length()) {
                return 0;
            }

            if (c == path.charAt(currPos)) {
                ++skipped;
            }
        }

        return skipped;
    }

    private int skipSeparator(String path, int pos, String separator) {
        int skipped;
        for(skipped = 0; path.startsWith(separator, pos + skipped); skipped += separator.length()) {
        }

        return skipped;
    }

    private boolean isWildcardChar(char c) {
        char[] var2 = WILDCARD_CHARS;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char candidate = var2[var4];
            if (c == candidate) {
                return true;
            }
        }

        return false;
    }

    protected String[] tokenizePattern(String pattern) {
        String[] tokenized = null;
        Boolean cachePatterns = this.cachePatterns;
        if (cachePatterns == null || cachePatterns) {
            tokenized = (String[])this.tokenizedPatternCache.get(pattern);
        }

        if (tokenized == null) {
            tokenized = this.tokenizePath(pattern);
            if (cachePatterns == null && this.tokenizedPatternCache.size() >= 65536) {
                this.deactivatePatternCache();
                return tokenized;
            }

            if (cachePatterns == null || cachePatterns) {
                this.tokenizedPatternCache.put(pattern, tokenized);
            }
        }

        return tokenized;
    }

    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public static String[] toStringArray(Collection<String> collection) {
        return (String[])collection.toArray(new String[0]);
    }


    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if (str == null) {
            return new String[0];
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            ArrayList tokens = new ArrayList();

            while(true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return toStringArray((Collection)tokens);
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while(ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }
    protected String[] tokenizePath(String path) {
        return tokenizeToStringArray(path, this.pathSeparator, this.trimTokens, true);
    }

    private boolean matchStrings(String pattern, String str, Map<String, String> uriTemplateVariables) {
        return this.getStringMatcher(pattern).matchStrings(str, uriTemplateVariables);
    }

    protected PathMatcher.AntPathStringMatcher getStringMatcher(String pattern) {
        PathMatcher.AntPathStringMatcher matcher = null;
        Boolean cachePatterns = this.cachePatterns;
        if (cachePatterns == null || cachePatterns) {
            matcher = (PathMatcher.AntPathStringMatcher)this.stringMatcherCache.get(pattern);
        }

        if (matcher == null) {
            matcher = new PathMatcher.AntPathStringMatcher(pattern, this.caseSensitive);
            if (cachePatterns == null && this.stringMatcherCache.size() >= 65536) {
                this.deactivatePatternCache();
                return matcher;
            }

            if (cachePatterns == null || cachePatterns) {
                this.stringMatcherCache.put(pattern, matcher);
            }
        }

        return matcher;
    }

    public Comparator<String> getPatternComparator(String path) {
        return new PathMatcher.AntPatternComparator(path);
    }

    private static class PathSeparatorPatternCache {
        private final String endsOnWildCard;
        private final String endsOnDoubleWildCard;

        public PathSeparatorPatternCache(String pathSeparator) {
            this.endsOnWildCard = pathSeparator + "*";
            this.endsOnDoubleWildCard = pathSeparator + "**";
        }
    }

    protected static class AntPatternComparator implements Comparator<String> {
        private final String path;

        public AntPatternComparator(String path) {
            this.path = path;
        }

        public int compare(String pattern1, String pattern2) {
            PathMatcher.AntPatternComparator.PatternInfo info1 = new PathMatcher.AntPatternComparator.PatternInfo(pattern1);
            PathMatcher.AntPatternComparator.PatternInfo info2 = new PathMatcher.AntPatternComparator.PatternInfo(pattern2);
            if (info1.isLeastSpecific() && info2.isLeastSpecific()) {
                return 0;
            } else if (info1.isLeastSpecific()) {
                return 1;
            } else if (info2.isLeastSpecific()) {
                return -1;
            } else {
                boolean pattern1EqualsPath = pattern1.equals(this.path);
                boolean pattern2EqualsPath = pattern2.equals(this.path);
                if (pattern1EqualsPath && pattern2EqualsPath) {
                    return 0;
                } else if (pattern1EqualsPath) {
                    return -1;
                } else if (pattern2EqualsPath) {
                    return 1;
                } else if (info1.isPrefixPattern() && info2.getDoubleWildcards() == 0) {
                    return 1;
                } else if (info2.isPrefixPattern() && info1.getDoubleWildcards() == 0) {
                    return -1;
                } else if (info1.getTotalCount() != info2.getTotalCount()) {
                    return info1.getTotalCount() - info2.getTotalCount();
                } else if (info1.getLength() != info2.getLength()) {
                    return info2.getLength() - info1.getLength();
                } else if (info1.getSingleWildcards() < info2.getSingleWildcards()) {
                    return -1;
                } else if (info2.getSingleWildcards() < info1.getSingleWildcards()) {
                    return 1;
                } else if (info1.getUriVars() < info2.getUriVars()) {
                    return -1;
                } else {
                    return info2.getUriVars() < info1.getUriVars() ? 1 : 0;
                }
            }
        }

        private static class PatternInfo {
            private final String pattern;
            private int uriVars;
            private int singleWildcards;
            private int doubleWildcards;
            private boolean catchAllPattern;
            private boolean prefixPattern;
            private Integer length;

            public PatternInfo(String pattern) {
                this.pattern = pattern;
                if (this.pattern != null) {
                    this.initCounters();
                    this.catchAllPattern = this.pattern.equals("/**");
                    this.prefixPattern = !this.catchAllPattern && this.pattern.endsWith("/**");
                }

                if (this.uriVars == 0) {
                    this.length = this.pattern != null ? this.pattern.length() : 0;
                }

            }

            protected void initCounters() {
                int pos = 0;
                if (this.pattern != null) {
                    while(true) {
                        while(pos < this.pattern.length()) {
                            if (this.pattern.charAt(pos) == '{') {
                                ++this.uriVars;
                                ++pos;
                            } else if (this.pattern.charAt(pos) == '*') {
                                if (pos + 1 < this.pattern.length() && this.pattern.charAt(pos + 1) == '*') {
                                    ++this.doubleWildcards;
                                    pos += 2;
                                } else if (pos > 0 && !this.pattern.substring(pos - 1).equals(".*")) {
                                    ++this.singleWildcards;
                                    ++pos;
                                } else {
                                    ++pos;
                                }
                            } else {
                                ++pos;
                            }
                        }

                        return;
                    }
                }
            }

            public int getUriVars() {
                return this.uriVars;
            }

            public int getSingleWildcards() {
                return this.singleWildcards;
            }

            public int getDoubleWildcards() {
                return this.doubleWildcards;
            }

            public boolean isLeastSpecific() {
                return this.pattern == null || this.catchAllPattern;
            }

            public boolean isPrefixPattern() {
                return this.prefixPattern;
            }

            public int getTotalCount() {
                return this.uriVars + this.singleWildcards + 2 * this.doubleWildcards;
            }

            public int getLength() {
                if (this.length == null) {
                    this.length = this.pattern != null ? PathMatcher.VARIABLE_PATTERN.matcher(this.pattern).replaceAll("#").length() : 0;
                }

                return this.length;
            }
        }
    }

    protected static class AntPathStringMatcher {
        private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");
        private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";
        private final Pattern pattern;
        private final List<String> variableNames;

        public AntPathStringMatcher(String pattern, boolean caseSensitive) {
            this.variableNames = new LinkedList();
            StringBuilder patternBuilder = new StringBuilder();
            Matcher matcher = GLOB_PATTERN.matcher(pattern);

            int end;
            for(end = 0; matcher.find(); end = matcher.end()) {
                patternBuilder.append(this.quote(pattern, end, matcher.start()));
                String match = matcher.group();
                if ("?".equals(match)) {
                    patternBuilder.append('.');
                } else if ("*".equals(match)) {
                    patternBuilder.append(".*");
                } else if (match.startsWith("{") && match.endsWith("}")) {
                    int colonIdx = match.indexOf(58);
                    if (colonIdx == -1) {
                        patternBuilder.append("(.*)");
                        this.variableNames.add(matcher.group(1));
                    } else {
                        String variablePattern = match.substring(colonIdx + 1, match.length() - 1);
                        patternBuilder.append('(');
                        patternBuilder.append(variablePattern);
                        patternBuilder.append(')');
                        String variableName = match.substring(1, colonIdx);
                        this.variableNames.add(variableName);
                    }
                }
            }

            patternBuilder.append(this.quote(pattern, end, pattern.length()));
            this.pattern = caseSensitive ? Pattern.compile(patternBuilder.toString()) : Pattern.compile(patternBuilder.toString(), 2);
        }

        private String quote(String s, int start, int end) {
            return start == end ? "" : Pattern.quote(s.substring(start, end));
        }

        public boolean matchStrings(String str, Map<String, String> uriTemplateVariables) {
            Matcher matcher = this.pattern.matcher(str);
            if (!matcher.matches()) {
                return false;
            } else {
                if (uriTemplateVariables != null) {
                    if (this.variableNames.size() != matcher.groupCount()) {
                        throw new IllegalArgumentException("The number of capturing groups in the pattern segment " + this.pattern + " does not match the number of URI template variables it defines, which can occur if capturing groups are used in a URI template regex. Use non-capturing groups instead.");
                    }

                    for(int i = 1; i <= matcher.groupCount(); ++i) {
                        String name = (String)this.variableNames.get(i - 1);
                        String value = matcher.group(i);
                        uriTemplateVariables.put(name, value);
                    }
                }

                return true;
            }
        }
    }
}
