package it.interop.dgc.verifier.utils;

public class DRLEUConstants {

    public static class DeltaField {

        public final static String COLLECTION_NAME = "european_delta";

        public final static String FROM_VERSION = "from_version";

        public final static String TO_VERSION = "to_version";

        public final static String TOTAL = "num_total_identifiers";

        public final static String NUM_ADD = "num_total_added";

        public final static String NUM_DELETE = "num_total_removed";
    }

    public static class DeltaEntryField {

        public final static String COLLECTION_NAME = "european_delta_entry";

        public final static String FROM_VERSION = "from_version";

        public final static String TO_VERSION = "to_version";

        public final static String ADD_IDENTIFIER = "identifier_to_add";

        public final static String DEL_IDENTIFIER = "identifier_to_remove";

    }

    public static class SnapshotField {

        public final static String COLLECTION_NAME = "european_snapshot";

        public final static String VERSION = "version";

        public final static String CREATION_DATE = "creation_date";

        public final static String FLAG_ARCHIVED = "is_archived";

        public final static String TOTAL = "num_total_identifiers";
    }

    public static class SnapshotEntryField {

        public final static String COLLECTION_NAME = "european_snapshot_entry";

        public final static String VERSION = "version";

        public final static String IDENTIFIER = "hash";

    }
}
