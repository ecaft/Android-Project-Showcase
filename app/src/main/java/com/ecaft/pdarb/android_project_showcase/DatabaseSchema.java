package com.ecaft.pdarb.android_project_showcase;

import android.provider.BaseColumns;

public class DatabaseSchema {
    public static final class Favorites{
        public static final String NAME = "favorites";
        public static final class Cols implements BaseColumns{
            public static final String PROJECT_NAME = "name";
            public static final String VISITED = "visited";
        }
    }
}
