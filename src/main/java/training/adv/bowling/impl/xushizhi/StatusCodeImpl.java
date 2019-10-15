package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {

    ACCEPTED(200) {  // All good

        @Override
        public String getCode() {
            return "200";
        }

        @Override
        public String getMessage() {
            return toString();
        }
    }, NEGATIVE(401) {  // Pin < 0

        @Override
        public String getCode() {
            return "401";
        }

        @Override
        public String getMessage() {
            System.out.println(toString());
            return toString();
        }
    }, OVERSIZE(402) {  // Pin > MAX_PIN

        @Override
        public String getCode() {
            return "402";
        }

        @Override
        public String getMessage() {
            System.out.println(toString());
            return toString();
        }
    }, SUMNOTALLOWED(403) {  // 1stPin + 2ndPin > MAX_PIN

        @Override
        public String getCode() {
            return "403";
        }

        @Override
        public String getMessage() {
            return toString();
        }
    }, GAMEISFINISHED(501) {  // Game has finished

        @Override
        public String getCode() {
            return "501";
        }

        @Override
        public String getMessage() {
            return toString();
        }
    };

    StatusCodeImpl(int value) {
    }
}
