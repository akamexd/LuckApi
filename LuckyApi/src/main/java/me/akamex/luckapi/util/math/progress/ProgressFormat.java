package me.akamex.luckapi.util.math.progress;

import me.akamex.luckapi.api.Creatable;

public interface ProgressFormat {

    static Builder builder() {
        return new Builder();
    }

    class Builder implements Creatable<ProgressFormat> {

        private int size;
        private String yes, no;

        private Builder() {
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setYesSymbol(String yes) {
            this.yes = yes;
            return this;
        }

        public Builder setNoSymbol(String no) {
            this.no = no;
            return this;
        }

        @Override
        public ProgressFormat create() {
            return new ProgressFormatImpl(size, yes, no);
        }
    }

    String format(ProgressState state);

}
