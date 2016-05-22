package jTunes.database;

public enum ValueType {
    genre,
    artist,
    album,
    song {
        @Override
        public ValueType next() {
            return this;
        }
    };
    
    public ValueType next() {
        // credits to http://stackoverflow.com/a/17664546
        return values()[ordinal() + 1];
    }
}
