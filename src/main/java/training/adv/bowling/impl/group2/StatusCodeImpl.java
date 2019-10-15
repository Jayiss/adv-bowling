package training.adv.bowling.impl.group2;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    FAILEDADD(-1){
        @Override
        public String getCode() {
            return ""+-1;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    },
    SUCCESSADD(1){
        @Override
        public String getCode() {
            return ""+-1;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    };
    private int value;
    private StatusCodeImpl(int value) {
        this.value = value;
    }
}
