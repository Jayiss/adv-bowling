package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImp implements StatusCode {
    FAILED(-1){
        @Override
        public String getCode() {
            return ""+-1;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    },FINISHED(0){
        @Override
        public String getCode() {
            return ""+0;
        }
        @Override
        public String getMessage() {
            return toString();
        }
    },SUCCESS(1){
        @Override
        public String getCode() {
            return ""+1;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    };
    private int value;
    private StatusCodeImp(int value){
        this.value = value;
    }
}