package training.adv.bowling.impl.ChaoyiFang;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImp implements StatusCode {
    INVALIDPIN(-1){
        @Override
        public String getCode() {
            return ""+-1;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    },GAMEFINISHED(0){
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
