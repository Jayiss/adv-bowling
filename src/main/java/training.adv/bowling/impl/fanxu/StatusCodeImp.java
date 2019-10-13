package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImp implements StatusCode {
    ISNEGATIVE(-1){
        @Override
        public String getCode() {
            return ""+-1;
        }

        @Override
        public String getMessage() {
            System.out.println(toString());
            return toString();
        }
    }, ADDGTMAXPIN(-2){
        @Override
        public String getCode() {
            return ""+-2;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    },GAMEFINISHED(-3){
        @Override
        public String getCode() {
            return ""+-3;
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
